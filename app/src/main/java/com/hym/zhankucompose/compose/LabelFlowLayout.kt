package com.hym.zhankucompose.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

/**
 * @author hehua2008
 * @date 2024/3/14
 */
private val ZeroPaddingValues = PaddingValues()

@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
@Composable
fun LabelFlowLayout(
    labels: ImmutableList<String>,
    modifier: Modifier = Modifier,
    defaultLabelIndex: Int = 0,
    allSelected: Boolean = false,
    itemPadding: PaddingValues = ZeroPaddingValues,
    contentPadding: PaddingValues = ZeroPaddingValues,
    onLabelSelected: (index: Int) -> Unit
) {
    var selectedIndex by rememberMutableIntState(labels, defaultLabelIndex) {
        defaultLabelIndex
    }

    FlowRow(
        modifier = modifier.selectableGroup(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalArrangement = Arrangement.Center
    ) {
        RemoveAccessibilityExtraSpace {
            labels.forEachIndexed { index, label ->
                SmallFilterChip(
                    selected = if (allSelected) true else (index == selectedIndex),
                    onClick = {
                        selectedIndex = index
                        onLabelSelected(index)
                    },
                    label = {
                        Text(
                            text = label,
                            maxLines = 1
                        )
                    },
                    modifier = Modifier.padding(itemPadding),
                    shape = ShapeDefaults.ExtraSmall,
                    minHeight = 0.dp,
                    paddingValues = contentPadding
                )
            }
        }
    }
}

@Preview
@Composable
private fun PreviewLabelFlowLayout() {
    val labels = remember {
        List(20) { index -> "标签$index" }.toImmutableList()
    }
    LabelFlowLayout(
        labels = labels,
        modifier = Modifier.background(Color.White),
        itemPadding = PaddingValues(vertical = 4.dp)
    ) {}
}
