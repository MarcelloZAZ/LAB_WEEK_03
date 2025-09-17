package com.example.lab_week_03

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.FragmentContainerView

// Listener untuk komunikasi fragment → activity
interface CoffeeListener {
    fun onSelected(id: Int)
}

class MainActivity : AppCompatActivity(), CoffeeListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Atur padding agar tidak tertutup status bar/navigation bar
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.fragment_container)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Tambahkan ListFragment pertama kali
        if (savedInstanceState == null) {
            findViewById<FragmentContainerView>(R.id.fragment_container).let { containerLayout ->
                val listFragment = ListFragment()
                supportFragmentManager.beginTransaction()
                    .add(containerLayout.id, listFragment)
                    .commit()
            }
        }
    }

    // Saat kopi dipilih, ganti ListFragment dengan DetailFragment
    override fun onSelected(id: Int) {
        findViewById<FragmentContainerView>(R.id.fragment_container).let { containerLayout ->
            val detailFragment = DetailFragment.newInstance(id)
            supportFragmentManager.beginTransaction()
                .replace(containerLayout.id, detailFragment)
                .addToBackStack(null) // biar bisa back ke ListFragment
                .commit()
        }
    }
}
