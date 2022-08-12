package com.hym.zhankukotlin.ui.detail

import com.hym.zhankukotlin.model.ProductImage
import com.hym.zhankukotlin.model.ProductVideo
import org.jsoup.Jsoup

/**
 * @author hehua2008
 * @date 2022/8/12
 */
sealed class DetailContent<T>(val type: Int, val data: T) {
    companion object {
        const val CONTENT_IMAGE = 1
        const val CONTENT_VIDEO = 2
        const val CONTENT_TEXT = 3

        fun htmlToDetailContent(html: String): List<DetailContent<*>> {
            val doc = Jsoup.parse(html)
            val list = mutableListOf<DetailContent<*>>()
            val body = doc.selectFirst("body") ?: return list
            val sb = StringBuilder()
            body.children().forEach {
                val img = it.selectFirst("img")
                if (img == null) {
                    if (it.text().isNotBlank()) {
                        sb.append(it.toString())
                    }
                } else {
                    if (sb.isNotEmpty()) {
                        list.add(DetailText(sb.toString()))
                        sb.setLength(0)
                    }
                    val imageUrl = img.absUrl("src")
                    if (imageUrl.isNullOrBlank()) return@forEach
                    val productImage = ProductImage(
                        0L,
                        0,
                        imageUrl,
                        "",
                        0,
                        0,
                        "",
                        0,
                        "",
                        "",
                        0,
                        0L,
                        0,
                        0L,
                        "",
                        imageUrl,
                        0
                    )
                    list.add(DetailImage(productImage))
                }
            }
            if (sb.isNotEmpty()) {
                list.add(DetailText(sb.toString()))
                sb.setLength(0)
            }
            return list
        }
    }

    override fun hashCode(): Int {
        return data.hashCode()
    }

    open fun shallowEquals(other: DetailContent<*>?): Boolean {
        return false
    }
}

class DetailImage(image: ProductImage) : DetailContent<ProductImage>(CONTENT_IMAGE, image) {
    override fun shallowEquals(other: DetailContent<*>?): Boolean {
        if (this === other) return true
        if (other !is DetailImage) return false
        return data.url == other.data.url
    }
}

class DetailVideo(video: ProductVideo) : DetailContent<ProductVideo>(CONTENT_VIDEO, video) {
    override fun shallowEquals(other: DetailContent<*>?): Boolean {
        if (this === other) return true
        if (other !is DetailVideo) return false
        return data.url == other.data.url
    }
}

class DetailText(article: String) : DetailContent<String>(CONTENT_TEXT, article) {
    override fun shallowEquals(other: DetailContent<*>?): Boolean {
        if (this === other) return true
        if (other !is DetailText) return false
        return data == other.data
    }
}