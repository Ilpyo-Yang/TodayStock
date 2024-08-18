package dev.todaystock.api.service

import dev.todaystock.api.dto.info.InfoTypeRequest
import dev.todaystock.api.dto.info.InfoTypeResponse
import dev.todaystock.api.entity.type.InfoType
import dev.todaystock.api.repository.CompanyRepository
import dev.todaystock.api.repository.CountryRepository
import dev.todaystock.api.repository.ThemeRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.sql.Timestamp
import java.util.*

@Service
class InfoTypeService(
    private val companyRepository: CompanyRepository,
    private val countryRepository: CountryRepository,
    private val themeRepository: ThemeRepository
) {
    fun findAll(infoType: InfoType): List<InfoTypeResponse?> {
        when(infoType) {
            InfoType.Company -> return InfoTypeResponse.fromCompanies(companyRepository.findAll())
            InfoType.Country -> return InfoTypeResponse.fromCountries(countryRepository.findAll())
            else -> {
                return InfoTypeResponse.fromThemes(themeRepository.findAll())
            }
        }
    }

    fun create(infoType: InfoType, request: InfoTypeRequest): InfoTypeResponse? {
        return when(infoType) {
            InfoType.Company -> createCompanyInfo(request)
            InfoType.Country -> createCountryInfo(request)
            else -> {
                createThemeInfo(request)
            }
        }
    }

    fun createCompanyInfo(request: InfoTypeRequest): InfoTypeResponse? {
        if(companyRepository.findByNameLike(request.name) != null) {
            throw RuntimeException("Company with name ${request.name} already exists")
        }
        return InfoTypeResponse.fromCompany(companyRepository.save(InfoTypeRequest.toCompany(request)))
    }

    fun createCountryInfo(request: InfoTypeRequest): InfoTypeResponse? {
        if(countryRepository.findByNameLike(request.name) != null) {
            throw RuntimeException("Company with name ${request.name} already exists")
        }
        return InfoTypeResponse.fromCompany(companyRepository.save(InfoTypeRequest.toCompany(request)))
    }

    fun createThemeInfo(request: InfoTypeRequest): InfoTypeResponse? {
        if(themeRepository.findByNameLike(request.name) != null) {
            throw RuntimeException("Company with name ${request.name} already exists")
        }
        return InfoTypeResponse.fromCompany(companyRepository.save(InfoTypeRequest.toCompany(request)))
    }


    @Transactional
    fun delete(infoType: InfoType, uuid: UUID): Boolean {
        return when(infoType) {
            InfoType.Company -> deleteCompany(uuid)
            InfoType.Country -> deleteCountry(uuid)
            else -> {
                deleteTheme(uuid)
            }
        }
    }

    fun deleteCompany(uuid: UUID): Boolean {
        val company = companyRepository.findById(uuid)
        if (company.isPresent) {
            company.get().deletedDate = Timestamp(System.currentTimeMillis())
            return true
        } else {
            throw IllegalArgumentException("Company Info not found")
        }
    }

    fun deleteCountry(uuid: UUID): Boolean {
        val country = countryRepository.findById(uuid)
        if (country.isPresent) {
            country.get().deletedDate = Timestamp(System.currentTimeMillis())
            return true
        } else {
            throw IllegalArgumentException("Country Info not found")
        }
    }

    fun deleteTheme(uuid: UUID): Boolean {
        val theme = themeRepository.findById(uuid)
        if (theme.isPresent) {
            theme.get().deletedDate = Timestamp(System.currentTimeMillis())
            return true
        } else {
            throw IllegalArgumentException("Theme Info not found")
        }
    }
}