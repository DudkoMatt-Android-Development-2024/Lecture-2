package com.github.dudkomatt.androidcourse.lecture2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.github.dudkomatt.androidcourse.lecture2.ui.theme.Lecture2DynamicStringColumnTheme

class MainActivity : ComponentActivity() {
    val state: MutableState<List<String>> = mutableStateOf(emptyList())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Lecture2DynamicStringColumnTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { padding ->
                    MainScreen(padding, state)
                }
            }
        }
    }
}

@Composable
fun MainScreen(padding: PaddingValues, state: MutableState<List<String>>) {
    val str = remember { mutableStateOf("") }

    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
    ) {
        val defaultMargin = 8.dp
        val defaultPadding = PaddingValues(8.dp)
        val (column, rowInput) = createRefs()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .constrainAs(column) {
                    top.linkTo(parent.top, margin = defaultMargin)
                    start.linkTo(parent.start, margin = defaultMargin)
                    end.linkTo(parent.end, margin = defaultMargin)
                    bottom.linkTo(rowInput.top, margin = defaultMargin)
                    width = Dimension.fillToConstraints
                    height = Dimension.fillToConstraints
                }
        ) {
            for (s in state.value) {
                key(s) {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .defaultMinSize(24.dp)
                            .padding(8.dp),
                        fontSize = 18.sp,
                        text = s
                    )
                }
            }
        }
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(defaultPadding)
            .constrainAs(rowInput) {
                start.linkTo(parent.start, margin = defaultMargin)
                end.linkTo(parent.end, margin = defaultMargin)
                bottom.linkTo(parent.bottom, margin = defaultMargin)
                width = Dimension.fillToConstraints
                height = Dimension.wrapContent
            }) {
            TextField(
                value = str.value,
                onValueChange = { str.value = it },
                modifier = Modifier
                    .weight(7f)
                    .padding(8.dp, 4.dp)
            )
            Button(
                onClick = {
                    state.value += str.value
                },
                modifier = Modifier
                    .weight(3f)
                    .align(Alignment.CenterVertically)
                    .padding(12.dp, 4.dp)
            ) {
                Text("Send")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    var sequence = generateSequence(1) {
        (it + 1).takeIf { it < 30 }
    }.map { it.toString() }.toList()

    val paddingValues = PaddingValues(0.dp)
    val state = remember { mutableStateOf(sequence) }

    MainScreen(paddingValues, state)
}