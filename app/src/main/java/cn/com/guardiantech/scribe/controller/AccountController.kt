package cn.com.guardiantech.scribe.controller

import cn.com.guardiantech.scribe.Global
import cn.com.guardiantech.scribe.api.API
import cn.com.guardiantech.scribe.api.request.authentication.AuthenticationRequest
import cn.com.guardiantech.scribe.api.request.authentication.CredentialRequest
import cn.com.guardiantech.scribe.api.request.authentication.PrincipalRequest
import cn.com.guardiantech.scribe.database.entity.CredentialType
import cn.com.guardiantech.scribe.database.entity.PrincipalType
import cn.com.guardiantech.scribe.database.entity.Session
import cn.com.guardiantech.scribe.eventbus.event.LoginEvent
import cn.com.guardiantech.scribe.eventbus.event.SessionChangeEvent
import com.j256.ormlite.dao.Dao
import java.util.regex.Pattern

/**
 * Created by liupeiqi on 2017/9/26.
 */
class AccountController {
    companion object {
        lateinit var sessionDao: Dao<Session, Int>
        private val emailPattern = Pattern.compile("^[^@]*[^ ]?@(?:[a-zA-Z0-9\\-]*?\\.[a-zA-Z]{2,}?)+?\$")
        private val usernamePattern = Pattern.compile("^[a-zA-Z]")

        private fun principalTypeOf(str: String): PrincipalType {
            if (emailPattern.matcher(str).matches()) return PrincipalType.EMAIL
            if (usernamePattern.matcher(str).find(0)) return PrincipalType.USERNAME
            return PrincipalType.PHONE
        }

        private fun credentialTypeOf(str: String): CredentialType {
            return CredentialType.PASSWORD
        }

        fun login(principal: String, credential: String, callback: (success: Boolean) -> Unit) {
            API.login(
                    AuthenticationRequest(
                            PrincipalRequest(
                                    type = principalTypeOf(principal),
                                    identification = principal
                            ),
                            CredentialRequest(
                                    type = credentialTypeOf(credential),
                                    secret = credential
                            )
                    )
            ) { success, error, session ->
                if (success) {
                    sessionDao.createOrUpdate(session)
                    API.apiHeaders["Authorization"] = session!!.sessionKey
                }
                Global.bus.post(LoginEvent(success, error ?: "Unknown"))
                callback(success)
            }
        }
    }
}