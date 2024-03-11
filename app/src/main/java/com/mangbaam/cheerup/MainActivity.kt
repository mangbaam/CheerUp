package com.mangbaam.cheerup

import android.icu.text.AlphabeticIndex.Bucket.LabelType
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.MarqueeAnimationMode
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mangbaam.cheerup.FlowDirection.*
import com.mangbaam.cheerup.screen.Main
import com.mangbaam.cheerup.ui.theme.CheerUpTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CheerUpTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    Main()
                    /*Column {
                        val focusRequester = remember {
                            FocusRequester()
                        }
                        LaunchedEffect(focusRequester) {
                            focusRequester.requestFocus()
                        }
                        var text by remember {
                            mutableStateOf("응원해~!!")
                        }
                        var textSize by remember { mutableIntStateOf(10) }
                        val sliderState = remember {
                            SliderState(
                                steps = 1,
                                value = textSize.toFloat(),
                            )
                        }

                        NeonPreview(
                            Modifier
                                .fillMaxWidth()
                                .height(150.dp)
                                .focusRequester(focusRequester)
                                .focusable(),
                            text = text,
                            option = NeonOption(textSize = textSize),
                        )
                        OutlinedTextField(
                            modifier = Modifier
                                .padding(top = 30.dp)
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp),
                            value = text,
                            onValueChange = { text = it }
                        )
                        Label(
                            modifier = Modifier.padding(top = 20.dp, start = 16.dp),
                            label = "방향",
                        )

                        Label(
                            modifier = Modifier.padding(top = 20.dp, start = 16.dp),
                            label = "글자 크기",
                        )

                        Slider(state = sliderState, )

                        Label(
                            modifier = Modifier.padding(top = 20.dp, start = 16.dp),
                            label = "색상",
                        )
                    }*/
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NeonPreview(
    modifier: Modifier = Modifier,
    text: String = "",
    option: NeonOption = NeonOption(),
) {
    Box(
        modifier = modifier.background(color = option.bgColor),
        contentAlignment = when (option.direction) {
            RTL -> Alignment.CenterStart
            LTR -> Alignment.CenterEnd
            STOP -> Alignment.Center
        },
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .apply {
                    when (option.direction) {
                        RTL -> basicMarquee(animationMode = MarqueeAnimationMode.Immediately)
                        LTR -> basicMarquee()
                        STOP -> align(Alignment.Center)
                    }
                },
            text = text,
            fontSize = (option.textSize * 5f).sp,
            fontWeight = FontWeight.ExtraBold,
            color = option.textColor,
        )
    }
}

@Composable
fun Label(
    label: String,
    modifier: Modifier = Modifier,
) {
    Text(
        modifier = modifier,
        text = label,
        style = MaterialTheme.typography.labelMedium,
    )
}

@Preview
@Composable
private fun NeonPreviewPreview() {
    CheerUpTheme {
        Surface {
            NeonPreview(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp),
                text = "사랑해",
                option = NeonOption(),
            )
        }
    }
}
