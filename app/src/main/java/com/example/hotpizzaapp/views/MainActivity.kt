package com.example.hotpizzaapp.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.hotpizzaapp.R


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.ThemeHotPizzaApp)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Main
        val menuFragment = MenuFragment()
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flFragment, menuFragment)
            commit()
        }
    }
}