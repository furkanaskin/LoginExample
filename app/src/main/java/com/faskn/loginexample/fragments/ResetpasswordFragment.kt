package com.faskn.loginexample.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import com.faskn.loginexample.R
import com.faskn.loginexample.base.BaseFragment
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_resetpassword.*


class ResetpasswordFragment : BaseFragment() {

    private val firebaseAuth = FirebaseAuth.getInstance()
    private val userEmail by lazy { edt_reset_mail.text.toString() }
    private val backButton by lazy {}
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_resetpassword, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btn_resetPass.setOnClickListener { view ->
            if (userEmail.isEmpty()) {
                Toast.makeText(this.context, "Mail address cannot be empty.Please enter your mail address.", Toast.LENGTH_SHORT).show()
            } else {

                // Reset password function

                firebaseAuth!!.sendPasswordResetEmail(userEmail)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                Toast.makeText(this.context, "Please check your mail inbox.", Toast.LENGTH_SHORT).show()
                                navigate(R.id.action_resetpasswordFragment_to_loginFragment)
                            } else {
                                Toast.makeText(this.context, "Oops.. Something went wrong,please check your internet connection and try again", Toast.LENGTH_SHORT).show()
                            }
                        }
            }
        }

        // Switch to Login screen
        btn_goback_resetpass.setOnClickListener { view ->
            navigate(R.id.action_resetpasswordFragment_to_loginFragment)
        }
    }


    private fun navigate(action: Int) {

        view?.let { _view ->

            Navigation.findNavController(_view).navigate(action)
        }
    }

}
