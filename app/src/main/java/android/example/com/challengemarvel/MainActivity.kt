package android.example.com.challengemarvel

import android.example.com.challengemarvel.databinding.ActivityMainBinding
import android.example.com.challengemarvel.utils.hide
import android.example.com.challengemarvel.utils.show
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainer) as NavHostFragment
        navController = navHostFragment.navController
        binding.bottomNavigationView.setupWithNavController(navController)
        hideBottomNavigation()
    }

    //Hides bottom Navigation depending on current fragment
    private fun hideBottomNavigation() {
        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            when (destination.id) {
                R.id.loginFragment -> binding.bottomNavigationView.hide()
                R.id.registerFragment -> binding.bottomNavigationView.hide()
                R.id.detailFragment -> binding.bottomNavigationView.hide()
                R.id.recoverPasswordFragment -> binding.bottomNavigationView.hide()
                else -> binding.bottomNavigationView.show()
            }

        }
    }
}