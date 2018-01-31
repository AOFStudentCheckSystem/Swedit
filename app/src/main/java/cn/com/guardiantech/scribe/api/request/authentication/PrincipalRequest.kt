package cn.com.guardiantech.scribe.api.request.authentication

import cn.com.guardiantech.scribe.database.entity.PrincipalType
import cn.com.guardiantech.scribe.util.NoArg

@NoArg
data class PrincipalRequest(
        val type: PrincipalType,
        val identification: String
)