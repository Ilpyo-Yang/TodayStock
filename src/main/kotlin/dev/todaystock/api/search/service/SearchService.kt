package dev.todaystock.api.chat.service

import com.google.cloud.vertexai.VertexAI
import dev.todaystock.api.chat.dto.NewsItemList
import dev.todaystock.api.chat.dto.SearchRequest
import dev.todaystock.api.common.exception.CustomRuntimeException
import dev.todaystock.api.info.entity.*
import dev.todaystock.api.info.repository.*
import dev.todaystock.api.search.dto.NewsItem
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import jakarta.annotation.PostConstruct
import org.springframework.ai.chat.model.ChatResponse
import org.springframework.ai.chat.prompt.Prompt
import org.springframework.ai.vertexai.gemini.VertexAiGeminiChatModel
import org.springframework.ai.vertexai.gemini.VertexAiGeminiChatOptions
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatusCode
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.web.client.RestClient
import org.springframework.web.client.toEntity
import org.springframework.web.util.UriComponentsBuilder


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

    @Value("\${spring.ai.vertex.ai.gemini.project-id}")
    lateinit var gcpProjectId: String

    @Value("\${spring.ai.vertex.ai.gemini.location}")
    lateinit var gcpLocation: String

    private val clientId by lazy { naverClientId }
    private val clientSecret by lazy { naverClientSecret }
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

    fun searchKeyword(searchRequest: SearchRequest): List<MarkerInfo> {
        val keyword: String = searchRequest.keyword
        val infoType: InfoType = searchRequest.infoType
        val items: List<NewsItem?>

        when(infoType) {
            InfoType.Company -> {
                var company: Company? = companyRepository.findByName(keyword)
                company?: {
                    company = searchInfoType(keyword).also {
                        companyRepository.save(it)
                    }
                }

                items = searchNews(keyword).body?.items!!
                if(items.isNotEmpty()) {
                    for(i in items) {
                        val info: ChatResponse? = recapNews(i!!)
                        companyInfoRepository.save(CompanyInfo(null, company!!, i.title, i.link, "info", i.pubDate))
                    }
                }

                return companyInfoRepository.findByCompanyUuid(company!!.uuid!!)
            }
            InfoType.Country -> {
                return ArrayList<MarkerInfo>()
            }
            else -> {
                return ArrayList<MarkerInfo>()
            }
        }
    }

    // prompt를 두 개 만들어놔야겠지?
    fun searchInfoType(keyword: String): Company {
        val result = chatModel.call(
            Prompt(keyword + "에 대해 설명해줘")
        )
        return Company(null, keyword, "2134", "null", null)
    }

    fun recapNews(newsItem: NewsItem): ChatResponse? {
        return chatModel.call(
            Prompt("Generate the names of 5 famous pirates.")
        )
    }

    fun searchNews(keyword: String): ResponseEntity<NewsItemList> {
        val uri = UriComponentsBuilder
            .fromUriString("https://openapi.naver.com/v1/search/news.json")
            .build("query", keyword)

        return naverClient.get()
            .uri(uri)
            .accept(APPLICATION_JSON)
            .retrieve()
            .onStatus(HttpStatusCode::is4xxClientError) { _, response ->
                throw CustomRuntimeException("Searching news failed, " + response.statusText) }
            .toEntity<NewsItemList>()
    }
}
