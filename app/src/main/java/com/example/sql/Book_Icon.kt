package com.example.sql

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

class Book_Icon {
    val Book: ImageVector
        get() {
            if (_Book != null) return _Book!!

            _Book = ImageVector.Builder(
                name = "Book",
                defaultWidth = 24.dp,
                defaultHeight = 24.dp,
                viewportWidth = 960f,
                viewportHeight = 960f
            ).apply {
                path(
                    fill = SolidColor(Color(0xFF000000))
                ) {
                    moveTo(240f, 880f)
                    quadToRelative(-33f, 0f, -56.5f, -23.5f)
                    reflectiveQuadTo(160f, 800f)
                    verticalLineToRelative(-640f)
                    quadToRelative(0f, -33f, 23.5f, -56.5f)
                    reflectiveQuadTo(240f, 80f)
                    horizontalLineToRelative(480f)
                    quadToRelative(33f, 0f, 56.5f, 23.5f)
                    reflectiveQuadTo(800f, 160f)
                    verticalLineToRelative(640f)
                    quadToRelative(0f, 33f, -23.5f, 56.5f)
                    reflectiveQuadTo(720f, 880f)
                    close()
                    moveToRelative(0f, -80f)
                    horizontalLineToRelative(480f)
                    verticalLineToRelative(-640f)
                    horizontalLineToRelative(-80f)
                    verticalLineToRelative(280f)
                    lineToRelative(-100f, -60f)
                    lineToRelative(-100f, 60f)
                    verticalLineToRelative(-280f)
                    horizontalLineTo(240f)
                    close()
                    moveToRelative(0f, 0f)
                    verticalLineToRelative(-640f)
                    close()
                    moveToRelative(200f, -360f)
                    lineToRelative(100f, -60f)
                    lineToRelative(100f, 60f)
                    lineToRelative(-100f, -60f)
                    close()
                }
            }.build()

            return _Book!!
        }

    private var _Book: ImageVector? = null


}

class BookIcon {
    val Book: ImageVector
        get() {
            if (_Book != null) return _Book!!

            _Book = ImageVector.Builder(
                name = "Book",
                defaultWidth = 24.dp,
                defaultHeight = 24.dp,
                viewportWidth = 960f,
                viewportHeight = 960f
            ).apply {
                path(
                    fill = SolidColor(Color(0xFF000000)) // base black
                ) {
                    moveTo(240f, 880f)
                    quadToRelative(-33f, 0f, -56.5f, -23.5f)
                    reflectiveQuadTo(160f, 800f)
                    verticalLineToRelative(-640f)
                    quadToRelative(0f, -33f, 23.5f, -56.5f)
                    reflectiveQuadTo(240f, 80f)
                    horizontalLineToRelative(480f)
                    quadToRelative(33f, 0f, 56.5f, 23.5f)
                    reflectiveQuadTo(800f, 160f)
                    verticalLineToRelative(640f)
                    quadToRelative(0f, 33f, -23.5f, 56.5f)
                    reflectiveQuadTo(720f, 880f)
                    close()
                    moveToRelative(0f, -80f)
                    horizontalLineToRelative(480f)
                    verticalLineToRelative(-640f)
                    horizontalLineToRelative(-80f)
                    verticalLineToRelative(280f)
                    lineToRelative(-100f, -60f)
                    lineToRelative(-100f, 60f)
                    verticalLineToRelative(-280f)
                    horizontalLineTo(240f)
                    close()
                    moveToRelative(0f, 0f)
                    verticalLineToRelative(-640f)
                    close()
                    moveToRelative(200f, -360f)
                    lineToRelative(100f, -60f)
                    lineToRelative(100f, 60f)
                    lineToRelative(-100f, -60f)
                    close()
                }
            }.build()

            return _Book!!
        }

    private var _Book: ImageVector? = null
}
