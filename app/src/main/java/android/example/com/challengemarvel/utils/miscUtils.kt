package android.example.com.challengemarvel.utils

import android.content.Context
import android.os.Build
import android.text.format.DateUtils
import android.util.Log
import android.util.Patterns
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import java.math.BigInteger
import java.security.MessageDigest
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.*

// Converts String to the required hash for the Marvel Api
fun String.toMD5Hash(): String {
    var pass = this
    var encryptedString: String? = null
    val md5: MessageDigest
    try {
        md5 = MessageDigest.getInstance("MD5")
        md5.update(pass.toByteArray(), 0, pass.length)
        pass = BigInteger(1, md5.digest()).toString(16)
        while (pass.length < 32) {
            pass = "0$pass"
        }
        encryptedString = pass
    } catch (e: Exception) {
        e.printStackTrace()
    }
    Log.d("hash", "$encryptedString")
    return encryptedString ?: ""
}

fun String.isValidEmail() : Boolean {
    return Patterns.EMAIL_ADDRESS.matcher(this).matches()
}

//View utilities
fun View.hide() {
    this.visibility = View.GONE
}
fun View.show() {
    this.visibility = View.VISIBLE
}

fun ImageView.loadImage(uri: String?, progressDrawable: CircularProgressDrawable) {
    val options = RequestOptions()
        .placeholder(progressDrawable)

    Glide.with(context)
        .setDefaultRequestOptions(options)
        .load(uri)
        .into(this)
}


@BindingAdapter("android:imageurl")
fun loadImage(view: ImageView, url: String?) {
    view.loadImage(url, getProgressDrawable(view.context))
}

fun getProgressDrawable(context: Context): CircularProgressDrawable {
    return CircularProgressDrawable(context).apply {
        strokeWidth = 10f
        centerRadius = 50f
    }
}

fun View.hideKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(windowToken, 0)
}

// Used as a workaround for not enabling clear text in Manifest
fun String.toHTTPS(): String {
    return this.substring(0, 4) + "s" + this.substring(4, this.length)
}




