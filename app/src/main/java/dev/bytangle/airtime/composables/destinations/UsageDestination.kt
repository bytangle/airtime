package dev.bytangle.airtime.composables.destinations

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.FilterList
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.navigation.NavHostController
import com.google.android.material.chip.Chip
import dev.bytangle.airtime.R
import dev.bytangle.airtime.composables.components.Introduce
import dev.bytangle.airtime.utils.SelectedUsageSpec

@Composable
fun UsageDestination(navHostController : NavHostController, modifier : Modifier = Modifier) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Usage",
                        fontSize = 26.sp
                    )
                },
                actions = {
                    IconButton(onClick = {}) {
                        Icon(imageVector = Icons.Rounded.FilterList, contentDescription = null)
                    }
                },
                backgroundColor = colorResource(id = R.color.airtime_bar_bg)
            )
        }
    ) {
        UsageBodyContent(modifier = Modifier.padding(it))
    }
}

@Composable
fun UsageBodyContent(modifier : Modifier = Modifier) {
    val selectedUsageSpec = remember { mutableStateOf(SelectedUsageSpec.DAILY) }
    Column(modifier = modifier) {
        // first child for chips
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)) {
            OutlinedButton(
                onClick = { selectedUsageSpec.value = SelectedUsageSpec.DAILY },
                shape = RoundedCornerShape(CornerSize(16.dp)),
                modifier = Modifier
                    .weight(1f)
                    .padding(2.dp),
                colors = if (selectedUsageSpec.value == SelectedUsageSpec.DAILY) ButtonDefaults.outlinedButtonColors(backgroundColor = MaterialTheme.colors.primary, contentColor = MaterialTheme.colors.onPrimary)
                            else ButtonDefaults.outlinedButtonColors(backgroundColor = MaterialTheme.colors.surface, contentColor = MaterialTheme.colors.primary)
            ) {
                Text(text = "Daily")
            }
            OutlinedButton(
                onClick = { selectedUsageSpec.value = SelectedUsageSpec.WEEKLY },
                shape = RoundedCornerShape(CornerSize(16.dp)),
                modifier = Modifier
                    .weight(1f)
                    .padding(2.dp),
                colors = if (selectedUsageSpec.value == SelectedUsageSpec.WEEKLY) ButtonDefaults.outlinedButtonColors(backgroundColor = MaterialTheme.colors.primary, contentColor = MaterialTheme.colors.onPrimary)
                else ButtonDefaults.outlinedButtonColors(backgroundColor = MaterialTheme.colors.surface, contentColor = MaterialTheme.colors.primary)
            ) {
                Text(text = "Weekly")
            }
            OutlinedButton(
                onClick = { selectedUsageSpec.value = SelectedUsageSpec.MONTHLY },
                shape = RoundedCornerShape(CornerSize(16.dp)),
                modifier = Modifier
                    .weight(1f)
                    .padding(2.dp),
                colors = if (selectedUsageSpec.value == SelectedUsageSpec.MONTHLY) ButtonDefaults.outlinedButtonColors(backgroundColor = MaterialTheme.colors.primary, contentColor = MaterialTheme.colors.onPrimary)
                else ButtonDefaults.outlinedButtonColors(backgroundColor = MaterialTheme.colors.surface, contentColor = MaterialTheme.colors.primary)
            ) {
                Text(text = "Monthly")
            }
        }
    }
}