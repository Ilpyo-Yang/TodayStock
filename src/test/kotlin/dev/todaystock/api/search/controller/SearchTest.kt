package dev.todaystock.api.search.controller

import com.fasterxml.jackson.databind.ObjectMapper
import dev.todaystock.api.chat.dto.SearchRequest
import dev.todaystock.api.member.dto.SigninRequest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
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

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs(uriScheme = "https", uriHost = "localhost")
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@DisplayName("Search 관련 API 테스트")
class SearchTest @Autowired constructor(
    private var mockMvc: MockMvc,
    private var mapper: ObjectMapper
) {
    private val searchUrl: String = "/v1/search"
    private val memberUrl: String = "/v1/member"

    private val password: String = "qwer123!@#"

    private var token: String? = null

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


    @Test
    @DisplayName("뉴스 검색 및 최신 정보 저장 테스트")
    fun searchInfo() {
        mockMvc.perform(
            RestDocumentationRequestBuilders.get(searchUrl)
                .header("Authorization", "Bearer $token")
                .queryParam("keyword", "삼성전자")
                .queryParam("infoType", "Company")
                .contentType("application/json")
        ).andDo(
            document("searchInfo")
        ).andExpectAll(
            status().isOk,
            content().contentType(MediaType.APPLICATION_JSON),
        )
    }
}