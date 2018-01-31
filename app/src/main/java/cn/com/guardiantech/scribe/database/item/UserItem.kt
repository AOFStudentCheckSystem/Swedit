package cn.com.guardiantech.scribe.database.item

import cn.com.guardiantech.scribe.util.NoArg
import com.j256.ormlite.field.DatabaseField
import com.j256.ormlite.table.DatabaseTable

/**
 * Created by liupeiqi on 2017/4/28.
 */

@DatabaseTable(tableName = "users")
@NoArg
class UserItem(
        @DatabaseField(id = true)
        var email: String,
        @DatabaseField
        var userLevel: Int = 0,
        @DatabaseField
        var token: String
)