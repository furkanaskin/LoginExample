package com.faskn.loginexample.fragments


import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation


import com.faskn.loginexample.R
import com.faskn.loginexample.base.BaseFragment
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_sign_up.*
import kotlinx.android.synthetic.main.mail_login_container.*
import kotlinx.android.synthetic.main.password_login_container.*


class SignupFragment : BaseFragment() {

    private val firebaseAuth by lazy { FirebaseAuth.getInstance() }
    private val userEmail by lazy { edt_user_email.text.toString() }
    private val userPassword by lazy { edt_user_password.text.toString() }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_up, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btn_signup.setOnClickListener { view ->
            firebaseAuth.createUserWithEmailAndPassword(userEmail, userPassword)
                    .addOnCompleteListener {

                        if (it.isSuccessful) {
                            Toast.makeText(this.context,"Account created successfully.",Toast.LENGTH_SHORT).show()
                            navigate(R.id.action_signupFragment_to_feedFragment)
                        }else{

                            val builder = AlertDialog.Builder(this.context)
                            builder.setTitle("Exception")
                            builder.setMessage("Check your mail or password.")

                            builder.setPositiveButton("OK"){_,_ ->}
                            builder.setNeutralButton("Cancel"){_,_ ->
                                Toast.makeText(this.context,"You cancelled the dialog.",Toast.LENGTH_SHORT).show()
                            }
                            val dialog: AlertDialog = builder.create()
                            dialog.show()
                        }
                    }

        }
    }

    private fun navigate(action: Int) {

        view?.let { _view ->

            Navigation.findNavController(_view).navigate(action)
        }
    }
}
