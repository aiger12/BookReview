package com.example.bookreview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.SearchView
import com.example.bookreview.databinding.ActivityFirstIconBinding

class FirstIcon : AppCompatActivity() {

    lateinit var binding: ActivityFirstIconBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityFirstIconBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //create a list of elements
        val user= arrayOf("Catcher in the Rye","Someone Like You","Kaguya-sama: Love is war")


        //put our user in adapter
        val adapterUser:ArrayAdapter<String> = ArrayAdapter(
            this,android.R.layout.simple_list_item_1, user
        )


        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                binding.searchView.clearFocus()
                if(user.contains(query)){
                    adapterUser.filter.filter(query)
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapterUser.filter.filter(newText)
                return false
            }
        })
    }
}