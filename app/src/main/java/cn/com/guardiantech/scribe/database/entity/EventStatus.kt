package cn.com.guardiantech.scribe.database.entity

/**
 * Created by dedztbh on 18-1-31.
 */
enum class EventStatus(val status: Int) {
    FUTURE(0),
    BOARDING(1),
    COMPLETED(2)
}