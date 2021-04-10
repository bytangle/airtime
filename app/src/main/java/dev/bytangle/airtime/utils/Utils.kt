package dev.bytangle.airtime.utils

/*
  * Used to get the prefix and network given a particular rechargePin
 */
fun determineRechargePinNetworkAndPrefix(rechargePin : CharSequence) : AirtimeNetworkAndPinPrefix {
       return when {
           AirtimeFilterPatterns.GLO_RECHARGE_PIN_REGEX.matches(rechargePin) -> {
               AirtimeNetworkAndPinPrefix(
                   prefix = AirtimeRechargePrefix.GLO_PIN_PREFIX,
                   network = AirtimeSupportedNetwork.GLO
               )
           }

           AirtimeFilterPatterns.NINE_MOBILE_RECHARGE_PIN_REGEX.matches(rechargePin) -> {
               AirtimeNetworkAndPinPrefix(
                   prefix = AirtimeRechargePrefix.NINE_MOBILE_PIN_PREFIX,
                   network = AirtimeSupportedNetwork.NINE_MOBILE
               )
           }

           AirtimeFilterPatterns.AIRTEL_RECHARGE_PIN_REGEX.matches(rechargePin) -> {
               AirtimeNetworkAndPinPrefix(
                   prefix = AirtimeRechargePrefix.AIRTEL_PIN_PREFIX,
                   network = AirtimeSupportedNetwork.AIRTEL
               )
           }

            AirtimeFilterPatterns.MTN_RECHARGE_PIN_REGEX.matches(rechargePin) -> {
                val mtnPrefix = when(rechargePin.length) {
                    // - included in the count
                    11 -> AirtimeRechargePrefix.MTN_10_DIGITS_PIN_PREFIX
                    19 -> AirtimeRechargePrefix.MTN_16_DIGITS_PIN_PREFIX
                    20 -> AirtimeRechargePrefix.MTN_17_DIGITS_PIN_PREFIX
                    else -> ""
                }

                AirtimeNetworkAndPinPrefix(
                    prefix = mtnPrefix,
                    network = AirtimeSupportedNetwork.MTN
                )
            }
           else -> {
               AirtimeNetworkAndPinPrefix(network = AirtimeSupportedNetwork.UNKNOWN, prefix = "")
           }
       }
}

/*
Extraction Functions
 */

fun extractRechargePin(rawInput : String) : CharSequence? {
    return filter(rawInput, AirtimeFilterPatterns.RECHARGE_PIN_REGEX).firstOrNull()?.let {
        return extractSubstringUsingFirstAndLastIndices(it.value, getFirstAndLastIndexOfInts(it.value))
    }
}

fun extractRechargePinPrefix(rawInput: String) : CharSequence? {
    return filter(rawInput, AirtimeFilterPatterns.RECHARGE_PIN_PREFIX_REGEX).firstOrNull()?.let {
        val extractedString = extractSubstringUsingFirstAndLastIndices(it.value, getFirstAndLastIndexOfRegexSpec(it.value, Regex("\\*")))

        /***
         * The trimming below is implemented to remove unnecessary strings like in
         * results where maybe the serial number on the recharge card is also filtered and returned with the main recharge card pin
         */
        return extractedString.split(Regex("\\s")).subList(1, extractedString.length - 1).joinToString { "" }
    }
}

fun extractRechargeAmount(rawInput : String) : CharSequence? {
    return filter(rawInput, AirtimeFilterPatterns.AMOUNT_REGEX).firstOrNull()?.let {
        return extractSubstringUsingFirstAndLastIndices(it.value, getFirstAndLastIndexOfInts(it.value))
    }
}

object AirtimeFilterPatterns {
    val RECHARGE_PIN_PREFIX_REGEX = Regex("^(\\s+|\\w+)?(\\s+|\\w+)?(\\s+|\\w+)?\\*\\d+\\*(\\s+)?(\\d+|\\w+)+(\\s+)?#?(\\s+)?$", setOf(RegexOption.MULTILINE))
    val RECHARGE_PIN_REGEX = Regex("^(\\s+|\\w+)?(\\s+|\\w+)?(\\s+|\\w+)?\\d{3,5}(((-|\\s)\\d{3,5})+)?((-|\\s)\\d{3,5})(\\s+)?$", RegexOption.MULTILINE)
    val AMOUNT_REGEX = Regex("^(\\s+|\\w+)?(\\s+|\\w+)?(\\s+|\\w+)?N?\\d{1,4}0(\\s+)?$", RegexOption.MULTILINE)

    val MTN_RECHARGE_PIN_REGEX = Regex("^((\\d{5}(-|\\s)\\d{5})|(\\d{4}(-|\\s)\\d{4}(-|\\s)\\d{4}(-|\\s)\\d{4})|(\\d{4}(-|\\s)\\d{4}(-|\\s)\\d{4}(-|\\s)\\d{5}))$", RegexOption.MULTILINE)
    val NINE_MOBILE_RECHARGE_PIN_REGEX = Regex("^\\d{4}(-|\\s)\\d{4}(-|\\s)\\d{4}(-|\\s)\\d{3}$", RegexOption.MULTILINE)
    val GLO_RECHARGE_PIN_REGEX = Regex("^\\d{5}(-|\\s)\\d{5}(-|\\s)\\d{5}$", RegexOption.MULTILINE)
    val AIRTEL_RECHARGE_PIN_REGEX = Regex("^\\d{4}(-|\\s)\\d{4}(-|\\s)\\d{4}(-|\\s)\\d{4}\$", RegexOption.MULTILINE)
}

object AirtimeRechargePrefix {
    val MTN_10_DIGITS_PIN_PREFIX = "*3551*"
    val MTN_16_DIGITS_PIN_PREFIX = "*2282*"
    val MTN_17_DIGITS_PIN_PREFIX = "*555*"
    val GLO_PIN_PREFIX = "*123*"
    val AIRTEL_PIN_PREFIX = "*126*"
    val NINE_MOBILE_PIN_PREFIX = "*222*"
}

fun filter(rawInput: String, spec : Regex) : Sequence<MatchResult> {
    return spec.findAll(rawInput)
}

fun extractSubstringUsingFirstAndLastIndices(input : CharSequence, pair: Pair<Int, Int>) : CharSequence {
    return input.slice(pair.first..pair.second)
}

fun getFirstAndLastIndexOfInts(input : CharSequence) : Pair<Int, Int> {
    val isIntRegex = Regex("\\d")
    return getFirstAndLastIndexOfRegexSpec(input, isIntRegex)
}

private fun getFirstAndLastIndexOfRegexSpec(input : CharSequence, regex: Regex) : Pair<Int, Int> {
    var firstIntIndex : Int = -1
    var lastIntIndex : Int = -1
    input.forEachIndexed { index, char ->
        if (regex.matches(char.toString())) {
            if (firstIntIndex < 0) firstIntIndex = index
            lastIntIndex = index
        }
    }
    return Pair(firstIntIndex, lastIntIndex)
}