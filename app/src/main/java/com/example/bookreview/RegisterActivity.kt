package com.example.bookreview


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.example.bookreview.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth


class RegisterActivity : AppCompatActivity() {

//    private val emailLiveData=MutableLiveData<String>()
//    private val passwordLiveData=MutableLiveData<String>()
//    private val isValidLiveData=MediatorLiveData<Boolean>().apply {
//        this.value=false
//
//        addSource(emailLiveData){ email->
//            val password=passwordLiveData.value
//            this.value=valueDateform(email,password)
//        }
//
//        addSource(passwordLiveData){ password->
//            val email=emailLiveData.value
//            this.value=valueDateform(email,password)
//        }
//    }

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var auth:FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Auth
        auth= FirebaseAuth.getInstance()

        //Email Validation
//        val emailLayout=binding.etEmail
//        val passwordLayout=binding.etPassword
//        val btnRegister=binding.btnRegister
//
//
//
//        emailLayout.doOnTextChanged { text, _, _, _ ->
//            emailLiveData.value=text.toString()
//        }
//
//        passwordLayout.doOnTextChanged { text, _, _, _ ->
//            passwordLiveData.value=text.toString()
//        }
//
//        isValidLiveData.observe(this){ isValid->
//            btnRegister.isEnabled=isValid
//        }

        //Click
        binding.btnRegister.setOnClickListener {
            val email=binding.etEmail.text.toString().trim()
            val password=binding.etPassword.text.toString().trim()
            registerUser(email,password)
        }
        binding.textView4.setOnClickListener {
            startActivity(Intent(this,MainActivity::class.java))
        }
    }
//    private fun valueDateform(email:String?, password:String?):Boolean{
//        val isValidEmail = email != null && email.isNotBlank() && email.contains("@")
//        val isValidPassword = password != null && password.isNotBlank() && password.length >=8
//
//        return isValidEmail && isValidPassword
//    }

    private fun registerUser(email: String,password: String){
        auth.createUserWithEmailAndPassword(email,password)
            .addOnCompleteListener(this){
                if(it.isSuccessful) {
                    startActivity(Intent(this, MainActivity::class.java))
                    Toast.makeText(this, "Successful register!", Toast.LENGTH_LONG).show()
                }else
                    Toast.makeText(this, it.exception?.message, Toast.LENGTH_LONG).show()
                }
            }


//   private fun showTextMinimalAlert(isNotValid:Boolean,text:String){
//        if(text=="Username" || text=="Nickname"){
//            binding.etNickname.error=if(isNotValid)"User name is not valid" else null
//        }
//        else if (text=="Password")
//        binding.etPassword.error=if(isNotValid) "$text Minimum is 8 elements" else null
//    }
//
//    private fun showEmailValidAlert(isNotValid:Boolean){
//        binding.etEmail.error=if(isNotValid) "Email is not valid" else null
//    }
//
//    private fun showPasswordConfirmAlert(isNotValid:Boolean){
//        binding.etPasConfirm.error=if(isNotValid) "Password is not valid" else null
//    }
}