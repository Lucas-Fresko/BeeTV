package database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    //used to be val's but complained about initialization
    @PrimaryKey(autoGenerate = true) var uid: Int,
    @ColumnInfo(name = "username") var username: String?,
    @ColumnInfo(name = "password") var password: String?,
    @ColumnInfo(name = "email") var email: String?,
    @ColumnInfo(name = "login_date") val login_date: Long? //What variable type does it need to be?
)