package com.hym.zhankucompose.compose

import android.content.ClipData
import android.content.ClipboardManager
import android.widget.Toast
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.hym.zhankucompose.MyApplication

/**
 * @author hehua2008
 * @date 2024/3/8
 */
val EMPTY_BLOCK = {}
val EMPTY_COMPOSABLE_BLOCK = @Composable {}
val COMMON_PADDING = 6.dp
val PADDING_VALUES_ZERO = PaddingValues()
val SMALL_PADDING_VALUES = PaddingValues(4.dp)
val BUTTON_CONTENT_PADDING = PaddingValues(8.dp)
val SMALL_BUTTON_CONTENT_PADDING = PaddingValues(2.dp)

val NUMBER_REGEX = Regex("\\d*")
val NON_NUMBER_REGEX = Regex("\\D+")
val MULTIPLE_SPACE = Regex("\\s{2,}")

fun CharSequence.copyToClipboard() {
    val context = MyApplication.INSTANCE
    val clipboard = ContextCompat.getSystemService(context, ClipboardManager::class.java)!!
    val clipData = ClipData.newPlainText(null, this)
    clipboard.setPrimaryClip(clipData)
    Toast.makeText(context, "Copied: $this", Toast.LENGTH_SHORT).show()
}
