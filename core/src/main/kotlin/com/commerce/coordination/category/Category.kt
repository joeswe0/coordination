package com.commerce.coordination.category

enum class Category(
    val displayName: String
) {
    TOP("상의"),
    OUTERWEAR("아우터"),
    PANTS("바지"),
    SNEAKERS("스니커즈"),
    BAG("가방"),
    HAT("모자"),
    SOCKS("양말"),
    ACCESSORIES("액세서리")
}


/**
 * 사용처가 늘어나면 공통 모듈로 이동 필요
 */
inline fun <reified T : Enum<T>> enumValueOfOrNull(name: String): T? {
    return try {
        enumValueOf<T>(name)
    } catch (e: IllegalArgumentException) {
        null
    }
}
