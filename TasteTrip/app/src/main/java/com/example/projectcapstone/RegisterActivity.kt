package com.example.projectcapstone

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import com.example.projectcapstone.LoginActivity

class RegisterActivity : ComponentActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val RBtn1: TextView = findViewById(R.id.R_btn_1)
        RBtn1.setOnClickListener(this)

        val txtSignin: TextView = findViewById(R.id.txt_signin)
        txtSignin.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.txt_signin -> {
                // Pindah ke LoginActivity
                val moveIntent = Intent(this@RegisterActivity, LoginActivity::class.java)
                startActivity(moveIntent)
            }
            R.id.R_btn_1 -> {
                // Ambil input dari EditText
                val edtName: EditText = findViewById(R.id.edt_name)
                val edtEmail: EditText = findViewById(R.id.edt_email)
                val edtPass: EditText = findViewById(R.id.edt_pass)
                val edtCpass: EditText = findViewById(R.id.edt_cpass)

                val name = edtName.text.toString()
                val email = edtEmail.text.toString()
                val password = edtPass.text.toString()
                val confirmPassword = edtCpass.text.toString()

                // Validasi input
                if (name.isEmpty()) {
                    edtName.error = "Enter Your Name"
                    return
                }
                if (email.isEmpty()) {
                    edtEmail.error = "Enter Your Email"
                    return
                }
                if (password.isEmpty()) {
                    edtPass.error = "Enter Your Password"
                    return
                }
                if (confirmPassword.isEmpty()) {
                    edtCpass.error = "Confirm Your Password"
                    return
                }
                if (password != confirmPassword) {
                    edtCpass.error = "Password Not Match"
                    return
                }

                // Lanjutkan jika semua input valid
                val moveIntent = Intent(this@RegisterActivity, LoginActivity::class.java)
                startActivity(moveIntent)
            }
        }
    }
}
