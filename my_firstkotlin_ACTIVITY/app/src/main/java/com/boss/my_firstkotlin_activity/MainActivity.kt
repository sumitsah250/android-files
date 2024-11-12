package com.boss.my_firstkotlin_activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val displaytext : TextView =  findViewById(R.id.txtdisplay)
        val edtname : EditText = findViewById(R.id.editname)
        val edtpassword : EditText = findViewById(R.id.editpassword)
        val btnsave : Button = findViewById(R.id.btnsave)
        val btnnext : Button = findViewById(R.id.btnnext)

        btnsave.setOnClickListener {
            displaytext.visibility = View.VISIBLE
            val name = edtname.text.toString()
            val password = edtpassword.text.toString()
            displaytext.text = name + password;
        }
        btnnext.setOnClickListener{
            val intent = Intent(this,MainActivity2::class.java)
            val bundle  = Bundle()
            bundle.putString("Name","Sumit")
            intent.putExtras(bundle)
            startActivity(intent)
        }


    }
}