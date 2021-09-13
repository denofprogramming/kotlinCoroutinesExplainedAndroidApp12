package com.denofprogramming.mycoroutineapplication

import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.denofprogramming.mycoroutineapplication.network.allImages
import com.denofprogramming.mycoroutineapplication.shared.uilt.logMessage
import com.denofprogramming.mycoroutineapplication.ui.main.MainFragment
import java.io.IOException

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment.newInstance())
                .commitNow()
        }


        assets.list("flowers")?.let { imageNames ->
            imageNames.forEach { imageName ->
                try {
                    assets.open("flowers/$imageName").run {
                        val bitmap = BitmapFactory.decodeStream(this)
                        logMessage("$imageName is $bitmap")
                        allImages.add(bitmap)
                    }
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }

    }


}