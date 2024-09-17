package dev.todaystock.api.search.controller

import com.fasterxml.jackson.databind.ObjectMapper
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
import org.springframework.transaction.annotation.Transactional
import java.util.*

@SpringBootTest
@Transactional
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
@AutoConfigureMockMvc
@AutoConfigureRestDocs(uriScheme = "https", uriHost = "localhost")
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("Member 관련 API 테스트")
class MemberTest @Autowired constructor(
    private var mockMvc: MockMvc,
    private var mapper: ObjectMapper
) {
    private val memberUrl: String = "/v1/member"

    private val newEmail: String = "stock@gmail.com"
    private val email: String = "test@gmail.com"
    private val password: String = "qwer123!@#"

    private var token: String? = null
    private var memberUuid: String = UUID.randomUUID().toString()

    fun getToken() {
        // signin before tests
        val content = SigninRequest(email, password)

        val loginResponse = mockMvc.perform(
            RestDocumentationRequestBuilders.post(memberUrl+"/signin")
                .content(mapper.writeValueAsString(content))
                .contentType("application/json")
        ).andExpectAll(
            status().isOk,
            content().contentType(MediaType.APPLICATION_JSON),
        ).andReturn()

        val jsonResponse =  ObjectMapper().readTree(loginResponse.response.contentAsString)
        token = jsonResponse.get("data").get("accessToken").asText()
    }


    @Test
    @Order(1)
    @DisplayName("회원가입 테스트")
    fun signup() {
        val content = MemberRequest(memberUuid, newEmail, password, null, "mockmvc", null, null)

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
            document("signin")
        ).andExpectAll(
            status().isOk,
            content().contentType(MediaType.APPLICATION_JSON),
        )
    }

    @Test
    @Order(3)
    @DisplayName("회원삭제 테스트")
    fun deleteMember() {
        getToken()

        mockMvc.perform(
            RestDocumentationRequestBuilders.delete(memberUrl)
                .content(email)
                .contentType("application/json")
        ).andDo(
            document("deleteMember")
        ).andExpectAll(
            status().isOk,
            content().contentType(MediaType.APPLICATION_JSON),
        )
    }
}