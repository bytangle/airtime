package dev.bytangle.airtime.composables.destinations

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.FilterList
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import dev.bytangle.airtime.R

@Composable
fun RechargeDestination(modifier : Modifier = Modifier, navHostController : NavHostController, arg : String?) {
    /***
     * arg format
     * first element : Recharge pin
     * second element : prefix
     * third element : amount
     * fourth element : network name
     */
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Airtime Recharge",
                        fontSize = 26.sp,
                        fontFamily = FontFamily(listOf(
                            Font(R.font.sourcesanspro_light)
                        ))
                    )
                },
                backgroundColor = colorResource(id = R.color.airtime_bar_bg)
            )
        }
    ) {
        RechargeBodyContent(modifier = Modifier.padding(it), arg = arg)
    }
}

@Composable
fun RechargeBodyContent(
    modifier: Modifier = Modifier,
    arg : String?
) {
    val airtimeInfo = remember { arg?.split(",")?.toMutableStateList() }
    Column(modifier = modifier.background(colorResource(id = R.color.blue_bg))) {
        // first child for chips
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(
                    top = 10.dp,
                    bottom = 10.dp,
                    start = 10.dp,
                    end = 10.dp,
                ),
            color = colorResource(id = R.color.airtime_bar_bg),
            elevation = 2.dp,
            shape = RoundedCornerShape(CornerSize(6.dp))
        ) {
            Column(
                modifier = Modifier.padding(
                    top = 60.dp,
                    bottom = 8.dp,
                    start = 8.dp,
                    end = 8.dp,
                )
            ) {
                if (airtimeInfo != null) {
                    // network
                    Text(
                        text = airtimeInfo[3],
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        fontSize = 26.sp,
                        color = colorResource(id = R.color.airtime_secondary),
                        fontFamily = FontFamily(listOf(
                            Font(R.font.sourcesanspro_light)
                        ))
                    )

                    // prefix
                    Text(
                        text = airtimeInfo[1],
                        modifier = Modifier.align(Alignment.Start),
                        fontSize = 26.sp,
                        color = colorResource(id = R.color.faded_black),
                       fontFamily = FontFamily(listOf(
                           Font(R.font.sourcesanspro_light)
                       ))
                    )

                    // recharge pin
                    Text(
                        text = "${airtimeInfo[0]}#",
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        fontSize = 35.sp,
                        textAlign = TextAlign.Center,
                        color = colorResource(id = R.color.airtime_primary),
                        fontFamily = FontFamily(listOf(
                            Font(R.font.sourcesanspro_black)
                        ))
                    )

                    // amount
                    Text(
                        text = "Amount: N${airtimeInfo[2]}",
                        modifier = Modifier
                            .align(Alignment.End)
                            .clip(
                                RoundedCornerShape(6.dp))
                            .background(colorResource(id = R.color.airtime_secondary))
                            .padding(8.dp)
,
                        fontSize = 16.sp,
                        color = colorResource(id = R.color.white_2),
                        fontFamily = FontFamily(listOf(
                            Font(R.font.sourcesanspro_light)
                        ))
                    )
                }

            }
        }
        Spacer(modifier = Modifier.height(80.dp))
    }
}