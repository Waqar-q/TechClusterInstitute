package com.techclusterdesignwala.institute.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun StatusBadge(status: String, modifier: Modifier = Modifier) {
    val (bgColor, textColor) = when (status.uppercase()) {
        "PRESENT" -> Color(0xFFE8F5E9) to Color(0xFF2E7D32)
        "ABSENT" -> Color(0xFFFFEBEE) to Color(0xFFC62828)
        "LEAVE" -> Color(0xFFFFF8E1) to Color(0xFFF57F17)
        "PENDING" -> Color(0xFFFFF3E0) to Color(0xFFE65100)
        "SUBMITTED" -> Color(0xFFE3F2FD) to Color(0xFF1565C0)
        "GRADED" -> Color(0xFFE8F5E9) to Color(0xFF2E7D32)
        else -> Color(0xFFF5F5F5) to Color(0xFF616161)
    }

    Surface(
        shape = RoundedCornerShape(4.dp),
        color = bgColor,
        modifier = modifier
    ) {
        Text(
            text = status.uppercase(),
            style = MaterialTheme.typography.labelSmall,
            color = textColor,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
        )
    }
}
