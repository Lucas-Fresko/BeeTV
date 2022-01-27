package com.example.BeeTV

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import database.User
import database.UserRepository

class RegisterActivity : AppCompatActivity() {

    private val userRepository: UserRepository = UserRepository()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        userRepository.initialize(applicationContext)

        val registerButton = findViewById<Button>(R.id.buttonRegister) //replace with kotlin plugin?
        val usernameField = findViewById<EditText>(R.id.inTextUsername)
        val passwordField = findViewById<EditText>(R.id.inTextPassword)
        val emailField = findViewById<EditText>(R.id.inTextEmail)
        val emailConfirmField = findViewById<EditText>(R.id.inTextEmailConfirm)

        val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"

        registerButton.setOnClickListener {
            Log.d("log", "Registration attempt made!")

            val vUsername = usernameField.text.toString().trim()
            val vPassword = passwordField.text.toString().trim()
            val vEmail = emailField.text.toString().trim()
            val vEmailTwo = emailConfirmField.text.toString().trim()
            var canRegister = true

            //check validity of registration fields
            if(vUsername.isEmpty()){
                usernameField.error = getString(R.string.username_required)
                canRegister = false
            }
            if(vUsername.length !in 6..20){
                usernameField.error = getString(R.string.username_length_requirement_error)
                canRegister = false
            }
            if(vPassword.isEmpty()){
                passwordField.error = getString(R.string.password_required)
                canRegister = false
                } else if(vPassword.length<6){
                passwordField.error = getString(R.string.password_length_error)
                canRegister = false
            }
            when {
                vEmail.isEmpty() -> {
                    emailField.error = getString(R.string.email_required)
                    canRegister = false
                }
                vEmail.matches(emailPattern.toRegex()) -> {
                }
                else -> {
                    emailField.error = getString(R.string.email_invalid)
                    canRegister = false
                }
            }
            if(vEmailTwo.isEmpty()){
                emailConfirmField.error = getString(R.string.confrim_email)
                canRegister = false
            }else if(vEmailTwo!=vEmail){
                emailConfirmField.error = getString(R.string.email_confirmation_failed)
                canRegister = false
            }

            //check if username is used
            val usernameUser: User? = userRepository.getUserByUsername(vUsername)
            if(usernameUser!=null){
                usernameField.error = "Username already in use."
                canRegister = false
            }

            //check if email is used
            val emailUser: User? = userRepository.getUserByEmail(vEmail)
            if(emailUser!=null){
                emailField.error = "E-mail already in use."
                canRegister = false
            }

            //quit if above requirements not met (at end so errors can be drawn)
            if(canRegister==false) return@setOnClickListener

            //check if user already exists
            val checkedUser: User? = userRepository.findUser(vUsername, vPassword)
            Log.d("log", "Searching user: $vUsername, $vPassword, result: $checkedUser")
            if (checkedUser==null){
                canRegister = true
            } else {
                canRegister = false
                Log.d("log", "Can't register! User already exists")
            }

            if(canRegister) {
                val user = User(0, vUsername, vPassword, vEmail, null)
                userRepository.addUser(user)

                //reload user to get generated id
                val user2 = userRepository.findUser(vUsername, vPassword)
                val logInIntent = Intent(this, MainUserScreen::class.java)
                logInIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                logInIntent.putExtra("uid", user2.uid)
                startActivity(logInIntent)
            }
        }

        val goBackButton = findViewById<Button>(R.id.buttonGoBack)
        goBackButton.setOnClickListener{
            val goBackIntent = Intent(this, MainActivity::class.java)
            goBackIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(goBackIntent)
        }
    }
}