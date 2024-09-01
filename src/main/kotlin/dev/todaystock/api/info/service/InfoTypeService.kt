package dev.todaystock.api.info.service

import dev.todaystock.api.common.exception.InvalidRequestException
import dev.todaystock.api.info.dto.InfoTypeRequest
import dev.todaystock.api.info.dto.InfoTypeResponse
import dev.todaystock.api.info.entity.InfoType
import dev.todaystock.api.info.repository.CompanyRepository
import dev.todaystock.api.info.repository.CountryRepository
import dev.todaystock.api.info.repository.ThemeRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime
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

//    fun findCompanyTicker(ticker: String): InfoTypeResponse? {
//        if(ticker == null) {
//            throw BadRequestException("Company ticker needed!")
//        }
//        return InfoTypeResponse.fromCompany(companyRepository.findByTicker(ticker))
//    }

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
        if(companyRepository.findByName(request.name) != null) {
            throw InvalidRequestException("Company with name ${request.name} already exists")
        }
        return InfoTypeResponse.fromCompany(companyRepository.save(InfoTypeRequest.toCompany(request)))
    }

    fun createCountryInfo(request: InfoTypeRequest): InfoTypeResponse? {
        if(countryRepository.findByName(request.name) != null) {
            throw InvalidRequestException("Country with name ${request.name} already exists")
        }
        return InfoTypeResponse.fromCompany(companyRepository.save(InfoTypeRequest.toCompany(request)))
    }

    fun createThemeInfo(request: InfoTypeRequest): InfoTypeResponse? {
        if(themeRepository.findByName(request.name) != null) {
            throw InvalidRequestException("Theme with name ${request.name} already exists")
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
            company.get().deletedDate = LocalDateTime.now()
            return true
        } else {
            throw IllegalArgumentException("Company Info not found")
        }
    }

    fun deleteCountry(uuid: UUID): Boolean {
        val country = countryRepository.findById(uuid)
        if (country.isPresent) {
            country.get().deletedDate = LocalDateTime.now()
            return true
        } else {
            throw IllegalArgumentException("Country Info not found")
        }
    }

    fun deleteTheme(uuid: UUID): Boolean {
        val theme = themeRepository.findById(uuid)
        if (theme.isPresent) {
            theme.get().deletedDate = LocalDateTime.now()
            return true
        } else {
            throw IllegalArgumentException("Theme Info not found")
        }
    }
}