package com.faskn.loginexample.fragments


import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import com.faskn.loginexample.R
import com.faskn.loginexample.activities.MainActivity
import com.faskn.loginexample.base.BaseFragment
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.android.synthetic.main.fragment_sign_up.*
import kotlinx.android.synthetic.main.mail_login_container.*
import kotlinx.android.synthetic.main.password_login_container.*


class SignupFragment : BaseFragment(), GoogleApiClient.OnConnectionFailedListener {


    private val firebaseAuth = FirebaseAuth.getInstance()
    private val userEmail by lazy { edt_user_email.text.toString().trim() }
    private val userPassword by lazy { edt_user_password.text.toString() }
    private var mGoogleApiClient: GoogleApiClient? = null
    private val RC_SIGN_IN = 9001


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_up, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("597612786239-lvhb9b8fq1ffjih8228gm4flf5hj54j2.apps.googleusercontent.com")
                .requestEmail()
                .build()

        mGoogleApiClient = GoogleApiClient.Builder(activity as MainActivity)
                .enableAutoManage(this.activity!! /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build()


        // Create user with firebase auth

        btn_signup.setOnClickListener { view ->
            firebaseAuth.createUserWithEmailAndPassword(userEmail, userPassword)
                    .addOnCompleteListener {

                        if (it.isSuccessful) {
                            Toast.makeText(this.context, "Account created successfully.", Toast.LENGTH_SHORT).show()
                            navigate(R.id.action_signupFragment_to_feedFragment)
                        } else {

                            val builder = AlertDialog.Builder(this.context)
                            builder.setTitle("Exception")
                            builder.setMessage("Check your mail or password.")

                            builder.setPositiveButton("OK") { _, _ -> }
                            builder.setNeutralButton("Cancel") { _, _ ->
                                Toast.makeText(this.context, "You cancelled the dialog.", Toast.LENGTH_SHORT).show()
                            }
                            val dialog: AlertDialog = builder.create()
                            dialog.show()
                        }
                    }
        }

        google_button.setOnClickListener { view ->
            val signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient)
            startActivityForResult(signInIntent, RC_SIGN_IN)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val result = Auth.GoogleSignInApi.getSignInResultFromIntent(data)
            val account = result.signInAccount
            firebaseAuthWithGoogle(account!!)
        } else {
            Log.v("qqq", "Wrong RC_SIGN_IN")
        }
    }


    // Create user with Google Sign in

    private fun firebaseAuthWithGoogle(acct: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(acct.idToken, null)
        firebaseAuth!!.signInWithCredential(credential).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                // Sign in success
                Log.e("qqq", "signInWithCredential: Success!")
                navigate(R.id.action_signupFragment_to_feedFragment)

            } else {
                // Sign in fails
                Log.w("qqq", "signInWithCredential: Failed!", task.exception)
                Toast.makeText(this.context, "Authentication failed!", Toast.LENGTH_SHORT).show()
            }
        }
    }


    override fun onConnectionFailed(p0: ConnectionResult) {
        Toast.makeText(this.context, "Connection failed,please try again.", Toast.LENGTH_SHORT).show()
    }


    private fun navigate(action: Int) {

        view?.let { _view ->

            Navigation.findNavController(_view).navigate(action)
        }
    }
}
