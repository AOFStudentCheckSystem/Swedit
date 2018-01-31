package cn.com.guardiantech.scribe.database.entity

import cn.com.guardiantech.scribe.util.NoArg
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.j256.ormlite.field.ForeignCollectionField
import javax.persistence.*

/**
 * Created by liupeiqi on 2017/4/28.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
@Entity(name = "session")
@NoArg
class Session(
        @Id
        @GeneratedValue
        @Column
        var id: Int,

        @Column
        var sessionKey: String,

        @Column
        var isFullyAuthenticated: Boolean,

        @Column
        var isAuthenticated: Boolean,


        //TODO: Test Collection
        @ElementCollection
        @JoinColumn
        var authenticatedFactors: Collection<CredentialType>,

        @ElementCollection
        @JoinColumn
        var permissions: Collection<String>
)