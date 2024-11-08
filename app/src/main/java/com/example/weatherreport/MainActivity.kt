package com.example.weatherreport

import android.os.Bundle
import android.telecom.Call.Details
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.BiasAlignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.weatherreport.ui.theme.WeatherReportTheme
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WeatherReportTheme {
                val selectedItem = remember {
                    mutableStateOf("Самара")
                }
                WeatherReportTheme(
                    localTime = "11:20",
                    windSpeed = 4.5,
                    airPressure = 759,
                    humidity = 50,
                    selectedItem = selectedItem.value,
                    items = listOf("Москва", "Самара", "Владивосток"),
                    onSelect = { selectedItem.value = it }
                )
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherReportTheme(
    localTime: String,
    windSpeed: Double,
    airPressure: Int,
    humidity: Int,
    modifier: Modifier = Modifier,
    selectedItem: String,
    items: List<String>,
    onSelect: (String) -> Unit
) {
    Scaffold(
        topBar = {
            val isExpanded = remember {
                mutableStateOf(false)
            }

            ExposedDropdownMenuBox(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = 22.dp,
                        vertical = 24.dp
                    ),
                expanded = isExpanded.value,
                onExpandedChange = { isExpanded.value = it },
                content = {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .menuAnchor()
                            .background(
                                color = Color.Black.copy(0.05f),
                                shape = RoundedCornerShape(12.dp)
                            )
                            .padding(
                                horizontal = 8.dp,
                                vertical = 10.dp
                            ),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically

                    )
                    {
                        Text(
                            text = selectedItem,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color.Black,
                            fontFamily = FontFamily(listOf(Font(R.font.montserrat_medium))),
                            modifier = Modifier
                        )
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpanded.value)
                    }

                    ExposedDropdownMenu(
                        expanded = isExpanded.value,
                        onDismissRequest = { isExpanded.value = false }
                    ) {
                        items.forEach { item ->
                            DropdownMenuItem(
                                text = { Text(text = item) },
                                onClick = {
                                    onSelect(item)
                                    isExpanded.value = false
                                }
                            )
                        }
                    }

                }
            )

        },
        bottomBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = 24.dp,
                        vertical = 44.dp
                    )
                    .background(
                        color = Color.Black.copy(0.05f),
                        shape = RoundedCornerShape(12.dp)
                    )
                    .padding(
                        horizontal = 15.dp,
                        vertical = 6.dp
                    ),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            )
            {
                DetailsTextBlock(
                    title = "Время",
                    subtitle = localTime
                )
                DetailsTextBlock(
                    title = "Ск.ветра",
                    subtitle = "$windSpeed M/C"
                )
                DetailsTextBlock(
                    title = "Давление",
                    subtitle = "$airPressure мм."
                )
                DetailsTextBlock(
                    title = "Влажность",
                    subtitle = "$humidity %"
                )
            }
                    },


        ) {
        Box(
            modifier = Modifier
                .padding(it)
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ){
            Box(
                modifier = Modifier
                    .blur(50.dp)
                    .padding(40.dp)
                    .fillMaxWidth(1f)
                    .fillMaxHeight(0.5f)
                    .background(
                        color = Color(0xFF9BB7F2).copy(0.7f),
                    shape = CircleShape
                    )
            )
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.partly_cloudy_day),
                    contentDescription = null
                )
               Text(
                   text = "Облачно",
                   fontSize = 30.sp,
                   fontWeight = FontWeight.SemiBold,
                   color = Color.White,
                   fontFamily = FontFamily(listOf(Font(R.font.montserrat_semibold))),

               )
                Row() {
                Text (
                    text = "31",
                    fontSize = 70.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.White,
                    fontFamily = FontFamily(listOf(Font(R.font.montserrat_semibold))),
                )
                    Image(
                        painter = painterResource(id = R.drawable.ellipse_1),
                        contentDescription = null
                    )
            }
            }
        }
    }

}


@Composable
fun DetailsTextBlock(
    title: String,
    subtitle: String
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text = title,
            fontSize = 12.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color.Black.copy(0.7f),
            fontFamily = FontFamily(listOf(Font(R.font.montserrat_semibold)))
        )
        Text(
            text = subtitle,
            fontSize = 15.sp,
            fontWeight = FontWeight.Medium,
            color = Color.Black,
            fontFamily = FontFamily(listOf(Font(R.font.montserrat_medium)))
        )

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropdownMenuExample(
    modifier: Modifier = Modifier,
    selectedItem: String,
    items: List<String>,
    onSelect: (String) -> Unit
) {


    val isExpanded = remember {
        mutableStateOf(false)
    }

    ExposedDropdownMenuBox(
        modifier = modifier,
        expanded = isExpanded.value,
        onExpandedChange = { isExpanded.value = it },
        content = {

            Text(
                text = selectedItem,
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor()
            )

            ExposedDropdownMenu(
                expanded = isExpanded.value,
                onDismissRequest = { isExpanded.value = false }
            ) {
                items.forEach { item ->
                    DropdownMenuItem(
                        text = { Text(text = item) },
                        onClick = {
                            onSelect(item)
                            isExpanded.value = false
                        }
                    )
                }
            }

        }
    )

}

@Preview
@Composable
fun DropdownMenuExamplePreview() {
    Surface {
        val selectedItem = remember {
            mutableStateOf("Самара")
        }
        WeatherReportTheme(
            localTime = "11:20",
            windSpeed = 4.5,
            airPressure = 759,
            humidity = 50,
            selectedItem = selectedItem.value,
            items = listOf("Москва", "Самара", "Владивосток"),
            onSelect = { selectedItem.value = it }
        )
    }
}


