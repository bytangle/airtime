package dev.bytangle.airtime.composables.destinations

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.fontResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
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
                        fontSize = 26.sp,
                        fontFamily = FontFamily(listOf(
                            Font(R.font.sourcesanspro_light)))
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
    Column(modifier = modifier.background(colorResource(id = R.color.blue_bg))) {
        // first child for chips
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 10.dp),
            color = colorResource(id = R.color.airtime_bar_bg),
            elevation = 2.dp,
            shape = RoundedCornerShape(CornerSize(26.dp))
        ) {
            Row(modifier = Modifier.padding(6.dp)) {
                OutlinedButton(
                    onClick = { selectedUsageSpec.value = SelectedUsageSpec.DAILY },
                    shape = RoundedCornerShape(CornerSize(16.dp)),
                    modifier = Modifier
                        .weight(1f)
                        .padding(4.dp),
                    colors = if (selectedUsageSpec.value == SelectedUsageSpec.DAILY) ButtonDefaults.outlinedButtonColors(backgroundColor = colorResource(
                        id = R.color.airtime_primary
                    ), contentColor = MaterialTheme.colors.onPrimary)
                    else ButtonDefaults.outlinedButtonColors(backgroundColor = MaterialTheme.colors.surface, contentColor = colorResource(
                        id = R.color.faded_black
                    ))
                ) {
                    Text(
                        text = "Daily",
                        fontFamily = FontFamily(listOf(
                        Font(R.font.sourcesanspro_light)))
                    )
                }
                OutlinedButton(
                    onClick = { selectedUsageSpec.value = SelectedUsageSpec.WEEKLY },
                    shape = RoundedCornerShape(CornerSize(16.dp)),
                    modifier = Modifier
                        .weight(1f)
                        .padding(4.dp),
                    colors = if (selectedUsageSpec.value == SelectedUsageSpec.WEEKLY) ButtonDefaults.outlinedButtonColors(backgroundColor = colorResource(
                        id = R.color.airtime_primary
                    ), contentColor = MaterialTheme.colors.onPrimary)
                    else ButtonDefaults.outlinedButtonColors(backgroundColor = MaterialTheme.colors.surface, contentColor = colorResource(
                        id = R.color.faded_black
                    ))
                ) {
                    Text(
                        text = "Weekly",
                        fontFamily = FontFamily(listOf(
                            Font(R.font.sourcesanspro_light)))
                    )
                }
                OutlinedButton(
                    onClick = { selectedUsageSpec.value = SelectedUsageSpec.MONTHLY },
                    shape = RoundedCornerShape(CornerSize(16.dp)),
                    modifier = Modifier
                        .weight(1f)
                        .padding(4.dp),
                    colors = if (selectedUsageSpec.value == SelectedUsageSpec.MONTHLY) ButtonDefaults.outlinedButtonColors(backgroundColor = colorResource(
                        id = R.color.airtime_primary
                    ), contentColor = MaterialTheme.colors.onPrimary)
                    else ButtonDefaults.outlinedButtonColors(backgroundColor = MaterialTheme.colors.surface, contentColor = colorResource(
                        id = R.color.faded_black
                    ))
                ) {
                    Text(
                        text = "Monthly",
                        fontFamily = FontFamily(listOf(
                            Font(R.font.sourcesanspro_light)))
                    )
                }
            }
        }

        LazyColumn(modifier = Modifier.weight(1f)) {
            items(5) {item ->
                UsageCard()
            }
        }
    }
}

@Composable
fun UsageCard(modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .height(140.dp)
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp),
        shape = RoundedCornerShape(5.dp),
        elevation = 2.dp
    ) {
        Column(modifier = Modifier) {
            Text(text = "Mon, Nov 4, 2021", modifier = Modifier.padding(8.dp), fontFamily = FontFamily(listOf(
                Font(R.font.sourcesanspro_light))))

            // Color line
            Row() {
                Surface(
                color = colorResource(id = R.color.airtime_primary),
                modifier = Modifier
                    .height(8.dp)
                    .weight(1f)
                ) {

                }
                Surface(
                    color = colorResource(id = R.color.airtime_tertiary),
                    modifier = Modifier
                        .height(8.dp)
                        .width(90.dp)
                ) {

                }
                Surface(
                    color = colorResource(id = R.color.airtime_secondary),
                    modifier = Modifier
                        .height(8.dp)
                        .width(30.dp)
                ) {

                }
            }
            Row(modifier = Modifier
                .weight(1f)
                .fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround, verticalAlignment = Alignment.Bottom) {
                (0 until 3).forEach { item ->
                    Surface(
                        shape = RoundedCornerShape( 2.dp),
                        elevation = 2.dp,
                        modifier = Modifier
                            .height(70.dp)
                            .weight(1f)
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Bottom) {
                            Text(text = "GLO", modifier = Modifier.align(Alignment.CenterHorizontally), fontFamily = FontFamily(listOf(
                                Font(R.font.sourcesanspro_light))))
                            Text(
                                text = "N5000",
                                modifier = Modifier.align(Alignment.CenterHorizontally),
                                fontSize = 35.sp,
                                color = colorResource(id = R.color.airtime_tertiary),
                                fontFamily = FontFamily(listOf(
                                    Font(R.font.sourcesanspro_light)
                                ))
                            )
                        }
                    }
                    Spacer(modifier = Modifier.width(2.dp))
                }
            }
        }
    }
}