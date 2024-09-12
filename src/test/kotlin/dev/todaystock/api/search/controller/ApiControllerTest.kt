package dev.todaystock.api.search.controller

import com.fasterxml.jackson.databind.ObjectMapper
import dev.todaystock.api.chat.dto.SearchRequest
import dev.todaystock.api.collect.dto.CollectRequest
import dev.todaystock.api.info.dto.InfoRequest
import dev.todaystock.api.info.dto.InfoTypeRequest
import dev.todaystock.api.info.entity.InfoType
import dev.todaystock.api.info.repository.CompanyRepository
import dev.todaystock.api.member.dto.MemberRequest
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
import java.util.*


@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
@AutoConfigureMockMvc
@AutoConfigureRestDocs(uriScheme = "https", uriHost = "localhost")
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@DisplayName("API 테스트")
class ApiControllerTest(
    private var mockMvc: MockMvc,
    private var mapper: ObjectMapper
) {
    @Autowired
    private lateinit var companyRepository: CompanyRepository
    private val searchUrl: String = "/v1/search"
    private val memberUrl: String = "/v1/member"
    private val infoUrl: String = "/v1/info/{infoType}/detail"
    private val infoTypeUrl: String = "/v1/info/{infoType}"
    private val collectUrl: String = "/v1/collect"

    private val email: String = "stock@gmail.com"
    private val password: String = "qwer123!@#"

    private var token: String? = null
    private var memberUuid: String = UUID.randomUUID().toString()
    private var infoUuid: String = UUID.randomUUID().toString()
    private var infoTypeUuid: String = UUID.randomUUID().toString()


    @BeforeEach
    fun beforeEachTest() {
        // signin before tests
        val content = SigninRequest("test@gmail.com", password)

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



//    member api test

    @Test
    @Order(1)
    @DisplayName("회원가입 테스트")
    fun signup() {
        val content = MemberRequest(memberUuid, email, password, null, "mockmvc", null, null)

        mockMvc.perform(
            RestDocumentationRequestBuilders.post(memberUrl+"/signup")
                .content(mapper.writeValueAsString(content))
                .contentType("application/json")
        ).andDo(
            document("signup")
        ).andExpectAll(
            status().isOk,
            content().contentType(MediaType.APPLICATION_JSON),
        )
    }

    @Test
    @Order(2)
    @DisplayName("로그인 테스트")
    fun signin() {
        val content = SigninRequest(email, password)

        mockMvc.perform(
            RestDocumentationRequestBuilders.post(memberUrl+"/signin")
                .content(mapper.writeValueAsString(content))
                .contentType("application/json")
        ).andDo(
            document("signup")
        ).andExpectAll(
            status().isOk,
            content().contentType(MediaType.APPLICATION_JSON),
        )
    }

    @Test
    @Order(3)
    @DisplayName("회원삭제 테스트")
    fun deleteMember() {
        mockMvc.perform(
            RestDocumentationRequestBuilders.delete(memberUrl)
                .content(email)
                .contentType("application/json")
        ).andDo(
            document("signup")
        ).andExpectAll(
            status().isOk,
            content().contentType(MediaType.APPLICATION_JSON),
        )
    }



//    info api test

    @Test
    @Order(4)
    @DisplayName("정보유형 생성 테스트")
    fun createInfoType() {
        val content = InfoTypeRequest(infoTypeUuid, infoTypeUuid, "ticker", "profile")

        mockMvc.perform(
            RestDocumentationRequestBuilders.post(infoTypeUrl, InfoType.Company)
                .header("Authorization", "Bearer $token")
                .content(mapper.writeValueAsString(content))
                .contentType("application/json")
        ).andDo(
            document("findByInfoType")
        ).andExpectAll(
            status().isOk,
            content().contentType(MediaType.APPLICATION_JSON),
        )
    }

    @Test
    @Order(5)
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
    @Order(6)
    @DisplayName("정보 생성 테스트")
    fun createInfo() {
        val content = InfoRequest(infoUuid, "b86b9c55-485d-491e-b2ab-2ab7f8caefdb", "infoTitle",
            "http://localhost:8080/info", "info", "2024-09-06 22:01:00")

        mockMvc.perform(
            RestDocumentationRequestBuilders.post(infoUrl, InfoType.Company)
                .header("Authorization", "Bearer $token")
                .content(mapper.writeValueAsString(content))
                .contentType("application/json")
        ).andDo(
            document("createInfo")
        ).andExpectAll(
            status().isOk,
            content().contentType(MediaType.APPLICATION_JSON),
        )
    }

    @Test
    @Order(7)
    @DisplayName("정보유형으로 정보 조회 테스트")
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
    @Order(8)
    @DisplayName("정보 삭제 테스트")
    fun deleteInfo() {
        mockMvc.perform(
            RestDocumentationRequestBuilders.delete(infoUrl, InfoType.Company)
                .header("Authorization", "Bearer $token")
                .content(mapper.writeValueAsString(infoUuid))
                .contentType("application/json")
        ).andDo(
            document("deleteInfo")
        ).andExpectAll(
            status().isOk,
            content().contentType(MediaType.APPLICATION_JSON),
        )
    }

    @Test
    @Order(9)
    @DisplayName("정보유형 삭제 테스트")
    fun deleteInfoType() {
        mockMvc.perform(
            RestDocumentationRequestBuilders.delete(infoTypeUrl, InfoType.Company)
                .header("Authorization", "Bearer $token")
                .content(mapper.writeValueAsString(infoTypeUuid))
                .contentType("application/json")
        ).andDo(
            document("deleteInfoType")
        ).andExpectAll(
            status().isOk,
            content().contentType(MediaType.APPLICATION_JSON),
        )
    }



//    search api test

    @Test
    @Order(10)
    @DisplayName("뉴스 검색 및 정보 저장 테스트")
    fun searchInfo() {
        val content = SearchRequest("삼성전자", "Company")

        mockMvc.perform(
            RestDocumentationRequestBuilders.get(searchUrl)
                .header("Authorization", "Bearer $token")
                .content(mapper.writeValueAsString(content))
                .contentType("application/json")
        ).andDo(
            document("searchInfo")
        ).andExpectAll(
            status().isOk,
            content().contentType(MediaType.APPLICATION_JSON),
        )
    }



//    collect api test
    @Test
    @Order(11)
    @DisplayName("회원이 저장한 collect 종류 조회 테스트")
    fun findByMemberUuid() {
        mockMvc.perform(
            RestDocumentationRequestBuilders.get(collectUrl)
                .header("Authorization", "Bearer $token")
                .queryParam("memberUuid", memberUuid)
                .contentType("application/json")
        ).andDo(
            document("findByMemberUuid")
        ).andExpectAll(
            status().isOk,
            content().contentType(MediaType.APPLICATION_JSON),
        )
    }

    @Test
    @Order(12)
    @DisplayName("회원이 저장한 특정 정보타입 collect 종류 조회 테스트")
    fun findByMemberUuidAndType() {
        mockMvc.perform(
            RestDocumentationRequestBuilders.get(collectUrl + "/type")
                .header("Authorization", "Bearer $token")
                .queryParam("memberUuid", memberUuid)
                .queryParam("infoType", InfoType.Company.name)
                .contentType("application/json")
        ).andDo(
            document("findByMemberUuidAndType")
        ).andExpectAll(
            status().isOk,
            content().contentType(MediaType.APPLICATION_JSON),
        )
    }

    @Test
    @Order(13)
    @DisplayName("collect 생성 테스트")
    fun createCollect() {
        val content = CollectRequest(null, memberUuid, "Company", infoUuid)

        mockMvc.perform(
            RestDocumentationRequestBuilders.post(collectUrl)
                .header("Authorization", "Bearer $token")
                .content(mapper.writeValueAsString(content))
                .contentType("application/json")
        ).andDo(
            document("createCollect")
        ).andExpectAll(
            status().isOk,
            content().contentType(MediaType.APPLICATION_JSON),
        )
    }
}