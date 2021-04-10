package dev.bytangle.airtime.utils

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
        return extractSubstringUsingFirstAndLastIndices(it.value, getFirstAndLastIndexOfChar(it.value, "\\*"))
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

    val MTN_RECHARGE_PIN_REGEX_ONE = Regex("^\\d{5}(-|\\s)\\d{5}$", RegexOption.MULTILINE)
    val MTN_RECHARGE_PIN_REGEX_TWO = Regex("^\\d{4}(-|\\s)\\d{4}(-|\\s)\\d{4}(-|\\s)\\d{4}$", RegexOption.MULTILINE)
    val MTN_RECHARGE_PIN_REGEX_THREE = Regex("^\\d{4}(-|\\s)\\d{4}(-|\\s)\\d{4}(-|\\s)\\d{5}$", RegexOption.MULTILINE)
    val NINE_MOBILE_RECHARGE_PIN_REGEX = Regex("^\\d{4}(-|\\s)\\d{4}(-|\\s)\\d{4}(-|\\s)\\d{3}$", RegexOption.MULTILINE)
    val GLO_RECHARGE_PIN_REGEX = Regex("^\\d{5}(-|\\s)\\d{5}(-|\\s)\\d{5}$", RegexOption.MULTILINE)
    val AIRTEL_RECHARGE_PIN_REGEX = Regex("^\\d{4}(-|\\s)\\d{4}(-|\\s)\\d{4}(-|\\s)\\d{4}\$", RegexOption.MULTILINE)
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

fun getFirstAndLastIndexOfChar(input : CharSequence, pattern : CharSequence) : Pair<Int, Int> {
    val isCharRegex = Regex("$pattern")
    return getFirstAndLastIndexOfRegexSpec(input, isCharRegex)
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