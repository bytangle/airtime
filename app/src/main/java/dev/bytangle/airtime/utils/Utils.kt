package dev.bytangle.airtime.utils

import java.util.*

enum class SelectedUsageSpec {
    DAILY, WEEKLY, MONTHLY
}

data class CalculatedAirtimeUsages(
    val date : String,
    val usages: List<CalculatedAirtimeUsage>
)

data class CalculatedAirtimeUsage(
    val totalAmount: Double,
    val network: AirtimeSupportedNetwork,
    val recharges : List<Airtime> // this includes all the airtime used in the calculation
)

data class Airtime(
    val network : AirtimeSupportedNetwork,
    val amount : Double,
    val date : String,
    val rechargeStatus: AirtimeRechargeStatus
)

enum class AirtimeSupportedNetwork {
    GLO, MTN, AIRTEL, NINE_MOBILE, TOTAL //
}

enum class AirtimeRechargeStatus {
    INVALID, USED, LOADED, NETWORK_ISSUE
}