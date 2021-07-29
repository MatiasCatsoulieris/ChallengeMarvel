package android.example.com.challengemarvel.ui.account

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.example.com.challengemarvel.R
import android.example.com.challengemarvel.core.Resource
import android.example.com.challengemarvel.databinding.FragmentLoginBinding
import android.example.com.challengemarvel.presentation.AccountViewModel
import android.example.com.challengemarvel.utils.hideKeyboard
import android.example.com.challengemarvel.utils.isValidEmail
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.navigation.Navigation
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginResult
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginFragment : Fragment() {

    //ViewBinding
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    //Auth
    private val firebaseAuth by lazy { FirebaseAuth.getInstance() }
    //ViewModel
    private val viewModel by viewModel<AccountViewModel>()
    // Facebook Callback Manager
    private lateinit var callbackManager: CallbackManager
    //TextWatcher
    private val textWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            validateInfo()
            binding.mailEditLayout.error = null
            binding.passwordEditLayout.error = null
        }

        override fun afterTextChanged(s: Editable?) {
        }
    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        callbackManager = CallbackManager.Factory.create()
        checkCurrentUser()
        setUiListeners()
        setFacebookLoginListener()
    }


    //Checks if user is in session
    private fun checkCurrentUser() {
        firebaseAuth.currentUser?.let {
            Navigation.findNavController(binding.root).navigate(LoginFragmentDirections.actionLoginToHome())
        }
    }

    private fun setUiListeners() {
        //Edit text watchers
        binding.mailEditText.addTextChangedListener(textWatcher)
        binding.passwordEditText.addTextChangedListener(textWatcher)
        binding.registerTextView.setOnClickListener {
            Navigation.findNavController(it)
                .navigate(LoginFragmentDirections.actionLoginToRegister())
        }
        binding.forgotPasswordTv.setOnClickListener {
            Navigation.findNavController(it).navigate(LoginFragmentDirections.actionLoginToRecover())
        }
        binding.loginButton.setOnClickListener {
            if (validateInfo()) {
                val email = binding.mailEditText.text.toString()
                val password = binding.passwordEditText.text.toString()
                login(email, password)
            }
        }
    }

    private fun setFacebookLoginListener() {
        binding.facebookLogin.setReadPermissions("email", "public_profile")
        binding.facebookLogin.fragment = this
        binding.facebookLogin.registerCallback(callbackManager, object: FacebookCallback<LoginResult> {
            override fun onSuccess(result: LoginResult?) {
                result?.let {
                    handleFacebookAccessToken(result.accessToken)
                }
            }

            override fun onCancel() {
                Toast.makeText(requireContext(), "Cancelled", Toast.LENGTH_SHORT).show()
            }

            override fun onError(error: FacebookException?) {
                Toast.makeText(requireContext(), "There was an error", Toast.LENGTH_SHORT).show()
            }

        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        callbackManager.onActivityResult(requestCode, resultCode, data)
    }


    private fun handleFacebookAccessToken(accessToken: AccessToken?) {
        accessToken?.let {
            val credential = FacebookAuthProvider.getCredential(accessToken.token)
            viewModel.loginWithCredential(credential).observe(viewLifecycleOwner, { resource ->
                when (resource) {
                    is Resource.Loading -> {
                        binding.loginButton.isEnabled = false
                        binding.loadingProgressBar.visibility = View.VISIBLE
                    }
                    is Resource.Success -> {
                        binding.loadingProgressBar.visibility = View.GONE
                        Navigation.findNavController(binding.root)
                            .navigate(LoginFragmentDirections.actionLoginToHome())
                    }
                    is Resource.Failure -> {
                        binding.passwordEditLayout.error = null
                        binding.mailEditLayout.error = null
                        binding.loadingProgressBar.visibility = View.GONE
                        Toast.makeText(
                            requireContext(),
                            "Login with facebook failed",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            })
        }
    }

    private fun login(email: String, password: String) {
        binding.root.hideKeyboard()
        viewModel.customLogin(email, password).observe(viewLifecycleOwner, { resource ->
            when (resource) {
                is Resource.Loading -> {
                    binding.loginButton.isEnabled = false
                    binding.loadingProgressBar.visibility = View.VISIBLE
                }
                is Resource.Success -> {
                    binding.mailEditText.text?.clear()
                    binding.passwordEditText.text?.clear()
                    binding.loadingProgressBar.visibility = View.GONE
                    Navigation.findNavController(binding.root)
                        .navigate(LoginFragmentDirections.actionLoginToHome())
                }
                is Resource.Failure -> {
                    binding.passwordEditLayout.error = " "
                    binding.mailEditLayout.error = " "
                    binding.loadingProgressBar.visibility = View.GONE
                    Toast.makeText(
                        requireContext(),
                        "The combination of email and password is incorrect. Please try again",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        })
    }


    private fun validateInfo(): Boolean {
        return if (binding.mailEditText.text.toString().isValidEmail()
            && binding.passwordEditText.text.toString().isNotEmpty()
        ) {
            binding.loginButton.isEnabled = true
            true
        } else {
            binding.loginButton.isEnabled = false
            false
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}