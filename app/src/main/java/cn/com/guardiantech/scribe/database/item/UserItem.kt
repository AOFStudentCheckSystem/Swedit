package cn.com.guardiantech.scribe.database.item

import cn.com.guardiantech.scribe.util.NoArg

/**
 * Created by liupeiqi on 2017/4/28.
 */

@com.j256.ormlite.table.DatabaseTable(tableName = "users")
@NoArg
class UserItem(
        @com.j256.ormlite.field.DatabaseField(id = true)
        var email: String,
        @com.j256.ormlite.field.DatabaseField
        var userLevel: Int = 0,
        @com.j256.ormlite.field.DatabaseField
        var token: String
)