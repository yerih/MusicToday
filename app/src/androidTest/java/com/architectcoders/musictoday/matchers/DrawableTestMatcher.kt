package com.architectcoders.musictoday.matchers

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import androidx.test.espresso.matcher.ViewMatchers
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher


class DrawableTestMatcher internal constructor(private val expectedId: Int) : TypeSafeMatcher<View?>(View::class.java) {
    private var resourceName: String? = null

    override fun matchesSafely(target: View?): Boolean {
        if (target !is ImageView) {
            return false
        }
        if (expectedId == EMPTY) {
            return target.drawable == null
        }
        if (expectedId == ANY) {
            return target.drawable != null
        }
        val resources = target.getContext().resources
        val expectedDrawable = resources.getDrawable(expectedId)
        resourceName = resources.getResourceEntryName(expectedId)
        if (expectedDrawable == null) {
            return false
        }
        val bitmap = getBitmap(target.drawable)
        val otherBitmap = getBitmap(expectedDrawable)
        return bitmap.sameAs(otherBitmap)
    }

    private fun getBitmap(drawable: Drawable): Bitmap {
        val bitmap = Bitmap.createBitmap(
            drawable.intrinsicWidth,
            drawable.intrinsicHeight,
            Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(bitmap)
        drawable.setBounds(0, 0, canvas.width, canvas.height)
        drawable.draw(canvas)
        return bitmap
    }

    override fun describeTo(description: Description) {
        description.appendText("with drawable from resource id: ")
        description.appendValue(expectedId)
        if (resourceName != null) {
            description.appendText("[")
            description.appendText(resourceName)
            description.appendText("]")
        }
    }


    companion object {
        const val EMPTY = -1
        const val ANY = -2
        fun withDrawable(resourceId: Int): Matcher<View?>? {
            return DrawableTestMatcher(resourceId)
        }
    }
}

fun ViewMatchers.withDrawable(idResource: Int) = DrawableTestMatcher.withDrawable(idResource)
