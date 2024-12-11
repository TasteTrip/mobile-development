package com.example.projectcapstone

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.ComponentActivity
//import androidx.activity.viewModels
//import androidx.appcompat.app.AppCompatActivity
//import androidx.activity.compose.setContent
//import androidx.activity.enableEdgeToEdge
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.padding
//import androidx.compose.material3.Scaffold
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.tooling.preview.Preview
//import com.example.projectcapstone.ui.theme.ProjectCapstoneTheme

class MainActivity : ComponentActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btn1: Button = findViewById(R.id.btn_1)
        btn1.setOnClickListener(this) // Mengatur listener untuk tombol

        val btn2: Button = findViewById(R.id.btn_2)
        btn2.setOnClickListener(this) // Mengatur listener untuk tombol

        val txtGuest: TextView = findViewById(R.id.txt_guest)
        txtGuest.setOnClickListener(this) // Mengatur listener untuk tombol
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_1 -> {
                // Memulai LoginActivity
                val moveIntent = Intent(this@MainActivity, LoginActivity::class.java)
                startActivity(moveIntent)
            }
            R.id.btn_2 -> {
                // Memulai RegisterActivity
                val moveIntent = Intent(this@MainActivity, RegisterActivity::class.java)
                startActivity(moveIntent)
            }
            R.id.txt_guest -> {
                // Memulai RegisterActivity
                val moveIntent = Intent(this@MainActivity, MenuActivity::class.java)
                startActivity(moveIntent)
            }
        }
    }
}