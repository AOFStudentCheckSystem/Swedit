package cn.com.guardiantech.scribe.api.request.authentication

import cn.com.guardiantech.scribe.util.NoArg

@NoArg
data class AuthenticationRequest (
        val principal: PrincipalRequest,
        val credential: CredentialRequest
)