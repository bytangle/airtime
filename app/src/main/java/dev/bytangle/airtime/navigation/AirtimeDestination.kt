package dev.bytangle.airtime.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AspectRatio
import androidx.compose.material.icons.twotone.*
import androidx.compose.ui.graphics.vector.ImageVector
import dev.bytangle.airtime.R

sealed class AirtimeDestination(val route : String, @StringRes val resourceId : Int, val icon : ImageVector) {
    object Scan : AirtimeDestination(route = "scan", resourceId = R.string.scan, icon = Icons.Outlined.AspectRatio)
    object Usage : AirtimeDestination(route = "usage", resourceId = R.string.usage, icon = Icons.TwoTone.Insights)
    object Settings : AirtimeDestination(route = "settings", resourceId = R.string.settings, icon = Icons.TwoTone.Settings)
    object Recharge : AirtimeDestination(route = "recharge", resourceId = R.string.recharge, icon = Icons.TwoTone.Receipt)
    companion object {
        val destinations = listOf(
            AirtimeDestination.Scan,
            AirtimeDestination.Usage,
            AirtimeDestination.Settings,
            AirtimeDestination.Recharge
        )
    }
}