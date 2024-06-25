package com.nishantpardamwar.notificationhistory

import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.ImageBitmapConfig
import androidx.compose.ui.graphics.drawscope.CanvasDrawScope
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import kotlin.math.roundToInt

fun Painter.toImageBitmap(
    density: Density = Density(1f),
    layoutDirection: LayoutDirection = LayoutDirection.Ltr,
    size: Size = intrinsicSize,
    config: ImageBitmapConfig = ImageBitmapConfig.Argb8888,
): ImageBitmap {
    val image = ImageBitmap(
        width = size.width.roundToInt(), height = size.height.roundToInt(), config = config
    )
    val canvas = Canvas(image)
    CanvasDrawScope().draw(
        density = density, layoutDirection = layoutDirection, canvas = canvas, size = size
    ) {
        draw(size = this.size)
    }
    return image
}

fun String?.stringOrNull(): String? {
    return if (this.isNullOrBlank()) null
    else this
}