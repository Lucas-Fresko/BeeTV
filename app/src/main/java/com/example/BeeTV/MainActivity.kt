package com.example.BeeTV

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //Toast.makeText(this, "Hello!", Toast.LENGTH_SHORT).show()

        val loginButton = findViewById<Button>(R.id.button_login)

        loginButton.setOnClickListener {
            val loginIntent = Intent(this, LoggingInActivity::class.java)
            startActivity(loginIntent)
        }

        val registerButton = findViewById<Button>(R.id.button_register)

        registerButton.setOnClickListener {
            val registerIntent = Intent(this, RegisterActivity::class.java)
            startActivity(registerIntent)
        }
    }
}
