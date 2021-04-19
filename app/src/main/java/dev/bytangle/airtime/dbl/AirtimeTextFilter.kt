package dev.bytangle.airtime.dbl

import android.util.Log
import com.google.mlkit.vision.text.Text
import dev.bytangle.airtime.utils.*

object AirtimeTextFilter {
    fun process(text : Text) : AirtimeProcessedResult {
        return processUsingTextBlocks(text.textBlocks)
        //return processUsingFulltext(text.text)
    }

    private fun processUsingTextBlocks(blocks : List<Text.TextBlock>): AirtimeProcessedResult {

        var rechargePin : CharSequence? = null
        var rechargePinPrefix : CharSequence? = null
        var rechargeAmount : CharSequence? = null

        blocks.forEach { textBlock ->
            textBlock.lines.forEach { textLine ->
                if (rechargePin == null) {
                    rechargePin = extractRechargePin(textLine.text)
                }
                if (rechargePinPrefix == null) {
                    rechargePinPrefix = extractRechargePinPrefix(textLine.text)
                }
                if (rechargeAmount == null) {
                    rechargeAmount = extractRechargeAmount(textLine.text)
                }
            }
        }

        val assumedRechargePinNetworkAndPrefix = determineRechargePinNetworkAndPrefix(rechargePin = rechargePin ?: "")

        return AirtimeProcessedResult(
            assumedNetwork = assumedRechargePinNetworkAndPrefix.network,
            rechargePin = rechargePin ?: "",
            amount = rechargeAmount ?: "",
            pinPrefix = assumedRechargePinNetworkAndPrefix.prefix
        )
    }

    private fun processUsingFulltext(chunks : String) : AirtimeProcessedResult {
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