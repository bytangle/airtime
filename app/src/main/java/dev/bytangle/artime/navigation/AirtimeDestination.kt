package dev.bytangle.artime.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.*
import androidx.compose.ui.graphics.vector.ImageVector
import dev.bytangle.artime.R

sealed class AirtimeDestination(val route : String, @StringRes val resourceId : Int, val icon : ImageVector) {
    object Scan : AirtimeDestination(route = "scan", resourceId = R.string.scan, icon = Icons.TwoTone.AspectRatio)
    object Usage : AirtimeDestination(route = "usage", resourceId = R.string.usage, icon = Icons.TwoTone.Insights)
    object Settings : AirtimeDestination(route = "settings", resourceId = R.string.settings, icon = Icons.TwoTone.Settings)

    companion object {
        val destinations = listOf(
            AirtimeDestination.Scan,
            AirtimeDestination.Usage,
            AirtimeDestination.Settings
        )
    }
}