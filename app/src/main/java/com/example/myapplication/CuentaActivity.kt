package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.ActionBar
import com.example.myapplication.databinding.ActivityCuentaBinding
import com.example.myapplication.databinding.ActivityRegistrarseBinding
import com.google.firebase.auth.FirebaseAuth

class CuentaActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCuentaBinding

    private lateinit var actionBar: ActionBar

    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCuentaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        actionBar = supportActionBar!!
        actionBar.title = "Cuenta"

        firebaseAuth = FirebaseAuth.getInstance()
        checkUser()

        binding.CerrarBtn.setOnClickListener {
            firebaseAuth.signOut()
            checkUser()
        }
    }

    private fun checkUser() {
        val firebaseUser = firebaseAuth.currentUser
        if (firebaseUser != null){

            val email = firebaseUser.email

            binding.emailTv.text = email

        }
        else{
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }


}
