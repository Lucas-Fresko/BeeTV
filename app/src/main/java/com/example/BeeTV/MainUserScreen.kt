package com.example.BeeTV

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import database.User
import database.UserRepository
import java.text.SimpleDateFormat
import java.util.*

class MainUserScreen : AppCompatActivity() {

    private val userRepository: UserRepository = UserRepository()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_user_screen)

        userRepository.initialize(applicationContext)
        val uid = intent.extras!!.getInt("uid")
        val activeUser: User = userRepository.getUser(uid)

        Log.d("log", "Main Screen, checking user: $activeUser")
        val vUsername = activeUser.username
        val usernameView = findViewById<TextView>(R.id.username_view)
        usernameView.text = vUsername

        val dateNow: Date = Calendar.getInstance().time
        val dateView = findViewById<TextView>(R.id.last_login_date_view)

        if(activeUser.login_date!=null) {
            val date = Date(activeUser.login_date)
            val format = SimpleDateFormat("dd MMM yyyy")
            dateView.setText(getString(R.string.last_log_in) + format.format(date))
        }

        userRepository.update(dateNow.time, activeUser.uid)

        val logoutButton = findViewById<Button>(R.id.buttonLogOut)
        logoutButton.setOnClickListener{
            val logOutIntent = Intent(this, MainActivity::class.java)
            logOutIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(logOutIntent)
        }

    }
}