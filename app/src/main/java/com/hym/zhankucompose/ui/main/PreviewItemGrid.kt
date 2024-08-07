package com.hym.zhankucompose.ui.main

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.FlingBehavior
import androidx.compose.foundation.gestures.ScrollableDefaults
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.times
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.itemKey
import com.hym.zhankucompose.R
import com.hym.zhankucompose.compose.COMMON_PADDING
import com.hym.zhankucompose.model.Content
import com.hym.zhankucompose.model.ContentType
import com.hym.zhankucompose.navigation.DetailsArgs
import com.hym.zhankucompose.navigation.LocalNavListener
import com.hym.zhankucompose.navigation.TagListArgs
import com.hym.zhankucompose.ui.NetworkStateLayout

/**
 * @author hehua2008
 * @date 2024/3/9
 */
private const val TAG = "PreviewItemGrid"

private val VerticalArrangement = Arrangement.spacedBy(COMMON_PADDING)

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PreviewItemGrid(
    lazyPagingItems: LazyPagingItems<Content>,
    modifier: Modifier = Modifier,
    columnSize: Int = 1,
    lazyGridState: LazyGridState = rememberLazyGridState(),
    flingBehavior: FlingBehavior = ScrollableDefaults.flingBehavior(),
    headerContent: @Composable ((headerModifier: Modifier) -> Unit)? = null
) {
    val navListener = LocalNavListener.current
    val surfaceContainerColor = MaterialTheme.colorScheme.surfaceContainer
    val horizontalArrangement = remember(columnSize) {
        object : Arrangement.Horizontal by Arrangement.SpaceEvenly {
            override val spacing = (columnSize + 1) * COMMON_PADDING
        }
    }
    val viewsPainter = rememberVectorPainter(
        ImageVector.vectorResource(R.drawable.vector_eye)
    )
    val commentPainter = rememberVectorPainter(
        ImageVector.vectorResource(R.drawable.vector_comment)
    )
    val favoritePainter = rememberVectorPainter(
        ImageVector.vectorResource(R.drawable.vector_favorite)
    )

    LazyVerticalGrid(
        columns = GridCells.Fixed(columnSize),
        modifier = modifier,
        state = lazyGridState,
        flingBehavior = flingBehavior,
        verticalArrangement = VerticalArrangement,
        horizontalArrangement = horizontalArrangement
    ) {
        if (headerContent != null) {
            item(key = "HeaderContent", span = { GridItemSpan(maxLineSpan) }) {
                headerContent(Modifier.animateItemPlacement())
            }
        }

        items(
            count = lazyPagingItems.itemCount,
            key = lazyPagingItems.itemKey { it.id }
        ) { index ->
            val previewItem = lazyPagingItems[index] // Note that item may be null.
            if (previewItem == null) {
                Log.e(TAG, "previewItem of index[$index] is null!")
                return@items
            }
            Surface(color = surfaceContainerColor, shape = ShapeDefaults.Small) {
                PreviewItem(
                    content = previewItem,
                    modifier = Modifier
                        .animateItemPlacement()
                        .fillMaxWidth()
                        .wrapContentHeight(),
                    viewsPainter = viewsPainter,
                    commentPainter = commentPainter,
                    favoritePainter = favoritePainter,
                    onImageClick = {
                        val contentType = ContentType.entries.firstOrNull { type ->
                            type.value == previewItem.objectType
                        } ?: ContentType.WORK
                        navListener.onNavigateToDetails(
                            DetailsArgs(contentType, previewItem.contentId)
                        )
                    },
                    onAuthorClick = {
                        navListener.onNavigateToTagList(
                            TagListArgs(previewItem.creatorObj, null, null)
                        )
                    }
                )
            }
        }

        val appendLoadState = lazyPagingItems.loadState.append
        if (appendLoadState !is LoadState.NotLoading) {
            item(key = "NetworkStateLayout") {
                NetworkStateLayout(
                    loadState = appendLoadState,
                    modifier = Modifier
                        .animateItemPlacement()
                        .fillMaxWidth()
                        .wrapContentHeight()
                ) {
                    lazyPagingItems.retry()
                }
            }
        }
    }
}
