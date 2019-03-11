package com.eatoko.id

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val loginBtn = findViewById<View>(R.id.loginBtn) as Button
        val registerBtn = findViewById<View>(R.id.registerBtn) as Button

        loginBtn.setOnClickListener(View.OnClickListener { login() })
        registerBtn.setOnClickListener(View.OnClickListener { register()  })
        mAuth = FirebaseAuth.getInstance()
    }
    private fun register() {
        startActivity(Intent(this, RegisterActivity::class.java))
    }

    private fun login() {
        val emailInput = findViewById<View>(R.id.email) as EditText
        val passwordInput = findViewById<View>(R.id.password) as EditText

        var email = emailInput.text.toString()
        var password = passwordInput.text.toString()

        if (!email.isEmpty() && !password.isEmpty()){
            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, OnCompleteListener { task ->
                if (task.isSuccessful){
                    startActivity(Intent(this, MainActivity::class.java))
                    Toast.makeText(this, "success", Toast.LENGTH_LONG).show()
                }else{
                    Toast.makeText(this, "Error (", Toast.LENGTH_LONG).show()
                }
            })
        }else{
            Toast.makeText(this, "Please fill up the credential ;(", Toast.LENGTH_LONG).show()
        }

    }
}
