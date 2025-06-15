package kv.apps.schoox.presentation.screens.utils

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import kotlin.math.roundToInt

@Composable
fun ProgressSlider(
    progress: Float,
    onProgressChanged: (Float) -> Unit,
    modifier: Modifier = Modifier,
    trackHeight: Dp = 4.dp,
    thumbSize: Dp = 28.dp
) {
    val density = LocalDensity.current
    var sliderWidth by remember { mutableFloatStateOf(0f) }
    val thumbWidthPx = remember(thumbSize) { with(density) { thumbSize.toPx() } }
    var dragOffset by remember(progress, sliderWidth) {
        mutableFloatStateOf(
            if (sliderWidth > 0) {
                (progress / 100f) * (sliderWidth - thumbWidthPx)
            } else {
                0f
            }
        )
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(thumbSize)
                .onSizeChanged { sliderWidth = it.width.toFloat() }
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(trackHeight)
                    .background(Color.LightGray)
                    .align(Alignment.CenterStart)
            )

            Box(
                modifier = Modifier
                    .height(trackHeight)
                    .background(Color.DarkGray)
                    .align(Alignment.CenterStart)
                    .width(
                        with(density) {
                            ((dragOffset + thumbWidthPx / 2).toDp())
                        }
                    )
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(thumbSize)
                    .pointerInput(Unit) {
                        detectHorizontalDragGestures { change, dragAmount ->
                            val newOffset = (dragOffset + dragAmount).coerceIn(
                                0f,
                                sliderWidth - thumbWidthPx
                            )
                            dragOffset = newOffset
                            val newProgress = (newOffset / (sliderWidth - thumbWidthPx)) * 100
                            onProgressChanged(newProgress)
                        }
                    }
            )

            Box(
                modifier = Modifier
                    .size(thumbSize)
                    .offset { IntOffset(dragOffset.roundToInt(), 0) }
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.surface)
                    .border(
                        width = 0.5.dp,
                        color = Color.LightGray,
                        shape = CircleShape
                    )
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Progress",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(end = 4.dp)
            )
            Text(
                text = "${progress.roundToInt()}%",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}