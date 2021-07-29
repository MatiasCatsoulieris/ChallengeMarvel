package android.example.com.challengemarvel.presentation

import android.example.com.challengemarvel.core.Resource
import android.example.com.challengemarvel.repositories.AccountRepo
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseUser
import java.lang.Exception

class AccountViewModel(private val accountRepo: AccountRepo) : ViewModel() {


    fun customLogin(email: String, password: String) = liveData<Resource<FirebaseUser?>> {
        emit(Resource.Loading())
        try {
            emit(Resource.Success(accountRepo.customLogin(email, password)))
        } catch (e: Exception) {
            emit(Resource.Failure(e))
        }
    }

    fun customRegister(email: String, password: String) =
        liveData<Resource<FirebaseUser?>> {
            emit(Resource.Loading())
            try {
                emit(Resource.Success(accountRepo.customRegister(email, password)))
            } catch (e: Exception) {
                emit(Resource.Failure(e))
            }
        }

    fun loginWithCredential(credential: AuthCredential) =
        liveData<Resource<FirebaseUser?>> {
            emit(Resource.Loading())
            try {
                emit(Resource.Success(accountRepo.loginWithCredential(credential)))
            } catch (e:Exception) {
                emit(Resource.Failure(e))
            }
        }

    fun sendEmail(email: String) = liveData<Resource<Unit>> {
        emit(Resource.Loading())
        try {
            emit(Resource.Success(accountRepo.sendEmailForRecovery(email)))
        } catch (e: Exception) {
            emit(Resource.Failure(e))
        }
    }


}