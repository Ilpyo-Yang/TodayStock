package dev.todaystock.api.common.security

import jakarta.servlet.FilterChain
import jakarta.servlet.ServletRequest
import jakarta.servlet.ServletResponse
import jakarta.servlet.http.HttpServletRequest
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.util.AntPathMatcher
import org.springframework.util.StringUtils
import org.springframework.web.filter.GenericFilterBean

class AuthenticationFilter(
    private val tokenProvider: TokenProvider
): GenericFilterBean() {

    override fun doFilter(
        request: ServletRequest?,
        response: ServletResponse?,
        filterChain: FilterChain?
    ) {
        val permitAllPaths = listOf("/v1/collect/**", "/v1/search", "/v1/member/delete")
        val path = (request as HttpServletRequest).requestURI
        if (permitAllPaths.any { AntPathMatcher().match(it, path) }) {
            val token = resolveToken(request as HttpServletRequest)
            if (token != null && tokenProvider.validateToken(token)) {
                val authentication = tokenProvider.getAuthentication(token)
                SecurityContextHolder.getContext().authentication = authentication
            }
        }
        filterChain?.doFilter(request, response)
    }

    private fun resolveToken(request: HttpServletRequest): String? {
        val bearerToken = request.getHeader("Authorization")
        return if (StringUtils.hasText(bearerToken) &&
            bearerToken.startsWith("Bearer")) {
            bearerToken.substring(7)
        } else {
            null
        }
    }
}