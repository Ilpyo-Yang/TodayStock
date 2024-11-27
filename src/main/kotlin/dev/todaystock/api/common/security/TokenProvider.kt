package dev.todaystock.api.common.security

import dev.todaystock.api.common.dto.TokenDto
import dev.todaystock.api.common.exception.InvalidTokenException
import io.jsonwebtoken.*
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import io.jsonwebtoken.security.SecurityException
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component
import java.util.*

const val EXPIRATION_MILLISECONDS: Long = 1000 * 60 * 60 * 12

@Component
class TokenProvider {
    @Value("\${jwt.secret}")
    lateinit var secretKey: String

    private val key by lazy {
        Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey))
    }

    fun createToken(authentication: Authentication): TokenDto {
        val authorities: String = authentication
            .authorities
            .joinToString(",", transform = GrantedAuthority::getAuthority)
        val now = Date()
        val accessExpiration = Date(now.time + EXPIRATION_MILLISECONDS)
        val accessToken = Jwts.builder()
            .setSubject(authentication.name)
            .claim("auth", authorities)
            .setIssuedAt(now)
            .setExpiration(accessExpiration)
            .signWith(key, SignatureAlgorithm.HS256)
            .compact()
        return TokenDto("Bearer", accessToken)
    }

    fun getAuthentication(token: String): Authentication {
        val claims: Claims = getClaims(token)
        val auth = claims["auth"] ?: throw RuntimeException(" .")
        val authorities: Collection<GrantedAuthority> = (auth as String)
            .split(",")
            .map { SimpleGrantedAuthority(it) }
        val principal: UserDetails = User(claims.subject, "", authorities)
        return UsernamePasswordAuthenticationToken(principal, "", authorities)
    }

    fun validateToken(token: String): Boolean {
        try {
            getClaims(token)
            return true
        } catch (e: Exception) {
            println(e.message)
            when (e) {
                is SecurityException -> {
                    throw InvalidTokenException("SecurityException, Invalid JWT Token")
                }
                is MalformedJwtException -> {
                    throw InvalidTokenException("Malformed JWT Token")
                }
                is ExpiredJwtException -> {
                    throw InvalidTokenException("Expired JWT Token")
                }
                is UnsupportedJwtException -> {
                    throw InvalidTokenException("Unsupported JWT Token")
                }
                is IllegalArgumentException -> {
                    throw InvalidTokenException("Unsupported JWT Token")
                }
                else -> {
                    throw InvalidTokenException("Invalid JWT Token")
                }
            }
        }
    }

    private fun getClaims(token: String): Claims =
        Jwts.parserBuilder()
            .setSigningKey(key)
            .build()
            .parseClaimsJws(token)
            .body
}