package com.example.BeeTV

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import database.User
import database.UserRepository

class LoggingInActivity : AppCompatActivity() {

    private val userRepository: UserRepository = UserRepository()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_logging_in)

        Log.d("log", "Login activity awake!")


        userRepository.initialize(applicationContext)

        val loginButton = findViewById<Button>(R.id.buttonLogin)
        val usernameField = findViewById<EditText>(R.id.inputViewUsername)
        val passwordField = findViewById<EditText>(R.id.inputViewPassword)

        val recoverPasswordButton = findViewById<Button>(R.id.button_forgot_password)

        recoverPasswordButton.setOnClickListener{

            var Dialog = PasswordRecoveryFragment()

            Dialog.show(supportFragmentManager, "PasswordRecoveryFragment")

        }

        loginButton.setOnClickListener {

            val vUsername = usernameField.text.toString()
            val vPassword = passwordField.text.toString()

            //check for freak case where user with no username or password was registered
            if(vUsername==""){
                Log.d("log", "Failed username check.")
                usernameField.error = getString(R.string.required_field)
                return@setOnClickListener
            }
            if(vPassword==""){
                Log.d("log", "Failed password check.")
                passwordField.error = getString(R.string.required_field)
                return@setOnClickListener
            }

            val loggingUser: User = userRepository.findUser(usernameField.text.toString(), passwordField.text.toString())

            if(loggingUser!=null){
                Log.d("log", "Logging in! User: $loggingUser")
                val logInIntent = Intent(this, MainUserScreen::class.java)
                logInIntent.putExtra("uid", loggingUser.uid)
                logInIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(logInIntent)
            } else {
                //todo "password does not match" check
                usernameField.error=getString(R.string.user_does_not_exist)
                passwordField.getText().clear()
                return@setOnClickListener
            }
        }

        val goBackButton = findViewById<Button>(R.id.buttonGoBack)
        goBackButton.setOnClickListener{
            val goBackIntent = Intent(this, MainActivity::class.java)
            goBackIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(goBackIntent)
        }
        //todo throw separate error if username exists but password does not match

    }
}