package com.hym.zhankucompose.model

import androidx.annotation.Keep

/**
{
"content": [...],
"first": false,
"last": true,
"loadNumber": 1,
"number": 1,
"numberOfElements": 1,
"pageable": {...},
"size": 10,
"total": 1,
"totalContent": 0,
"totalElements": 1,
"totalPages": 1
}
 */
@Keep
data class SearchDesigner(
    val content: List<CreatorObj>,
    val first: Boolean,
    val last: Boolean,
    val loadNumber: Int,
    val number: Int,
    val numberOfElements: Int,
    val pageable: Pageable,
    val size: Int,
    val total: Int,
    val totalContent: Int,
    val totalElements: Int,
    val totalPages: Int
)