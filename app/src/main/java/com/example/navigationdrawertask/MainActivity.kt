package com.example.navigationdrawertask

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.navigationdrawertask.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    private lateinit var editTextHome: EditText
    private lateinit var btnSubmit: Button

    private lateinit var sharedPreferences: SharedPreferences
    private val PREF_NAME = "MyPrefs"
    private val TEXT_KEY = "saved_text"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarMain.toolbar)

        sharedPreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

        val navController = findNavController(R.id.nav_host_fragment_content_main)
        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView

        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        editTextHome = findViewById(R.id.text_home)
        btnSubmit = findViewById(R.id.button)

        btnSubmit.setOnClickListener {
            val enteredText = editTextHome.text.toString().trim()
            if (enteredText.isNotEmpty()) {
                displayTextInDrawer(enteredText)
                editTextHome.text.clear()
                showToast("Message Sent")
            }
        }

        val savedText = sharedPreferences.getString(TEXT_KEY, "")
        if (!savedText.isNullOrEmpty()) {
            displayTextInDrawer(savedText)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    private fun displayTextInDrawer(text: String) {
        val navView: NavigationView = binding.navView
        val headerView = navView.getHeaderView(0)
        val drawerTextView: TextView = headerView.findViewById(R.id.drawer_layout)
        drawerTextView.text = text

        sharedPreferences.edit().putString(TEXT_KEY, text).apply()
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}