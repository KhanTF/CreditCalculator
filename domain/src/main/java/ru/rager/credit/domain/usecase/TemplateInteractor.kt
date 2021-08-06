package ru.rager.credit.domain.usecase

import ru.rager.credit.domain.entity.PeriodValueEntity
import ru.rager.credit.domain.entity.enums.PeriodType
import javax.inject.Inject

class TemplateInteractor @Inject constructor() {

    fun getPretermTemplates(): List<PeriodValueEntity> {
        return listOf(
            PeriodValueEntity.getSingle(),
            PeriodValueEntity(1, PeriodType.MONTH),
            PeriodValueEntity(2, PeriodType.MONTH),
            PeriodValueEntity(1, PeriodType.QUARTER),
            PeriodValueEntity(6, PeriodType.MONTH),
            PeriodValueEntity(1, PeriodType.YEAR)
        )
    }

    fun getTermTemplates(): List<PeriodValueEntity> {
        return listOf(
            PeriodValueEntity(1, PeriodType.YEAR),
            PeriodValueEntity(3, PeriodType.YEAR),
            PeriodValueEntity(5, PeriodType.YEAR),
            PeriodValueEntity(10, PeriodType.YEAR),
            PeriodValueEntity(15, PeriodType.YEAR),
            PeriodValueEntity(20, PeriodType.YEAR),
            PeriodValueEntity(25, PeriodType.YEAR)
        )
    }

}