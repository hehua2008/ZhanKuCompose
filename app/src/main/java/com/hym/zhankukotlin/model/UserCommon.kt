package com.hym.zhankukotlin.model

import androidx.annotation.Keep

/**
{
"postMap": [...],
"constellation": "摩羯座",
"qrcode": "community/07375c619643bf1101508eb5163d1a.jpg",
"member": {...},
"honors_str": "zcool,gogoup",
"time": "60后",
"birthdayStr": "1960-01-01",
"guanzhuStatus": 0
}
 */
@Keep
data class UserCommon(
    val birthdayStr: String,
    val constellation: String,
    val guanzhuStatus: Int,
    val honors_str: String,
    val member: Member,
    val postMap: List<PostMap>,
    val qrcode: String,
    val time: String
)