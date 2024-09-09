package dev.todaystock.api.search.controller

import com.fasterxml.jackson.databind.ObjectMapper
import dev.todaystock.api.chat.dto.SearchRequest
import dev.todaystock.api.chat.service.SearchService
import dev.todaystock.api.info.dto.InfoRequest
import dev.todaystock.api.info.dto.InfoTypeRequest
import dev.todaystock.api.info.entity.InfoType
import dev.todaystock.api.member.dto.MemberRequest
import dev.todaystock.api.member.dto.SigninRequest
import org.junit.jupiter.api.Test
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
class ApiControllerTest(
    private var mockMvc: MockMvc,
    private var ObjectMapper: ObjectMapper,
    private val searchService: SearchService
) {
    private val searchUrl = "/v1/search"
    private val memberUrl = "/v1/member"
    private val infoUrl = "/v1/info/{infoType}/detail"
    private val infoTypeUrl = "/v1/info/{infoType}"
    private val collectUrl = "/v1/collect"


//    member api test

    @Test
    fun signup() {
        val content = MemberRequest(null, "tester@gmail.com", "qwer123!@#",
            null, "mockmvc", null, null)

        mockMvc.perform(
            RestDocumentationRequestBuilders.post(memberUrl+"signup")
                .content(ObjectMapper.writeValueAsString(content))
        ).andDo(
            document("signup")
        ).andExpectAll(
            status().isOk,
            content().contentType(MediaType.APPLICATION_JSON),
        )
    }

    @Test
    fun signin() {
        val content = SigninRequest("tester@gmail.com", "qwer123!@#")

        mockMvc.perform(
            RestDocumentationRequestBuilders.post(memberUrl+"signin")
                .content(ObjectMapper.writeValueAsString(content))
        ).andDo(
            document("signup")
        ).andExpectAll(
            status().isOk,
            content().contentType(MediaType.APPLICATION_JSON),
        )
    }



//    infoType api test

    @Test
    fun findByInfoType() {
        mockMvc.perform(
            RestDocumentationRequestBuilders.get(infoTypeUrl, InfoType.Company)
        ).andDo(
            document("findByInfoType")
        ).andExpectAll(
            status().isOk,
            content().contentType(MediaType.APPLICATION_JSON),
        )
    }

    @Test
    fun createInfoType() {
        val content = InfoTypeRequest(null, "name", "ticker", "profile")

        mockMvc.perform(
            RestDocumentationRequestBuilders.post(infoTypeUrl, InfoType.Company)
                .content(ObjectMapper.writeValueAsString(content))
        ).andDo(
            document("findByInfoType")
        ).andExpectAll(
            status().isOk,
            content().contentType(MediaType.APPLICATION_JSON),
        )
    }

    @Test
    fun deleteInfoType() {
        mockMvc.perform(
            RestDocumentationRequestBuilders.delete(infoTypeUrl, InfoType.Company)
                .content("2b8d43a8-4189-42ee-9b2e-891a9564663e")
        ).andDo(
            document("findByInfoType")
        ).andExpectAll(
            status().isOk,
            content().contentType(MediaType.APPLICATION_JSON),
        )
    }


//    info api test

    @Test
    fun findByInfoTypeUuid() {
        mockMvc.perform(
            RestDocumentationRequestBuilders.get(infoUrl, InfoType.Company)
                .content("2b8d43a8-4189-42ee-9b2e-891a9564663e")
        ).andDo(
            document("findByInfoTypeUuid")
        ).andExpectAll(
            status().isOk,
            content().contentType(MediaType.APPLICATION_JSON),
        )
    }

    @Test
    fun createInfo() {
        val content = InfoRequest(null, "2b8d43a8-4189-42ee-9b2e-891a9564663e", "infoTitle",
            "http://localhost:8080/info", "info", "Fri, 06 Sep 2024 22:01:00 +0900")

        mockMvc.perform(
            RestDocumentationRequestBuilders.post(infoUrl, InfoType.Company)
                .content(ObjectMapper.writeValueAsString(content))
        ).andDo(
            document("createInfo")
        ).andExpectAll(
            status().isOk,
            content().contentType(MediaType.APPLICATION_JSON),
        )
    }

    @Test
    fun deleteInfo() {
        mockMvc.perform(
            RestDocumentationRequestBuilders.delete(infoUrl, InfoType.Company)
                .content("2b8d43a8-4189-42ee-9b2e-891a9564663e")
        ).andDo(
            document("deleteInfo")
        ).andExpectAll(
            status().isOk,
            content().contentType(MediaType.APPLICATION_JSON),
        )
    }



//    search api test

    @Test
    fun searchInfo() {
        val content = SearchRequest("삼성전자", "Company")

        mockMvc.perform(
            RestDocumentationRequestBuilders.get(searchUrl)
                .content(ObjectMapper.writeValueAsString(content))
        ).andDo(
            document("searchInfo")
        ).andExpectAll(
            status().isOk,
            content().contentType(MediaType.APPLICATION_JSON),
        )
    }
}