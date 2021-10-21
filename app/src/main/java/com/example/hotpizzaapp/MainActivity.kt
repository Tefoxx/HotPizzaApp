package com.example.hotpizzaapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater


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