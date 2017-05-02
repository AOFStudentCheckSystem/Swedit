package cn.com.guardiantech.scribe.database.item

import com.j256.ormlite.field.DatabaseField
import com.j256.ormlite.table.DatabaseTable

/**
 * Created by liupeiqi on 2017/4/28.
 */

@com.j256.ormlite.table.DatabaseTable(tableName = "users")
class UserItem() {
    @com.j256.ormlite.field.DatabaseField(id = true)
    lateinit var email: String
    @com.j256.ormlite.field.DatabaseField
    var userLevel: Int = 0
    @com.j256.ormlite.field.DatabaseField
    lateinit var token: String

    constructor(email: String, userLevel: Int, token: String): this() {
        this.email = email
        this.userLevel = userLevel
        this.token = token
    }
}