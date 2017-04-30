package guardiantech.com.cn.swedit.database.item

import com.j256.ormlite.field.DatabaseField
import com.j256.ormlite.table.DatabaseTable

/**
 * Created by liupeiqi on 2017/4/28.
 */

@DatabaseTable(tableName = "users")
class UserItem() {
    @DatabaseField(id = true)
    lateinit var email: String
    @DatabaseField
    var userLevel: Int = 0
    @DatabaseField
    lateinit var token: String

    constructor(email: String, userLevel: Int, token: String): this() {
        this.email = email
        this.userLevel = userLevel
        this.token = token
    }
}