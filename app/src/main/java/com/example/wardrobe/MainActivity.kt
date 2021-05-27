package com.example.wardrobe

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import com.google.android.material.floatingactionbutton.FloatingActionButton
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.ViewPager
import com.example.wardrobe.config.*
import com.example.wardrobe.database.model.Shirt
import com.example.wardrobe.database.model.Trouser
import com.example.wardrobe.ui.main.adapter.ImagePagerAdapter
import com.example.wardrobe.ui.main.PageViewModel
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var pageViewModel: PageViewModel
    lateinit var shirtPhotoPath: String
    lateinit var trouserPhotoPath: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /*val tabs: TabLayout = findViewById(R.id.tabs)
        tabs.setupWithViewPager(viewPager)*/

        val viewPagerShirt: ViewPager = findViewById(R.id.viewPagerShirt)
        val viewPagerTrousers: ViewPager = findViewById(R.id.viewPagerTrousers)

        var shirtPagerAdapter: ImagePagerAdapter
        var trouserPagerAdapter: ImagePagerAdapter

        pageViewModel = ViewModelProvider(this).get(PageViewModel::class.java)

        pageViewModel.loadShirtImageList().observe(this, Observer<List<Shirt>> {
            pageViewModel.setShirtList(it)
            shirtPagerAdapter =
                ImagePagerAdapter(
                    SHIRT_FRAGMENT,
                    it.size,
                    supportFragmentManager
                )
            viewPagerShirt.adapter = shirtPagerAdapter

            // TEST
            for ((index, image) in it.withIndex()){
                if(DEBUG_ON) Log.e("TEST", "act_shirt image is $index -> $image")
            }
            // TEST
        })

        pageViewModel.loadTrouserImageList().observe(this, Observer<List<Trouser>> {
            pageViewModel.setTrouserList(it)
            trouserPagerAdapter =
                ImagePagerAdapter(
                    TROUSER_FRAGMENT,
                    it.size,
                    supportFragmentManager
                )
            viewPagerTrousers.adapter = trouserPagerAdapter

            // TEST
            for (image in it){
                if(DEBUG_ON) Log.e("TEST", "act_trouser image is ${image}")
            }
            // TEST
        })

        setupFab()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when(requestCode){
            REQUEST_CODE_CAPTURE_SHIRT_IMAGE_PERMISSION -> {
                if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    captureImageFromCamera(REQUEST_SHIRT_IMAGE_CAPTURE)
                } else {
                    Toast.makeText(this, R.string.hint_take_shirt_picture_camera_permission, Toast.LENGTH_SHORT).show()
                }
            }
            REQUEST_CODE_CAPTURE_TROUSER_IMAGE_PERMISSION -> {
                if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    captureImageFromCamera(REQUEST_TROUSER_IMAGE_CAPTURE)
                } else {
                    Toast.makeText(this, R.string.hint_take_trouser_picture_camera_permission, Toast.LENGTH_SHORT).show()
                }
            }
            else -> {
                super.onRequestPermissionsResult(requestCode, permissions, grantResults)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when(resultCode){
            Activity.RESULT_OK -> {
                when(requestCode) {
                    REQUEST_SHIRT_IMAGE_CAPTURE -> {
                        val file = File(shirtPhotoPath)
                        val imageString = pageViewModel.getBase64EncodedString(file)
                        // pageViewModel.addShirtToList(Shirt(1, "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"))
                        pageViewModel.addShirtToList(Shirt(0, imageString))
                    }
                    REQUEST_TROUSER_IMAGE_CAPTURE -> {
                        val file = File(trouserPhotoPath)
                        val imageString = pageViewModel.getBase64EncodedString(file)
                        // pageViewModel.addShirtToList(Shirt(1, "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"))
                        pageViewModel.addTrouserToList(Trouser(0, imageString))
                    }
                }
            }
            else -> super.onActivityResult(requestCode, resultCode, data)
        }

    }

    private fun setupFab(){
        val fabAddShirt: FloatingActionButton = findViewById(R.id.fabAddShirt)
        fabAddShirt.setOnClickListener { view ->
            /*Snackbar.make(view, "Shirt added to Wardrobe", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()*/

            pageViewModel.doShuffle.value = false
            getImageFromCamera(REQUEST_SHIRT_IMAGE_CAPTURE)
        }

        val fabShuffle: FloatingActionButton = findViewById(R.id.fabShuffle)
        fabShuffle.setOnClickListener { view ->
            Toast.makeText(this, "Looking for a new look...", Toast.LENGTH_SHORT).show()
            // pageViewModel.shuffleList()
            pageViewModel.doShuffle.value = true
            //pageViewModel.doShuffle.value = true
        }

        val fabFavourite: FloatingActionButton = findViewById(R.id.fabFavourite)
        fabFavourite.setOnClickListener { view ->
            // TODO to be implemented
            Toast.makeText(this, "Coming Soon...", Toast.LENGTH_SHORT).show()
        }

        val fabAddTrousers: FloatingActionButton = findViewById(R.id.fabAddTrousers)
        fabAddTrousers.setOnClickListener { view ->
            pageViewModel.doShuffle.value = false
            getImageFromCamera(REQUEST_TROUSER_IMAGE_CAPTURE)
        }
    }

    private fun getImageFromCamera(requestCode: Int){
        when {
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED -> {
                captureImageFromCamera(requestCode)
            }
            else -> {
                when(requestCode){
                    REQUEST_SHIRT_IMAGE_CAPTURE -> {
                        requestPermissions(
                            arrayOf(Manifest.permission.CAMERA),
                            REQUEST_CODE_CAPTURE_SHIRT_IMAGE_PERMISSION)
                    }
                    REQUEST_TROUSER_IMAGE_CAPTURE -> {
                        requestPermissions(
                            arrayOf(Manifest.permission.CAMERA),
                            REQUEST_CODE_CAPTURE_TROUSER_IMAGE_PERMISSION)
                    }
                }
            }
        }
        // pageViewModel.addShirtToList(Shirt(2,"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"))
    }

    private fun captureImageFromCamera(requestCode: Int){
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if(intent.resolveActivity(packageManager) != null){
            var file: File? = null
            try {
                file = createImageFile(requestCode)
            } catch (e: IOException){
                if(DEBUG_ON) e.printStackTrace()
            }
            file?.let {
                val imageUri = FileProvider.getUriForFile(this, "$packageName.provider", file)
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri)
                startActivityForResult(intent, requestCode)
            }
        }
    }

    @Throws(IOException::class)
    private fun createImageFile(requestCode: Int): File {
        // Create an image file name
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.ENGLISH).format(Date())
        val storageDir: File? = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
            "JPEG_${timeStamp}_", /* prefix */
            ".jpg", /* suffix */
            storageDir /* directory */
        ).apply {
            // Save a file: path for use with ACTION_VIEW intents
            when(requestCode){
                REQUEST_SHIRT_IMAGE_CAPTURE -> shirtPhotoPath = absolutePath
                REQUEST_TROUSER_IMAGE_CAPTURE -> trouserPhotoPath = absolutePath
            }
        }
    }

}