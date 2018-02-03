package cn.com.guardiantech.scribe.database.entity

import cn.com.guardiantech.scribe.util.NoArg
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.j256.ormlite.field.DataType
import com.j256.ormlite.field.DatabaseField
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

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
        @DatabaseField(dataType = DataType.SERIALIZABLE)
        var authenticatedFactors: HashSet<CredentialType>,

        @DatabaseField(dataType = DataType.SERIALIZABLE)
        var permissions: HashSet<String>
)