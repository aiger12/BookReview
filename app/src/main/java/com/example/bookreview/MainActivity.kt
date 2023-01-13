package com.example.bookreview

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.example.bookreview.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth

class   MainActivity : AppCompatActivity() {
    private val emailLiveData= MutableLiveData<String>()
    private val passwordLiveData= MutableLiveData<String>()
    private val isValidLiveData= MediatorLiveData<Boolean>().apply {
        this.value=false

        addSource(emailLiveData){ email->
            val password=passwordLiveData.value
            this.value=valueDateform(email,password)
        }

        addSource(passwordLiveData){ password->
            val email=emailLiveData.value
            this.value=valueDateform(email,password)
        }
    }

    private lateinit var binding:ActivityMainBinding
    private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Auth
        auth= FirebaseAuth.getInstance()

        //Email Validation
        val emailLayout=binding.etEmail
        val passwordLayout=binding.etPassword
        val btnRegister=binding.btnLogin



        emailLayout.doOnTextChanged { text, _, _, _ ->
            emailLiveData.value=text?.toString()
        }

        passwordLayout.doOnTextChanged { text, _, _, _ ->
            passwordLiveData.value=text.toString()
        }

        isValidLiveData.observe(this){ isValid->
            btnRegister.isEnabled=isValid
        }

        //Click
        binding.btnLogin.setOnClickListener {
            val email=binding.etEmail.text.toString().trim()
            val password=binding.etPassword.text.toString().trim()
            loginUser(email,password)
        }
        binding.textView2.setOnClickListener {
            startActivity(Intent(this,RegisterActivity::class.java))
        }

    }
    private fun valueDateform(email:String?, password:String?):Boolean{
        val isValidEmail = email != null && email.isNotBlank() && email.contains("@")
        val isValidPassword = password != null && password.isNotBlank() && password.length >=8

        return isValidEmail && isValidPassword
    }

    private fun loginUser(email: String,password: String){
        auth.signInWithEmailAndPassword(email,password)
            .addOnCompleteListener(this){ login ->
                if(login.isSuccessful){
                    Intent(this,ChooseActivity::class.java).also{
                        it.flags=Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(it)
                        Toast.makeText(this,"Login Successful!",Toast.LENGTH_SHORT).show()
                    }
                } else{
                        Toast.makeText(this,"You did not have an account!\n Please, create an account",Toast.LENGTH_SHORT).show()
                    }

            }
    }
}