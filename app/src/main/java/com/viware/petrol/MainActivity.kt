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
private const val TEST_IMAGE = "content://com.viware.petrol.provider/cached_files/vihtinestepyloni_248.jpg"

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
                //pylonPreviewImage.setImageURI(uri)
                pylonPreviewImage.setImageURI(Uri.parse(TEST_IMAGE))
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
