package com.example.bookreview

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts
import com.example.bookreview.databinding.ActivityChooseBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage

class ChooseActivity : AppCompatActivity() {
    private lateinit var image: ImageView
    private lateinit var btnUpload:Button //browse
    private lateinit var btnSave:Button   //upload

    private var storageRef=Firebase.storage
    private lateinit var uri:Uri

    private lateinit var binding: ActivityChooseBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose)
        binding= ActivityChooseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        storageRef= FirebaseStorage.getInstance()

        image=findViewById(R.id.profile_image)
        btnUpload=findViewById(R.id.btn_upload_img)
        btnSave=findViewById(R.id.btn_save_img)
        val btnContinue=binding.continueChoose

        val galleryImage=registerForActivityResult(
            ActivityResultContracts.GetContent(),
            ActivityResultCallback {
                image.setImageURI(it)
                if (it != null) {
                    uri= it
                }
            })

        btnUpload.setOnClickListener {
            galleryImage.launch("image/*")
        }

        btnSave.setOnClickListener {
            storageRef.getReference("images").child(System.currentTimeMillis().toString())
                .putFile(uri)
                .addOnSuccessListener { task ->
                    task.metadata!!.reference!!.downloadUrl
                        .addOnSuccessListener {
                            val userId=FirebaseAuth.getInstance().currentUser!!.uid
                            val mapImage= mapOf(
                                "uri" to it.toString()
                            )
                            val databaseReference=FirebaseDatabase.getInstance().getReference("user images")
                            databaseReference.child(userId).setValue(mapImage)
                                .addOnSuccessListener {
                                    Toast.makeText(this,"Successful!",Toast.LENGTH_SHORT).show()
                                }
                                .addOnSuccessListener { error ->
                                    Toast.makeText(this,it.toString(),Toast.LENGTH_SHORT).show()
                                }
                        }
                }
        }

        binding.continueChoose.setOnClickListener {
            startActivity(Intent(this,DraftOne::class.java))
        }
    }
}