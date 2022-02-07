package com.example.BeeTV

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import database.User
import database.UserRepository

class PasswordRecoveryFragment: DialogFragment() {

    private val userRepository: UserRepository = UserRepository()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        userRepository.initialize(requireActivity().application)
        //requireActivity().application is a Kotlin feature that gives necessary application context.
        Log.d("log","OnCreateView started")
        var rootView: View = inflater.inflate(R.layout.fragment_password_recovery_input, container, false)
        val emailField = rootView.findViewById<EditText>(R.id.emailView)
        var button = rootView.findViewById<Button>(R.id.buttonConfirm)
        button.setOnClickListener{
            //todo read email to get user
            //todo load password recovery animation (another fragment?)
            //todo log in with user from email

            // Q: What is this fragment for? The animation? Is a new fragment necessary for that?
            /*Log.d("log", "Button pressed. Inflating animation!")
            var Dialog = PasswordRecoveryAnimation()
            var supportFragmentManager: FragmentManager = getSupportFragmentManager()
            Dialog.show(supportFragmentManager, "PasswordRecoveryAnimation")*/

            Log.d("log","Email button pressed")

            val vEmail = emailField.text.toString()
            if(vEmail==""){
                Log.d("log", "Failed email check.")
                emailField.error = getString(R.string.required_field)
                return@setOnClickListener
            }

            val loggingUser: User = userRepository.getUserByEmail(vEmail)
            Log.d("log", "Logging user= $loggingUser")
            if(loggingUser!=null){
                Log.d("log","Found user for password recovery")
                var inputLayout = rootView.findViewById<LinearLayout>(R.id.inputLayout)
                var animationLayout = rootView.findViewById<LinearLayout>(R.id.animationLayout)
                inputLayout.visibility = if (inputLayout.visibility == View.VISIBLE){
                    View.INVISIBLE
                } else{
                    View.VISIBLE
                }
                animationLayout.visibility = if (animationLayout.visibility == View.VISIBLE){
                    View.INVISIBLE
                } else{
                    View.VISIBLE
                }
                val animationText = rootView.findViewById<TextView>(R.id.animationText)
                Handler(Looper.getMainLooper()).postDelayed(
                    {
                        animationText.text="Sending password recovery e-mail..."


                        Handler(Looper.getMainLooper()).postDelayed(
                            {
                                animationText.text="Recovering your e-mail password..."

                                Handler(Looper.getMainLooper()).postDelayed(
                                    {
                                        animationText.text="What do you mean my password can't be the same as the last one?"

                                        Handler(Looper.getMainLooper()).postDelayed(
                                            {
                                                animationText.text="Trying again..."
                                            },
                                            2000
                                        )

                                    },
                                    2000
                                )

                            },
                            2000
                        )

                    },
                    2000
                )

            } else {
                Log.d("log","No user for password recovery")
                emailField.error=getString(R.string.user_does_not_exist)
            }
        }

        return rootView
    }

}