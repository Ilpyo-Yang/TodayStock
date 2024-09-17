package dev.todaystock.api.search.controller

import com.fasterxml.jackson.databind.ObjectMapper
import dev.todaystock.api.info.dto.InfoTypeRequest
import dev.todaystock.api.info.entity.Company
import dev.todaystock.api.info.entity.InfoType
import dev.todaystock.api.info.repository.CompanyRepository
import dev.todaystock.api.member.dto.SigninRequest
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

@SpringBootTest
@Transactional
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
@AutoConfigureMockMvc
@AutoConfigureRestDocs(uriScheme = "https", uriHost = "localhost")
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("InfoType 관련 API 테스트")
class InfoTypeTest @Autowired constructor(
    private var mockMvc: MockMvc,
    private var mapper: ObjectMapper
) {
    @Autowired
    private val companyRepository: CompanyRepository? = null

    private val memberUrl: String = "/v1/member"
    private val infoTypeUrl: String = "/v1/info/{infoType}"

    private val email: String = "test@gmail.com"
    private val password: String = "qwer123!@#"

    private var token: String? = null

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
    }


    @Test
    @Order(1)
    @DisplayName("정보유형 생성 테스트")
    fun createInfoType() {
        val content = InfoTypeRequest(null, "_name", "ticker", "profile")

        mockMvc.perform(
            RestDocumentationRequestBuilders.post(infoTypeUrl, InfoType.Company)
                .header("Authorization", "Bearer $token")
                .content(mapper.writeValueAsString(content))
                .contentType("application/json")
        ).andDo(
            document("createInfoType")
        ).andExpectAll(
            status().isOk,
            content().contentType(MediaType.APPLICATION_JSON),
        )
    }

    @Test
    @Order(2)
    @DisplayName("정보유형 조회 테스트")
    fun findByInfoType() {
        mockMvc.perform(
            RestDocumentationRequestBuilders.get(infoTypeUrl, InfoType.Company)
                .header("Authorization", "Bearer $token")
        ).andDo(
            document("findByInfoType")
        ).andExpectAll(
            status().isOk,
            content().contentType(MediaType.APPLICATION_JSON),
        )
    }

    @Test
    @Order(3)
    @DisplayName("정보유형 삭제 테스트")
    fun deleteInfoType() {
        val company = Company(null, "name", "ticker", "profile")
        companyRepository!!.save(company)

        mockMvc.perform(
            RestDocumentationRequestBuilders.delete(infoTypeUrl, InfoType.Company)
                .header("Authorization", "Bearer $token")
                .content(mapper.writeValueAsString(company.uuid))
                .contentType("application/json")
        ).andDo(
            document("deleteInfoType")
        ).andExpectAll(
            status().isOk,
            content().contentType(MediaType.APPLICATION_JSON),
        )
    }
}