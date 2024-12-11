package com.example.projectcapstone

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity

class LoginActivity : ComponentActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val LBtn1: Button = findViewById(R.id.L_btn_1) // Ubah menjadi Button, bukan TextView
        LBtn1.setOnClickListener(this)

        val txtSignup: TextView = findViewById(R.id.txt_signup)
        txtSignup.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.L_btn_1 -> {
                val edtEmail: EditText = findViewById(R.id.L_edt_email)
                val edtPass: EditText = findViewById(R.id.L_edt_pass)

                val email = edtEmail.text.toString()
                val password = edtPass.text.toString()

                // Validasi input email dan password
                if (email.isEmpty()) {
                    edtEmail.error = "Enter Your Email"
                    return
                }
                if (password.isEmpty()) {
                    edtPass.error = "Enter Your Password"
                    return
                }

                // Jika validasi berhasil, lanjutkan ke MenuActivity
                val moveIntent = Intent(this@LoginActivity, MenuActivity::class.java)
                startActivity(moveIntent)
            }
            R.id.txt_signup -> {
                // Pindah ke RegisterActivity
                val moveIntent = Intent(this@LoginActivity, RegisterActivity::class.java)
                startActivity(moveIntent)
            }
        }
    }
}
