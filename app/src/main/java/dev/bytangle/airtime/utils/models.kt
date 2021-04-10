package dev.bytangle.airtime.utils

// return payload after text recognition result is filtered
data class AirtimeProcessedResult(
    val assumedNetwork : AirtimeSupportedNetwork,
    val rechargePin : String,
    val amount : String,
    val pinPrefix: AirtimePinPrefix
)

data class AirtimePinPrefix(
    val network: AirtimeSupportedNetwork,
    val suffix : String
)

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