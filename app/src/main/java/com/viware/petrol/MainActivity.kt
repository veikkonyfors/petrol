package com.viware.petrol


import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.provider.MediaStore
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.io.File


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnDashCam: Button = findViewById(R.id.button_dashcam)
        btnDashCam.setOnClickListener {
            //val intent = Intent(this@MainActivity, AboutActivity::class.java)
            //startActivity(intent)
            Toast.makeText(this, "btnDashCam clicked", Toast.LENGTH_LONG).show()
        }

        val btnCamera: Button = findViewById(R.id.button_camera)
        btnCamera.setOnClickListener {
            //Toast.makeText(this, "btnCamera clicked", Toast.LENGTH_LONG).show()
            btnCamera.setOnClickListener(View.OnClickListener {
                //
                val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                startActivityForResult(cameraIntent, 200)
            })
        }

        val btnFillUp: Button = findViewById(R.id.button_fillup)
        btnFillUp.setOnClickListener {
            //val intent = Intent(this@MainActivity, AboutActivity::class.java)
            //startActivity(intent)
            Toast.makeText(this, "btnFillUp clicked", Toast.LENGTH_LONG).show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == 200 && data != null){
            val imageView : ImageView = ImageView(this)
            imageView.setImageBitmap(data.extras?.get("data") as Bitmap)
            // Laita kuva pylonin tilalle
            //val pylonikuva: ImageView? = findViewById(R.id.pricetable_Image)
            //pylonikuva!!.setImageDrawable(imageView.drawable)


            val imgFile = File("/data/local/tmp/nestevihtipyloni.jpg")
            //val imgFile = File("/sdcard/download/nestevihtipyloni.jpg")

            if (imgFile.exists()) {
                val myBitmap = BitmapFactory.decodeFile(imgFile.absolutePath)
                val myImage = findViewById<View>(R.id.pricetable_Image) as ImageView
                myImage.setImageBitmap(myBitmap)
            }

        }
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.threedot_menu, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here.
        val id = item.getItemId()

        if (id == R.id.menuitem_about) {
            //Toast.makeText(this, "Item One Clicked", Toast.LENGTH_LONG).show()
            val intent = Intent(this@MainActivity, AboutActivity::class.java)
            startActivity(intent)
            return true
        }

        return super.onOptionsItemSelected(item)

    }

}