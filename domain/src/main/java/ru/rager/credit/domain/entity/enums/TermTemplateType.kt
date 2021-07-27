package ru.rager.credit.domain.entity.enums

enum class TermTemplateType(val value: Int, val period: CreditPeriodType) {
    YEAR_1(1, CreditPeriodType.EVERY_YEAR),
    YEAR_3(3, CreditPeriodType.EVERY_YEAR),
    YEAR_5(5, CreditPeriodType.EVERY_YEAR),
    YEAR_10(10, CreditPeriodType.EVERY_YEAR),
    YEAR_15(15, CreditPeriodType.EVERY_YEAR),
    YEAR_20(20, CreditPeriodType.EVERY_YEAR),
    YEAR_25(25, CreditPeriodType.EVERY_YEAR),
}