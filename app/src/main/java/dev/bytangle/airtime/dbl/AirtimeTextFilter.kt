package dev.bytangle.airtime.dbl

import com.google.mlkit.vision.text.Text
import dev.bytangle.airtime.utils.AirtimeFilterPatterns
import dev.bytangle.airtime.utils.AirtimeProcessedResult

object AirtimeTextFilter {
    private lateinit var chunks : String
    fun process(text : Text) //: List<AirtimeProcessedResult> {
    {
        chunks = text.text
        val rechargePin = filterRechargePin(chunks)
    }

    fun filterRechargePin(chunks : String) : String? {
        val result = filter(chunks, AirtimeFilterPatterns.RECHARGE_PIN_REGEX)
        return result.firstOrNull()?.value
    }

    private fun filter(chunks : String, spec : Regex) : Sequence<MatchResult> {
        return spec.findAll(chunks)
    }
}