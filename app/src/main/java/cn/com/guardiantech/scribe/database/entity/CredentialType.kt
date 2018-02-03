package cn.com.guardiantech.scribe.database.entity

import java.io.Serializable

/**
 * Created by dedztbh on 18-1-31.
 */
enum class CredentialType(val weight: Short) : Serializable {
    PASSWORD(0b100000000000000),
    TOTP(0b000000000000001)
}