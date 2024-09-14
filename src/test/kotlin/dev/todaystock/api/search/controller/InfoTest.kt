package dev.todaystock.api.search.controller

import com.fasterxml.jackson.databind.ObjectMapper
import dev.todaystock.api.info.dto.InfoRequest
import dev.todaystock.api.info.entity.Company
import dev.todaystock.api.info.entity.CompanyInfo
import dev.todaystock.api.info.entity.InfoType
import dev.todaystock.api.info.repository.CompanyInfoRepository
import dev.todaystock.api.info.repository.CompanyRepository
import dev.todaystock.api.member.dto.SigninRequest
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import org.junit.jupiter.api.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders
import org.springframework.test.context.TestConstructor
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@SpringBootTest
@Transactional
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
@AutoConfigureMockMvc
@AutoConfigureRestDocs(uriScheme = "https", uriHost = "localhost")
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("Info 관련 API 테스트")
class InfoTest(
    private var mockMvc: MockMvc,
    private var mapper: ObjectMapper
) {
    @Autowired
    private val companyRepository: CompanyRepository? = null
    @Autowired
    private val companyInfoRepository: CompanyInfoRepository? = null

    private val memberUrl: String = "/v1/member"
    private val infoUrl: String = "/v1/info/{infoType}/detail"
    private val infoTypeUrl: String = "/v1/info/{infoType}"

    private val email: String = "test@gmail.com"
    private val password: String = "qwer123!@#"

    private var token: String? = null
    private var infoUuid: String? = null
    private var infoType: Company? = null
    private var infoTypeUuid: String? = null

    @BeforeEach
    fun beforeEachTest() {
        // signin before tests
        val content = SigninRequest(email, password)

        val loginResponse = mockMvc.perform(
            RestDocumentationRequestBuilders.post(memberUrl+"/signin")
                .content(mapper.writeValueAsString(content))
                .contentType("application/json")
        ).andDo(
            document("signup")
        ).andExpectAll(
            status().isOk,
            content().contentType(MediaType.APPLICATION_JSON),
        ).andReturn()

        val jsonResponse =  ObjectMapper().readTree(loginResponse.response.contentAsString)
        token = jsonResponse.get("data").get("accessToken").asText()
        infoType = companyRepository?.findTopByOrderByCreatedDateDesc()!!
        infoTypeUuid = infoType!!.uuid.toString()
    }



    @Test
    @Order(1)
    @DisplayName("정보 생성 테스트")
    fun createInfo() {
        val content = InfoRequest(null, infoTypeUuid!!, "infoTitle",
            "http://localhost:8080/info", "info", "2024-09-06 22:01:00"
        )

        val result = mockMvc.perform(
            RestDocumentationRequestBuilders.post(infoUrl, InfoType.Company)
                .header("Authorization", "Bearer $token")
                .content(mapper.writeValueAsString(content))
                .contentType("application/json")
        ).andDo(
            document("createInfo")
        ).andExpectAll(
            status().isOk,
            content().contentType(MediaType.APPLICATION_JSON),
        ).andReturn()

        val json = Json.parseToJsonElement(result.response.contentAsString)
        infoUuid = json.jsonObject["uuid"]?.jsonPrimitive?.content
    }

    @Test
    @Order(2)
    @DisplayName("정보유형별 정보 조회 테스트")
    fun findByInfoTypeUuid() {
        mockMvc.perform(
            RestDocumentationRequestBuilders.get(infoUrl, InfoType.Company)
                .header("Authorization", "Bearer $token")
                .content(mapper.writeValueAsString(infoTypeUuid))
                .contentType("application/json")
        ).andDo(
            document("findByInfoTypeUuid")
        ).andExpectAll(
            status().isOk,
            content().contentType(MediaType.APPLICATION_JSON),
        )
    }

    @Test
    @Order(3)
    @DisplayName("정보 삭제 테스트")
    fun deleteInfo() {
        val companyInfo = CompanyInfo(null, infoType!!, "infoTitle", "http://localhost:8080/info", "info", LocalDateTime.now())
        companyInfoRepository!!.save(companyInfo)

        mockMvc.perform(
            RestDocumentationRequestBuilders.delete(infoUrl, InfoType.Company)
                .header("Authorization", "Bearer $token")
                .content(mapper.writeValueAsString(companyInfo.uuid))
                .contentType("application/json")
        ).andDo(
            document("deleteInfo")
        ).andExpectAll(
            status().isOk,
            content().contentType(MediaType.APPLICATION_JSON),
        )
    }
}