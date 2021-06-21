package ru.rager.credit.domain.entity.enums

enum class CreditPeriodType(val value: Int) {
    EVERY_YEAR(12),
    EVERY_QUARTER(3),
    EVERY_MONTH(1);
}