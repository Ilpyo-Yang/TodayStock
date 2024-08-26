package dev.todaystock.api.info.service

import dev.todaystock.api.info.dto.InfoRequest
import dev.todaystock.api.info.dto.InfoResponse
import dev.todaystock.api.info.entity.InfoType
import dev.todaystock.api.info.repository.*
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime
import java.util.*
import kotlin.NoSuchElementException

@Service
class InfoService(
    private val companyInfoRepository: CompanyInfoRepository,
    private val countryInfoRepository: CountryInfoRepository,
    private val themeInfoRepository: ThemeInfoRepository,
    private val companyRepository: CompanyRepository,
    private val countryRepository: CountryRepository,
    private val themeRepository: ThemeRepository
) {
    fun findAll(infoUuid: UUID, infoType: InfoType): List<InfoResponse?> {
        when(infoType) {
            InfoType.Company -> return InfoResponse.fromCompanyInfoList(companyInfoRepository.findByUuid(infoUuid))
            InfoType.Country -> return InfoResponse.fromCountryInfoList(countryInfoRepository.findByUuid(infoUuid))
            else -> {
                return InfoResponse.fromThemeInfoList(themeInfoRepository.findByUuid(infoUuid))
            }
        }
    }

    fun create(infoType: InfoType, request: InfoRequest): InfoResponse? {
        return when(infoType) {
            InfoType.Company -> {
                val company = companyRepository.findById(request.typeUuid)
                    .orElseThrow { NoSuchElementException("NOT registered company uuid") }
                InfoResponse.fromCompanyInfo(companyInfoRepository.save(InfoRequest.toCompanyInfo(request, company)))
            }
            InfoType.Country -> {
                val country = countryRepository.findById(request.typeUuid)
                    .orElseThrow { NoSuchElementException("NOT registered company uuid") }
                InfoResponse.fromCountryInfo(countryInfoRepository.save(InfoRequest.toCountryInfo(request, country)))
            }
            else -> {
                val theme = themeRepository.findById(request.typeUuid)
                    .orElseThrow { NoSuchElementException("NOT registered company uuid") }
                InfoResponse.fromThemeInfo(themeInfoRepository.save(InfoRequest.toThemeInfo(request, theme)))
            }
        }
    }

    @Transactional
    fun delete(infoType: InfoType, uuid: UUID): Boolean {
        return when(infoType) {
            InfoType.Company -> deleteCompanyInfo(uuid)
            InfoType.Country -> deleteCountryInfo(uuid)
            else -> {
                deleteThemeInfo(uuid)
            }
        }
    }

    fun deleteCompanyInfo(uuid: UUID): Boolean {
        val companyInfo = companyInfoRepository.findById(uuid)
        if (companyInfo.isPresent) {
            companyInfo.get().deletedDate = LocalDateTime.now()
            return true
        } else {
            throw IllegalArgumentException("Company Info not found")
        }
    }

    fun deleteCountryInfo(uuid: UUID): Boolean {
        val countryInfo = countryInfoRepository.findById(uuid)
        if (countryInfo.isPresent) {
            countryInfo.get().deletedDate = LocalDateTime.now()
            return true
        } else {
            throw IllegalArgumentException("Country Info not found")
        }
    }

    fun deleteThemeInfo(uuid: UUID): Boolean {
        val themeInfo = themeInfoRepository.findById(uuid)
        if (themeInfo.isPresent) {
            themeInfo.get().deletedDate = LocalDateTime.now()
            return true
        } else {
            throw IllegalArgumentException("Theme Info not found")
        }
    }
}