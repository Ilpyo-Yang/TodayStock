package dev.todaystock.api.info.service

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

    @Transactional
    fun create(infoType: InfoType, request: InfoTypeRequest): InfoTypeResponse? {
        when(infoType) {
            InfoType.Company -> {
                companyRepository.findByName(request.name)
                    ?.let { throw NoSuchElementException("Company with name ${request.name} already exists") }
                    ?: return InfoTypeResponse.fromCompany(companyRepository.save(InfoTypeRequest.toCompany(request)))
            }
            InfoType.Country -> {
                countryRepository.findByName(request.name)
                    ?.let { throw NoSuchElementException("Country with name ${request.name} already exists") }
                    ?: return InfoTypeResponse.fromCountry(countryRepository.save(InfoTypeRequest.toCountry(request)))

            }
            else -> {
                themeRepository.findByName(request.name)
                    ?.let { throw NoSuchElementException("Theme with name ${request.name} already exists") }
                    ?: return InfoTypeResponse.fromTheme(themeRepository.save(InfoTypeRequest.toTheme(request)))
            }
        }
    }

    @Transactional
    fun delete(infoType: InfoType, uuid: UUID): Boolean {
        when(infoType) {
            InfoType.Company -> {
                var company = companyRepository.findById(uuid)
                    .orElseThrow { IllegalArgumentException("Company Info NOT found") }
                company.deletedDate = LocalDateTime.now()
            }
            InfoType.Country -> {
                var country = countryRepository.findById(uuid)
                    .orElseThrow { IllegalArgumentException("Country Info NOT found") }
                country.deletedDate = LocalDateTime.now()
            }
            else -> {
                var theme = themeRepository.findById(uuid)
                    .orElseThrow { IllegalArgumentException("Theme Info NOT found") }
                theme.deletedDate = LocalDateTime.now()
            }
        }
        return true
    }
}