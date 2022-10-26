package com.viware.petrol

import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.io.File
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.lifecycle.lifecycleScope

// VN 20221026
//import androidx.viewbinding.BuildConfig
//import com.viware.petrol.BuildConfig

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    /* Change deprecated startActivityForResult + onActivityResult
        to Androidx Activity Result API methods
        registerForActivityResult(ActivityResultContracts.TakePicture()) and
        launch() method of the returned ActivityResultLauncher object.
    */

    // Uri where image taken by TakePicture contract is accessible
    private var cameraImageUri: Uri? = null
    // A preview imageview of the shot pylon image. By lazy to speed up startup
    private val pylonPreviewImage by lazy { findViewById<ImageView>(R.id.pricetable_Image) }

    // Register for taking the photo.
    // ActivityResultLauncher(Uri) returned, with launch(Uri) method.
    // Registering takes place using a lambda function: If the launch(Uri) was successful, imageUri for pylonPreviewImage gets updated to image just shot.
    // The old startActivityForResult way commented down there on btnCamera.setOnClickListener method.
    private val takeImageResult = registerForActivityResult(ActivityResultContracts.TakePicture()) { isSuccess ->
        if (isSuccess) {
            cameraImageUri?.let { uri ->
                pylonPreviewImage.setImageURI(uri)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnDashCam: Button = findViewById(R.id.button_dashcam)
        btnDashCam.setOnClickListener {
            Toast.makeText(this, "btnDashCam clicked", Toast.LENGTH_LONG).show()
        }

        val btnCamera: Button = findViewById(R.id.button_camera)
        btnCamera.setOnClickListener {
            takeImage()
            //Toast.makeText(this, "btnCamera clicked", Toast.LENGTH_LONG).show()
            // Old startActivityForResult way commented out now
            //    val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            //    startActivityForResult(cameraIntent, 200)
        }

        val btnFillUp: Button = findViewById(R.id.button_fillup)
        btnFillUp.setOnClickListener {
            //val intent = Intent(this@MainActivity, AboutActivity::class.java)
            //startActivity(intent)
            Toast.makeText(this, "btnFillUp clicked", Toast.LENGTH_LONG).show()
        }
    }

    private fun takeImage() {
        lifecycleScope.launchWhenStarted {
            getTmpFileUri().let { uri ->
                cameraImageUri = uri
                Log.i(TAG,"uri: $uri")
                takeImageResult.launch(uri)
            }
        }
    }

    private fun getTmpFileUri(): Uri {
        val tmpFile = File.createTempFile("tmp_image_file", ".png", cacheDir).apply {
            createNewFile()
            deleteOnExit()
        }

        return FileProvider.getUriForFile(applicationContext, "${BuildConfig.APPLICATION_ID}.provider", tmpFile)
    }

    // This old onActivityResult way will now be commented out
    // We use ActivityResultLauncher.launch(Uri) method instead in btnCamera lictener.
    /*override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
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
    }*/

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
