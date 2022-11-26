package com.example.myapplication

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AlertDialog
import com.example.myapplication.databinding.ActivityRegistrarseBinding
import com.google.firebase.auth.FirebaseAuth

class RegistroActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegistrarseBinding

    private lateinit var actionBar: ActionBar

    private lateinit var progressDialog: ProgressDialog

    private lateinit var firebaseAuth: FirebaseAuth
    private var email = ""
    private var password = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistrarseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        actionBar = supportActionBar!!
        actionBar.title = "Registrese"
        actionBar.setDisplayShowHomeEnabled(true)
        actionBar.setDisplayShowHomeEnabled(true)

        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Espere porfavor")
        progressDialog.setMessage("Creando cuenta")
        progressDialog.setCanceledOnTouchOutside(false)

        firebaseAuth = FirebaseAuth.getInstance()

        binding.RegistrarseBtn.setOnClickListener {

            validateData()
        }
    }

    private fun validateData() {
        email = binding.emailEt.text.toString().trim()
        password = binding.passwordEt.text.toString().trim()

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            binding.emailEt.error = "Email invalido"
        }
        else if (TextUtils.isEmpty(password)){
            binding.passwordEt.error = "Ingrese contraseña"
        }
        else if (password.length <6){
            binding.passwordEt.error = "La contraseña requiere al menos 6 digitos"
        }
        else{

            firebaseRegistrarse()

            }


            }

    private fun firebaseRegistrarse() {

        progressDialog.show()

        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener {

                progressDialog.dismiss()

                val firebaseUser = firebaseAuth.currentUser
                val email = firebaseUser!!.email
                Toast.makeText(this, "Cuenta creada con correo electronico $email",Toast.LENGTH_SHORT).show()

                startActivity(Intent(this, CuentaActivity::class.java))
                finish()
            }
            .addOnFailureListener {e->

        progressDialog.dismiss()
        Toast.makeText(this, "Registro fallido debido a ${e.message}",Toast.LENGTH_SHORT).show()
    }
}



    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}



