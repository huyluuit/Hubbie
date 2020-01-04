package com.example.hubbie.utilis

import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider

object Callbacks : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

    private val auth = FirebaseAuth.getInstance()

    override fun onCodeSent(
        verificationId: String,
        forceResendingToken: PhoneAuthProvider.ForceResendingToken
    ) {
        // Save the verification id somewhere
        // ...

        // The corresponding whitelisted code above should be used to complete sign-in.
        //this@MainActivity.enableUserManuallyInputCode()
    }

    override fun onVerificationCompleted(phoneAuthCredential: PhoneAuthCredential) {
        // Sign in with the credential
        // ...
        auth.signInWithCredential(phoneAuthCredential)
    }

    override fun onVerificationFailed(e: FirebaseException) {
        // Notify wrong verify code
    }
}
