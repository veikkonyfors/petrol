package com.viware.petrol


import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
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
import android.util.Log
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.drawToBitmap

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {

    /* pitäisi laittaa sivuun deprekoitu startActivityForResult + onActivityResult versio
       Käytetään sen sijaan Activity Result APIn
    registerForActivityResult ja palautetun ActivityResultLauncher.launch() metodia*/

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnDashCam: Button = findViewById(R.id.button_dashcam)
        btnDashCam.setOnClickListener {
            Toast.makeText(this, "btnDashCam clicked", Toast.LENGTH_LONG).show()
        }

        val btnCamera: Button = findViewById(R.id.button_camera)
        btnCamera.setOnClickListener {
            //Toast.makeText(this, "btnCamera clicked", Toast.LENGTH_LONG).show()
                val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                startActivityForResult(cameraIntent, 200)
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
            //imageView.setImageBitmap(data.extras?.get("data") as Bitmap)
            // Laita kuva pylonin tilalle
            //val pylonikuva: ImageView? = findViewById(R.id.pricetable_Image)
            //pylonikuva!!.setImageDrawable(imageView.drawable)

            /* Let's take a fake photo from /data/local/tmp to OCR prices.
            Need to use phone over wifi to test in reality. Later on then . . .
             */

            val imagePath="/data/local/tmp/vihtinestepyloni_99.png"

            val imgFile = File(imagePath)
            // sdcardilla ei saa permissioita kohdalleen,kun taitaa olla fatti. On aina rw-rw----.
            //val imgFile = File("/sdcard/download/nestevihtipyloni.jpg")
            // adb shell ja  chmod sieltä ei tee mitään, ei anna kuitenkaan virhettäkään.

            if (imgFile.exists()) {

                // show photo on device
                val myBitmap = BitmapFactory.decodeFile(imgFile.absolutePath)
                val myImage = findViewById<View>(R.id.pricetable_Image) as ImageView
                myImage.setImageBitmap(myBitmap)
                // And then handle prices from photo
                // fake
                //val handlePylonPrices = HandlePylonPrices(imageView.drawToBitmap())
                // real
                //val handlePylonPrices = HandlePylonPrices(R.id.button_camera)
            } else Log.d(TAG, "Unable to access image file:$imagePath")



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
