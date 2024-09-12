package dev.todaystock.api.chat.service

import com.google.cloud.vertexai.VertexAI
import dev.todaystock.api.chat.dto.NewsItemList
import dev.todaystock.api.chat.dto.SearchRequest
import dev.todaystock.api.common.exception.CustomRuntimeException
import dev.todaystock.api.info.entity.*
import dev.todaystock.api.info.repository.*
import dev.todaystock.api.search.dto.NewsItem
import dev.todaystock.api.search.dto.SearchInfoType
import jakarta.annotation.PostConstruct
import kotlinx.serialization.json.Json
import org.springframework.ai.chat.prompt.Prompt
import org.springframework.ai.vertexai.gemini.VertexAiGeminiChatModel
import org.springframework.ai.vertexai.gemini.VertexAiGeminiChatOptions
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatusCode
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.client.RestClient
import org.springframework.web.client.toEntity


@Service
class SearchService(
    private val companyInfoRepository: CompanyInfoRepository,
    private val countryInfoRepository: CountryInfoRepository,
    private val themeInfoRepository: ThemeInfoRepository,
    private val companyRepository: CompanyRepository,
    private val countryRepository: CountryRepository,
    private val themeRepository: ThemeRepository
) {
    @Value("\${naver.api.client.id}")
    lateinit var naverClientId: String

    @Value("\${naver.api.client.secret}")
    lateinit var naverClientSecret: String

    @Value("\${naver.api.client.uri}")
    lateinit var naverClientUri: String

    @Value("\${spring.ai.vertex.ai.gemini.project-id}")
    lateinit var gcpProjectId: String

    @Value("\${spring.ai.vertex.ai.gemini.location}")
    lateinit var gcpLocation: String

    @Value("\${prompt.search-info}")
    lateinit var searchInfoPrompt: String

    @Value("\${prompt.recap-news}")
    lateinit var recapNewsPrompt: String

    private val clientId by lazy { naverClientId }
    private val clientSecret by lazy { naverClientSecret }
    private val clientUri by lazy { naverClientUri }
    private val projectId by lazy { gcpProjectId }
    private val location by lazy { gcpLocation }

    lateinit var naverClient: RestClient
    lateinit var chatModel: VertexAiGeminiChatModel

    @PostConstruct
    fun init() {
        naverClient = RestClient.builder()
            .baseUrl("https://openapi.naver.com/")
            .defaultHeader("X-Naver-Client-Id", clientId)
            .defaultHeader("X-Naver-Client-Secret", clientSecret)
            .build()

        chatModel = VertexAiGeminiChatModel(
            VertexAI(projectId, location),
            VertexAiGeminiChatOptions.builder()
                .withModel(VertexAiGeminiChatModel.ChatModel.GEMINI_1_5_PRO)
                .withTemperature(0.4.toFloat())
                .build())
    }

    @Transactional
    // search keyword
    fun searchKeyword(searchRequest: SearchRequest): List<MarkerInfo> {
        val keyword: String = searchRequest.keyword
        val infoType: InfoType = searchRequest.infoType
        val items: List<NewsItem?>

        when(infoType) {
            InfoType.Company -> {
                val company: Company = companyRepository.findByName(keyword).orElseGet {
                    val info = searchInfoType(keyword)
                    val company = Company(null, keyword, info.tickerCode, info.description, null)
                    companyRepository.save(company)
                }

                items = searchNews(keyword).body?.items!!
                if(items.isNotEmpty()) {
                    for(i in items) {
                        val info: String = recapNews(i!!.description)?: ""
                        companyInfoRepository.save(CompanyInfo(null, company, i.title, i.link, info, i.pubDate))
                    }
                }

                return companyInfoRepository.findByCompanyUuid(company.uuid!!)
            }
            InfoType.Country -> {
                val country: Country = countryRepository.findByName(keyword).orElseGet {
                    val info = searchInfoType(keyword)
                    val country = Country(null, keyword, info.description, null)
                    countryRepository.save(country)
                }

                items = searchNews(keyword).body?.items!!
                if(items.isNotEmpty()) {
                    for(i in items) {
                        val info: String = recapNews(i!!.description)?: ""
                        countryInfoRepository.save(CountryInfo(null, country, i.title, i.link, info, i.pubDate))
                    }
                }

                return countryInfoRepository.findByCountryUuid(country.uuid!!)
            }
            else -> {
                val theme: Theme = themeRepository.findByName(keyword).orElseGet {
                    val info = searchInfoType(keyword)
                    val theme = Theme(null, keyword, info.description, null)
                    themeRepository.save(theme)
                }

                items = searchNews(keyword).body?.items!!
                if(items.isNotEmpty()) {
                    for(i in items) {
                        val info: String = recapNews(i!!.description)?: ""
                        themeInfoRepository.save(ThemeInfo(null, theme, i.title, i.link, info, i.pubDate))
                    }
                }

                return themeInfoRepository.findByThemeUuid(theme.uuid!!)
            }
        }
    }

    // find info type information
    fun searchInfoType(keyword: String): SearchInfoType {
        val result = chatModel.call(Prompt(keyword + searchInfoPrompt)).result.output.content
        val json = result.substring(result.indexOf("{"), result.lastIndexOf("}")+1)
        return Json{ ignoreUnknownKeys = true }.decodeFromString<SearchInfoType>(json)
    }

    // find related recent news
    fun searchNews(keyword: String): ResponseEntity<NewsItemList> {
        return naverClient.get()
            .uri(clientUri, keyword)
            .retrieve()
            .onStatus(HttpStatusCode::is4xxClientError) { _, response ->
                throw CustomRuntimeException("Searching news failed, " + response.statusText) }
            .toEntity<NewsItemList>()
    }

    // find additional news info
    fun recapNews(description: String): String? {
        return chatModel.call(Prompt(description + recapNewsPrompt)).result.output.content
    }
}
