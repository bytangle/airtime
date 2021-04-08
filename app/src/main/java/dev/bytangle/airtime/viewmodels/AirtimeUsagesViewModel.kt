package dev.bytangle.airtime.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dev.bytangle.airtime.utils.AirtimeSupportedNetwork
import dev.bytangle.airtime.utils.CalculatedAirtimeUsage
import dev.bytangle.airtime.utils.CalculatedAirtimeUsages
import dev.bytangle.airtime.utils.SelectedUsageSpec
import java.util.*

class AirtimeUsagesViewModel : ViewModel() {
    private val _airtimeUsagesList : MutableLiveData<List<CalculatedAirtimeUsages>> by lazy {
        MutableLiveData<List<CalculatedAirtimeUsages>>().also {
            it.value = this.fetchAndCalculateUsagesWithSpec(SelectedUsageSpec.DAILY)
        }
    }

    val airtimeUsagesList : LiveData<List<CalculatedAirtimeUsages>>
        get() = this._airtimeUsagesList

    val onCalculateUsagesUsingSpec : (selectedUsageSpec: SelectedUsageSpec) -> Unit = { selectedUsageSpec ->
        Log.d("AirtimeUsagesViewModel", selectedUsageSpec.name)
        this._airtimeUsagesList.value = this.fetchAndCalculateUsagesWithSpec(selectedUsageSpec)
    }

    private fun fetchAndCalculateUsagesWithSpec(spec : SelectedUsageSpec) : List<CalculatedAirtimeUsages> {
        return listOf(
            CalculatedAirtimeUsages(
                date = "Mon, 4th April, 2021",
                usages = listOf(
                    CalculatedAirtimeUsage(
                        totalAmount = 500.0,
                        network = AirtimeSupportedNetwork.MTN,
                        recharges = emptyList()
                    ),
                    CalculatedAirtimeUsage(
                        totalAmount = 900.0,
                        network = AirtimeSupportedNetwork.GLO,
                        recharges = emptyList()
                    ),
                    CalculatedAirtimeUsage(
                        totalAmount = 1400.0,
                        network = AirtimeSupportedNetwork.TOTAL,
                        recharges = emptyList()
                    )
                )
            ),
            CalculatedAirtimeUsages(
                date = "Sun, 3th April, 2021",
                usages = listOf(
                    CalculatedAirtimeUsage(
                        totalAmount = 800.0,
                        network = AirtimeSupportedNetwork.AIRTEL,
                        recharges = emptyList()
                    ),
                    CalculatedAirtimeUsage(
                        totalAmount = 300.0,
                        network = AirtimeSupportedNetwork.NINE_MOBILE,
                        recharges = emptyList()
                    ),
                    CalculatedAirtimeUsage(
                        totalAmount = 1400.0,
                        network = AirtimeSupportedNetwork.TOTAL,
                        recharges = emptyList()
                    )
                )
            )
        )
    }
}