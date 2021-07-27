package ru.rager.credit.domain.entity.enums

enum class EarlyPaymentPeriodType(val value: Int) {
    SINGLE(0), EVERY_MONTH(1), EVERY_QUARTER(3), EVERY_HALF_YEAR(6), EVERY_YEAR(12)
}