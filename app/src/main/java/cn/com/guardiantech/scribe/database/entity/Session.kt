package cn.com.guardiantech.scribe.database.entity

import cn.com.guardiantech.scribe.util.NoArg
import javax.persistence.*

/**
 * Created by liupeiqi on 2017/4/28.
 */

@Entity(name = "session")
@NoArg
class Session(
        @Id
        @Column
        var sessionKey: String,

        @Column
        var isFullyAuthenticated: Boolean,

        @Column
        var isAuthenticated: Boolean,

        @ElementCollection(fetch = FetchType.EAGER)
        @CollectionTable(joinColumns = [(JoinColumn(name = "id"))])
        var authenticatedFactors: Collection<CredentialType>,

        var permissions: Boolean
)