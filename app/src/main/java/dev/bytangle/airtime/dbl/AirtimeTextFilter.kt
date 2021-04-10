package dev.bytangle.airtime.dbl

import com.google.mlkit.vision.text.Text
import dev.bytangle.airtime.utils.*

object AirtimeTextFilter {
    private lateinit var chunks : String
    fun process(text : Text) : AirtimeProcessedResult {
        chunks = text.text
        val rechargePin = extractRechargePin(chunks)
        val rechargePinPrefix = extractRechargePinPrefix(chunks)
        val rechargeAmount = extractRechargeAmount(chunks)
        val assumedRechargePinNetworkAndPrefix = determineRechargePinNetworkAndPrefix(rechargePin = rechargePin ?: "")

        return AirtimeProcessedResult(
            assumedNetwork = assumedRechargePinNetworkAndPrefix.network,
            rechargePin = rechargePin ?: "",
            amount = rechargeAmount ?: "",
            pinPrefix = assumedRechargePinNetworkAndPrefix.prefix
        )
    }
}