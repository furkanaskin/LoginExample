package com.faskn.loginexample.fragments


import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import com.faskn.loginexample.R
import com.faskn.loginexample.base.BaseFragment
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.mail_login_container.*
import kotlinx.android.synthetic.main.password_login_container.*


class LoginFragment : BaseFragment() {

    private val firebaseAuth by lazy { FirebaseAuth.getInstance() }
    private val userEmail by lazy { edt_user_email.text.toString() }
    private val userPassword by lazy { edt_user_password.text.toString() }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btn_login.setOnClickListener { _ ->

            if (userEmail.isNullOrEmpty() || userPassword.isEmpty()) {
                Toast.makeText(this.context, "Mail address or password cannot be empty.", Toast.LENGTH_SHORT).show()
            } else {


                // Firebase auth Sign in function

                firebaseAuth.signInWithEmailAndPassword(userEmail, userPassword)
                        .addOnCompleteListener {

                            if (it.isSuccessful) {

                                navigate(R.id.action_loginFragment_to_feedFragment)
                                Toast.makeText(this.context, "Welcome :)", Toast.LENGTH_SHORT).show()
                            } else {
                                val builder = AlertDialog.Builder(this.context)
                                builder.setTitle("Oops.. Something went wrong.")
                                builder.setMessage("Check your mail or password, if you're not registered yet please register below.")

                                builder.setPositiveButton("OK") { _, _ -> }
                                builder.setNeutralButton("Cancel") { _, _ ->
                                    Toast.makeText(this.context, "You cancelled the dialog.", Toast.LENGTH_SHORT).show()
                                }
                                val dialog: AlertDialog = builder.create()
                                dialog.show()
                            }
                        }
            }
        }

        // Switch to register page

        btn_registernow.setOnClickListener { view ->
            navigate(R.id.action_loginFragment_to_signUpFragment)
        }


        // Switch to reset password page

        txt_forgot.setOnClickListener { view ->
            navigate(R.id.action_loginFragment_to_resetpasswordFragment)
        }
    }

    private fun navigate(action: Int) {

        view?.let { _view ->

            Navigation.findNavController(_view).navigate(action)
        }
    }
}
