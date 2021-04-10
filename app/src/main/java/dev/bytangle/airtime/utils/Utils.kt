package dev.bytangle.airtime.utils

import java.util.*

enum class SelectedUsageSpec {
    DAILY, WEEKLY, MONTHLY
}

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

enum class AirtimeSupportedNetwork {
    GLO, MTN, AIRTEL, NINE_MOBILE, TOTAL, UNKNOWN
}

enum class AirtimeRechargeStatus {
    INVALID, USED, LOADED, NETWORK_ISSUE
}

object AirtimeFilterPatterns {
    val RECHARGE_PIN_PREFIX_REGEX = Regex("^(\\s+|\\w+)?(\\s+|\\w+)?(\\s+|\\w+)?\\*\\d+\\*(\\s+)?(\\d+|\\w+)+(\\s+)?#?(\\s+)?$", setOf(RegexOption.MULTILINE))
    val RECHARGE_PIN_REGEX = Regex("^(\\s+|\\w+)?(\\s+|\\w+)?(\\s+|\\w+)?\\d{3,5}(((-|\\s)\\d{3,5})+)?((-|\\s)\\d{3,5})(\\s+)?$", RegexOption.MULTILINE)
    val AMOUNT_REGEX = Regex("^(\\s+|\\w+)?(\\s+|\\w+)?(\\s+|\\w+)?N?\\d{1,4}0(\\s+)?$", RegexOption.MULTILINE)

    val MTN_RECHARGE_PIN_REGEX_ONE = Regex("^\\d{5}(-|\\s)\\d{5}$", RegexOption.MULTILINE)
    val MTN_RECHARGE_PIN_REGEX_TWO = Regex("^\\d{4}(-|\\s)\\d{4}(-|\\s)\\d{4}(-|\\s)\\d{4}$", RegexOption.MULTILINE)
    val MTN_RECHARGE_PIN_REGEX_THREE = Regex("^\\d{4}(-|\\s)\\d{4}(-|\\s)\\d{4}(-|\\s)\\d{5}$", RegexOption.MULTILINE)
    val NINE_MOBILE_RECHARGE_PIN_REGEX = Regex("^\\d{4}(-|\\s)\\d{4}(-|\\s)\\d{4}(-|\\s)\\d{3}$", RegexOption.MULTILINE)
    val GLO_RECHARGE_PIN_REGEX = Regex("^\\d{5}(-|\\s)\\d{5}(-|\\s)\\d{5}$", RegexOption.MULTILINE)
    val AIRTEL_RECHARGE_PIN_REGEX = Regex("^\\d{4}(-|\\s)\\d{4}(-|\\s)\\d{4}(-|\\s)\\d{4}\$", RegexOption.MULTILINE)
}

fun extractSubstringUsingFirstAndLastIndices(input : CharSequence, pair: Pair<Int, Int>) : CharSequence {
    return input.slice(pair.first..pair.second)
}

fun getFirstAndLastIndexOfInts(input : CharSequence) : Pair<Int, Int> {
    val isIntRegex = Regex("\\d")
    var firstIntIndex : Int = -1
    var lastIntIndex : Int = -1
    input.forEachIndexed { index, char ->
        if (isIntRegex.matches(char.toString())) {
            if (firstIntIndex < 0) firstIntIndex = index
            lastIntIndex = index
        }
    }
    return Pair(firstIntIndex, lastIntIndex)
}