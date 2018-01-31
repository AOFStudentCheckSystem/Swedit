package cn.com.guardiantech.scribe.api.request.authentication

import cn.com.guardiantech.scribe.database.entity.CredentialType
import cn.com.guardiantech.scribe.util.NoArg

@NoArg
data class CredentialRequest(
        val type: CredentialType,
        var secret: String
)