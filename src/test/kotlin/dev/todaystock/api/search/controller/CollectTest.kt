package dev.todaystock.api.search.controller

import com.fasterxml.jackson.databind.ObjectMapper
import dev.todaystock.api.collect.dto.CollectRequest
import dev.todaystock.api.info.entity.InfoType
import dev.todaystock.api.member.dto.SigninRequest
import org.junit.jupiter.api.*
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
import java.util.*

@SpringBootTest
@Transactional
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
@AutoConfigureMockMvc
@AutoConfigureRestDocs(uriScheme = "https", uriHost = "localhost")
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("Collect API 테스트")
class CollectTest(
    private var mockMvc: MockMvc,
    private var mapper: ObjectMapper
) {
    private val memberUrl: String = "/v1/member"
    private val collectUrl: String = "/v1/collect"

    private val email: String = "test@gmail.com"
    private val password: String = "qwer123!@#"

    private var token: String? = null
    private var memberUuid: String = UUID.randomUUID().toString()
    private var infoUuid: String = UUID.randomUUID().toString()

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
    @DisplayName("회원이 저장한 collect 종류 조회 테스트")
    fun findCollectByMemberUuid() {
        mockMvc.perform(
            RestDocumentationRequestBuilders.get(collectUrl)
                .header("Authorization", "Bearer $token")
                .queryParam("memberUuid", memberUuid)
                .contentType("application/json")
        ).andDo(
            document("findCollectByMemberUuid")
        ).andExpectAll(
            status().isOk,
            content().contentType(MediaType.APPLICATION_JSON),
        )
    }

    @Test
    @DisplayName("회원이 저장한 특정 정보타입 collect 종류 조회 테스트")
    fun findCollectByMemberUuidAndType() {
        mockMvc.perform(
            RestDocumentationRequestBuilders.get(collectUrl + "/type")
                .header("Authorization", "Bearer $token")
                .queryParam("memberUuid", memberUuid)
                .queryParam("infoType", InfoType.Company.name)
                .contentType("application/json")
        ).andDo(
            document("findCollectByMemberUuidAndType")
        ).andExpectAll(
            status().isOk,
            content().contentType(MediaType.APPLICATION_JSON),
        )
    }

    @Test
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