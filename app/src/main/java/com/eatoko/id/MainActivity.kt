package com.eatoko.id

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class MainActivity : AppCompatActivity() {

    private val mAuth = FirebaseAuth.getInstance()
    lateinit var mDatabase : DatabaseReference
    var user = FirebaseAuth.getInstance().currentUser
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val logoutbtn = findViewById<View>(R.id.logout) as Button
        val name = findViewById<View>(R.id.displayname) as TextView
        var uid = user!!.uid

        logoutbtn.setOnClickListener(View.OnClickListener { view->
            mAuth.signOut()
            startActivity(Intent(this, LoginActivity::class.java))
            Toast.makeText(this, "logout succes :D", Toast.LENGTH_LONG).show()
        })
        mDatabase = FirebaseDatabase.getInstance().getReference("Names")

        mDatabase.child(uid).child("Name").addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {      }
            override fun onDataChange(snapshot: DataSnapshot) {
                name.text = snapshot.value.toString()
            }
        })
    }
}
