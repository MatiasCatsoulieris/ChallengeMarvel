package android.example.com.challengemarvel.data.remote

import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.tasks.await


class AuthDataSource {

    suspend fun customLogin(email: String, password: String): FirebaseUser? {
        val authResult =
            FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password).await()
        return authResult.user
    }

    suspend fun customRegister(email: String, password: String): FirebaseUser? {
        val authResult =
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password).await()
        return authResult.user
    }

    suspend fun loginWithCredential(credential: AuthCredential): FirebaseUser? {
        return FirebaseAuth.getInstance().signInWithCredential(credential).await().user

    }

    fun sendEmailForRecovery(email: String) {
        FirebaseAuth.getInstance().sendPasswordResetEmail(email)
    }
}