package android.example.com.challengemarvel.repositories

import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseUser

interface AccountRepo {

    suspend fun customRegister(email: String, password: String): FirebaseUser?

    suspend fun customLogin(email: String, password: String): FirebaseUser?

    suspend fun loginWithCredential(credential: AuthCredential): FirebaseUser?

    suspend fun sendEmailForRecovery(email: String)
}