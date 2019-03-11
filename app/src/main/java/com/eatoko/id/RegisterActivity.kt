package com.eatoko.id

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class RegisterActivity : AppCompatActivity() {
    val mAuth = FirebaseAuth.getInstance()
    lateinit var mDatabase : DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        mDatabase = FirebaseDatabase.getInstance().getReference("Names")
        val registerBtn = findViewById<View>(R.id.register)
        registerBtn.setOnClickListener(View.OnClickListener { view ->
            register()
        })
    }
    private fun register() {
        val emailInput = findViewById<View>(R.id.input_email) as EditText
        val passwordInput = findViewById<View>(R.id.input_password) as EditText
        val nameInput = findViewById<View>(R.id.name) as EditText

        var email = emailInput.text.toString()
        var password = passwordInput.text.toString()
        var name = nameInput.text.toString()

        if (!email.isEmpty() && !password.isEmpty() && !name.isEmpty()) {
            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, OnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = mAuth.currentUser
                    val uid = user!!.uid
                    mDatabase.child(uid).child("Name").setValue(name)
                    startActivity(Intent(this, MainActivity::class.java))
                    Toast.makeText(this, "Successfully registered :)", Toast.LENGTH_LONG).show()
                }else {
                    Toast.makeText(this, "Error registering, try again later :(", Toast.LENGTH_LONG).show()
                }
            })
        }else {
            Toast.makeText(this,"Please fill up the Credentials :|", Toast.LENGTH_LONG).show()
        }
    }
}
