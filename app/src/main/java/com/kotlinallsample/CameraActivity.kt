package com.kotlinallsample

import android.Manifest
import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.support.design.widget.Snackbar
import android.support.v4.content.FileProvider
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener

import kotlinx.android.synthetic.main.activity_camera.*
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class CameraActivity : AppCompatActivity() {

    val TAKE_PHOTO_CAMERA: Int = 1111
    val TAKE_PHOTO_GALLERY: Int = 2222
    var mCurrentPhotoPath: String = ""
    lateinit var imageFilePath: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
            validatePermissions()
            //  pickImageFrom()
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun launchCamera() {

        try {
            val imageFile = createImageFile()
            val callCameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            if (callCameraIntent.resolveActivity(packageManager) != null) {
                val authorities = packageName + ".fileprovider"
                val imageUri = FileProvider.getUriForFile(this, authorities, imageFile)
                callCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri)
                startActivityForResult(callCameraIntent, TAKE_PHOTO_CAMERA)
            }
        } catch (e: IOException) {
            Toast.makeText(this, "Could not create file!", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int,

                                  data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == TAKE_PHOTO_GALLERY && data != null && resultCode == Activity.RESULT_OK) {
            val selectedImage = data.data
            val filepath = arrayOf(MediaStore.Images.Media.DATA)
            val cursor = contentResolver.query(selectedImage, filepath, null, null, null)
            cursor.moveToFirst()
            val Index = cursor.getColumnIndex(filepath[0])
            val Picture = cursor.getString(Index)
            cursor.close()
            imgDisplay.setImageBitmap(BitmapFactory.decodeFile(Picture))
        } else if (resultCode == Activity.RESULT_OK
                && requestCode == TAKE_PHOTO_CAMERA) {
            if (resultCode == Activity.RESULT_OK) {
                imgDisplay.setImageBitmap(setScaledBitmap())
            }
        }
    }

    @Throws(IOException::class)
    fun createImageFile(): File {
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val imageFileName: String = "JPEG_" + timeStamp + "_"
        val storageDir: File = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        if (!storageDir.exists()) storageDir.mkdirs()
        val imageFile = File.createTempFile(imageFileName, ".jpg", storageDir)
        imageFilePath = imageFile.absolutePath
        return imageFile
    }

    fun setScaledBitmap(): Bitmap {
        val imageViewWidth = imgDisplay.width
        val imageViewHeight = imgDisplay.height

        val bmOptions = BitmapFactory.Options()
        bmOptions.inJustDecodeBounds = true
        BitmapFactory.decodeFile(imageFilePath, bmOptions)
        val bitmapWidth = bmOptions.outWidth
        val bitmapHeight = bmOptions.outHeight

        val scaleFactor = Math.min(bitmapWidth / imageViewWidth, bitmapHeight / imageViewHeight)

        bmOptions.inJustDecodeBounds = false
        bmOptions.inSampleSize = scaleFactor

        return BitmapFactory.decodeFile(imageFilePath, bmOptions)

    }

    fun pickFromGallery() {
        var intent = Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, TAKE_PHOTO_GALLERY)
    }

    private fun pickImageFrom() {

        AlertDialog.Builder(this@CameraActivity)
                .setTitle("Pick from")
                .setMessage("Select Image from following option.")
                .setPositiveButton("Gallery", { dialog, _ ->
                    dialog.dismiss()
                    pickFromGallery()
                })
                .setNegativeButton("Camera", { dialog, _ ->
                    dialog.dismiss()
                    launchCamera()
                })
                .show()

    }

    private fun processCapturedPhoto() {


        val cursor = contentResolver.query(Uri.parse(mCurrentPhotoPath),
                Array(1) { android.provider.MediaStore.Images.ImageColumns.DATA },
                null, null, null)
        cursor.moveToFirst()
        val photoPath = cursor.getString(0)
        cursor.close()
        val file = File(photoPath)
        val uri = Uri.fromFile(file)
        val bm: Bitmap? = BitmapFactory.decodeFile(file.absolutePath) as Bitmap
        // imgDisplay?.setImageBitmap(bm)

        imgDisplay?.setImageURI(Uri.parse(uri.toString()))
        /*
        val height = resources.getDimensionPixelSize(R.dimen.photo_height)
        val width = resources.getDimensionPixelSize(R.dimen.photo_width)

        val request = ImageRequestBuilder.newBuilderWithSource(uri)
                .setResizeOptions(ResizeOptions(width, height))
                .build()
        val controller = Fresco.newDraweeControllerBuilder()
                .setOldController(imgvPhoto?.controller)
                .setImageRequest(request)
                .build()
        imgDisplay?.controller = controller*/
    }

    private fun validatePermissions() {

        Dexter.withActivity(this)
                .withPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(object : PermissionListener {
                    override fun onPermissionGranted(
                            response: PermissionGrantedResponse?) {
                        pickImageFrom()
                    }

                    override fun onPermissionRationaleShouldBeShown(
                            permission: PermissionRequest?,
                            token: PermissionToken?) {

                        AlertDialog.Builder(this@CameraActivity)
                                .setTitle(
                                        "Title")
                                .setMessage(
                                        "Message")
                                .setNegativeButton(
                                        android.R.string.cancel,
                                        { dialog, _ ->
                                            dialog.dismiss()
                                            token?.cancelPermissionRequest()
                                        })
                                .setPositiveButton(android.R.string.ok,
                                        { dialog, _ ->
                                            dialog.dismiss()
                                            token?.continuePermissionRequest()
                                        })
                                .setOnDismissListener({
                                    token?.cancelPermissionRequest()
                                })
                                .show()
                    }

                    override fun onPermissionDenied(
                            response: PermissionDeniedResponse?) {
                        Toast.makeText(this@CameraActivity, "Denied", Toast.LENGTH_SHORT).show()

                    }
                })
                .check()
    }
}
