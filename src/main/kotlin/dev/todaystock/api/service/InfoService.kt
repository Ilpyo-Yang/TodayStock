package dev.todaystock.api.service

import dev.todaystock.api.dto.info.InfoRequest
import dev.todaystock.api.dto.info.InfoResponse
import dev.todaystock.api.entity.type.InfoType
import dev.todaystock.api.repository.CompanyInfoRepository
import dev.todaystock.api.repository.CountryInfoRepository
import dev.todaystock.api.repository.ThemeInfoRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.sql.Timestamp
import java.util.*

@Service
class InfoService(
    private val companyInfoRepository: CompanyInfoRepository,
    private val countryInfoRepository: CountryInfoRepository,
    private val themeInfoRepository: ThemeInfoRepository
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
            InfoType.Company -> InfoResponse.fromCompanyInfo(companyInfoRepository.save(InfoRequest.toCompanyInfo(request)))
            InfoType.Country -> InfoResponse.fromCountryInfo(countryInfoRepository.save(InfoRequest.toCountryInfo(request)))
            else -> {
                InfoResponse.fromThemeInfo(themeInfoRepository.save(InfoRequest.toThemeInfo(request)))
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
            companyInfo.get().deletedDate = Timestamp(System.currentTimeMillis())
            return true
        } else {
            throw IllegalArgumentException("Company Info not found")
        }
    }

    fun deleteCountryInfo(uuid: UUID): Boolean {
        val countryInfo = countryInfoRepository.findById(uuid)
        if (countryInfo.isPresent) {
            countryInfo.get().deletedDate = Timestamp(System.currentTimeMillis())
            return true
        } else {
            throw IllegalArgumentException("Country Info not found")
        }
    }

    fun deleteThemeInfo(uuid: UUID): Boolean {
        val themeInfo = themeInfoRepository.findById(uuid)
        if (themeInfo.isPresent) {
            themeInfo.get().deletedDate = Timestamp(System.currentTimeMillis())
            return true
        } else {
            throw IllegalArgumentException("Theme Info not found")
        }
    }
}