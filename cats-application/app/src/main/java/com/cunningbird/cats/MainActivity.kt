package com.cunningbird.cats

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setUpView()
    }

    private fun setUpView() {
        findViewById<BottomNavigationView>(R.id.navBottomBar).apply {
            setupWithNavController(findNavController(R.id.fragmentNavHost))
        }
    }

    /*override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1488) {
            //val photo: Bitmap = data?.extras?.get("data") as Bitmap
            //println("Flex")
            //findNavController(R.id.fragmentNavHost).navigate(R.id.uploadsFragment)
            //(inflatedView.findViewById(R.id.image) as ImageView).setImageBitmap(photo)
            // TODO put picture in fragment function
        }
    }*/
}