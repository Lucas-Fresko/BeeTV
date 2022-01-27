package com.example.BeeTV

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.DialogFragment

class PasswordRecoveryFragment: DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var rootView: View = inflater.inflate(R.layout.fragment_password_recovery_input, container, false)

        var button = rootView.findViewById<Button>(R.id.buttonConfirm)
        button.setOnClickListener{
            //todo read email to get user
            //todo load password recovery animation (another fragment?)
            //todo log in with user from email
            Log.d("log", "Button pressed. Inflating animation!")
            var Dialog = PasswordRecoveryAnimation()
            //var supportFragmentManager: FragmentManager = getSupportFragmentManager()

            //Dialog.show(supportFragmentManager, "PasswordRecoveryAnimation")
        }

        return rootView
    }

}