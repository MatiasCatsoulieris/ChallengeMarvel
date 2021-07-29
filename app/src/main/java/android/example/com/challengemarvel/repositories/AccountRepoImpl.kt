package android.example.com.challengemarvel.repositories

import android.example.com.challengemarvel.data.remote.AuthDataSource
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseUser

class AccountRepoImpl(private val authDataSource: AuthDataSource) : AccountRepo {


    override suspend fun customRegister(
        email: String,
        password: String
    ): FirebaseUser? = authDataSource.customRegister(email, password)

    override suspend fun customLogin(email: String, password: String): FirebaseUser? =
        authDataSource.customLogin(email, password)

    override suspend fun loginWithCredential(credential: AuthCredential): FirebaseUser? =
        authDataSource.loginWithCredential(credential)

    override suspend fun sendEmailForRecovery(email: String) = authDataSource.sendEmailForRecovery(email)


}
