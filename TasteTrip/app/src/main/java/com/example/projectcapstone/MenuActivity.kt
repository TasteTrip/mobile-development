package com.example.projectcapstone

import android.content.Intent
import android.content.pm.PackageManager
import android.Manifest
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.projectcapstone.ml.Efficientnetv2sModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.tensorflow.lite.support.image.TensorImage
import java.io.InputStreamReader

class MenuActivity : ComponentActivity() {

    private val CAMERA_PERMISSION_REQUEST_CODE = 100
    private lateinit var imageView: ImageView
    private lateinit var openCameraLauncher: ActivityResultLauncher<Intent>
    private lateinit var labelTextView: TextView

    private lateinit var foodList: List<FoodItem>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        // Load JSON data
        loadFoodData()

        // Temukan view dari layout
        val openCameraButton: ImageButton = findViewById(R.id.M_imagebutton_camera)
        imageView = findViewById(R.id.M_img_1)
        labelTextView = findViewById(R.id.M_text_description)  // TextView untuk menampilkan hasil

        // Inisialisasi ActivityResultLauncher
        openCameraLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == RESULT_OK) {
                // Ambil data gambar dari Intent
                val photo: Bitmap? = result.data?.extras?.get("data") as Bitmap?
                photo?.let {
                    // Menyesuaikan ukuran gambar dengan ImageView
                    val resizedBitmap = Bitmap.createScaledBitmap(it, imageView.width, imageView.height, false)
                    imageView.setImageBitmap(resizedBitmap)

                    // Proses gambar dengan TensorFlow Lite
                    processImageWithTFModel(resizedBitmap)
                }
            } else {
                Toast.makeText(this, "Gagal mengambil foto", Toast.LENGTH_SHORT).show()
            }
        }

        // Set listener pada tombol kamera
        openCameraButton.setOnClickListener {
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.CAMERA
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                openCamera()
            } else {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.CAMERA),
                    CAMERA_PERMISSION_REQUEST_CODE
                )
            }
        }
    }

    private fun openCamera() {
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (cameraIntent.resolveActivity(packageManager) != null) {
            openCameraLauncher.launch(cameraIntent)
        } else {
            Toast.makeText(this, "Tidak ada aplikasi kamera yang tersedia", Toast.LENGTH_SHORT).show()
        }
    }

    private fun processImageWithTFModel(bitmap: Bitmap) {
        // Memuat model TensorFlow Lite
        val model = Efficientnetv2sModel.newInstance(this)

        // Mengonversi gambar ke TensorImage
        val image = TensorImage.fromBitmap(bitmap)

        // Menjalankan inferensi
        val outputs = model.process(image)
        val probability = outputs.probabilityAsCategoryList

        // Menampilkan label dan probabilitas
        val result = probability.maxByOrNull { it.score }
        result?.let { detectedLabel ->
            val detectedFood = foodList.find { it.name.equals(detectedLabel.label, ignoreCase = true) }
            if (detectedFood != null) {
                labelTextView.text = "${detectedFood.name}: ${detectedFood.info}"
            } else {
                labelTextView.text = "Tidak ditemukan informasi makanan untuk: ${detectedLabel.label}"
            }
        }

        // Membebaskan sumber daya model
        model.close()
    }

    private fun loadFoodData() {
        try {
            val inputStream = resources.openRawResource(R.raw.food_data)
            val reader = InputStreamReader(inputStream)
            val type = object : TypeToken<List<FoodItem>>() {}.type
            foodList = Gson().fromJson(reader, type)
            reader.close()
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(this, "Gagal memuat data makanan", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == CAMERA_PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openCamera()
            } else {
                Toast.makeText(this, "Izin kamera ditolak", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
