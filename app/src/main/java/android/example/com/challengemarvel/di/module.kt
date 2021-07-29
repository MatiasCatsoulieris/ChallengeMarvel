package android.example.com.challengemarvel.di

import android.example.com.challengemarvel.data.remote.AuthDataSource
import android.example.com.challengemarvel.data.remote.MarvelApi
import android.example.com.challengemarvel.presentation.AccountViewModel
import android.example.com.challengemarvel.presentation.MainViewModel
import android.example.com.challengemarvel.repositories.AccountRepo
import android.example.com.challengemarvel.repositories.AccountRepoImpl
import android.example.com.challengemarvel.repositories.MainRepo
import android.example.com.challengemarvel.repositories.MainRepoImpl
import android.example.com.challengemarvel.utils.BASE_URL
import android.example.com.challengemarvel.utils.PRIVATE_API_KEY
import android.example.com.challengemarvel.utils.PUBLIC_API_KEY
import android.example.com.challengemarvel.utils.toMD5Hash
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.CallAdapter
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

//Koin Modules

fun provideRetrofit(baseUrl: String, client: OkHttpClient, gson: Gson): Retrofit = Retrofit.Builder()
    .baseUrl(baseUrl)
    .addConverterFactory(GsonConverterFactory.create(gson))
    .client(client)
    .build()

fun provideGson(): Gson = GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create()

fun provideApiService(retrofit: Retrofit): MarvelApi = retrofit.create(MarvelApi::class.java)

fun provideOkHttpClient() = OkHttpClient().newBuilder()

    .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
    .connectTimeout(60L, TimeUnit.SECONDS)
    .readTimeout(60L, TimeUnit.SECONDS)
    .build()


val remoteModule = module {
    single<Gson> { provideGson() }
    single<OkHttpClient> { provideOkHttpClient() }
    single<Retrofit> { provideRetrofit(BASE_URL, get(), get()) }
    single<MarvelApi> { provideApiService(get()) }
}

val authDataSourceModule = module {
    single<AuthDataSource> { AuthDataSource() }
}

val repoModule = module {
    single<AccountRepo> { AccountRepoImpl(get()) }
    single<MainRepo> { MainRepoImpl(get())}
}

val viewModelModule = module {
    viewModel<AccountViewModel> { AccountViewModel(get()) }
    viewModel<MainViewModel>{ MainViewModel(get()) }
}