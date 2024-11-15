package com.boss.my_firstkotlin_activity

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main2)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val recyclerview : RecyclerView=findViewById(R.id.recyclerView)
//        val text1 : TextView = findViewById(R.id.textView)
//        val bundle =intent.extras
//
//        if (bundle != null) {
//            text1.text = bundle.getString("Name")
//        }

        val arrcontacts = ArrayList<ContactModel>()
        var i = 0;
        arrcontacts.add(ContactModel(i,9809641235,"Sumit"))
        i++
        arrcontacts.add(ContactModel(i,9809641235,"Sumit"))
        i++
        arrcontacts.add(ContactModel(i,9809641235,"Sumit"))
        i++
        arrcontacts.add(ContactModel(i,9809641235,"Sumit"))
        i++
        arrcontacts.add(ContactModel(i,9809641235,"Sumit"))
        i++
        arrcontacts.add(ContactModel(i,9809641235,"Sumit"))
        i++
        arrcontacts.add(ContactModel(i,9809641235,"Sumit"))
        i++
        arrcontacts.add(ContactModel(i,9809641235,"Sumit"))
        i++
        arrcontacts.add(ContactModel(i,9809641235,"Sumit"))
        i++
        arrcontacts.add(ContactModel(i,9809641235,"Sumit"))
        i++
        arrcontacts.add(ContactModel(i,9809641235,"Sumit"))
        i++
        arrcontacts.add(ContactModel(i,9809641235,"Sumit"))
        i++
        arrcontacts.add(ContactModel(i,9809641235,"Sumit"))
        i++
        arrcontacts.add(ContactModel(i,9809641235,"Sumit"))
        i++
        arrcontacts.add(ContactModel(i,9809641235,"Sumit"))
        i++
        arrcontacts.add(ContactModel(i,9809641235,"Sumit"))
        i++
        arrcontacts.add(ContactModel(i,9809641235,"Sumit"))
        i++
        arrcontacts.add(ContactModel(i,9809641235,"Sumit"))
        i++
        arrcontacts.add(ContactModel(i,9809641235,"Sumit"))
        i++
        arrcontacts.add(ContactModel(i,9809641235,"Sumit"))
        i++
        arrcontacts.add(ContactModel(i,9809641235,"Sumit"))

        recyclerview.layoutManager = GridLayoutManager(this,3)




      val recycleradapter = RecyclerContactAdapter(this,arrcontacts)
        recyclerview.adapter = recycleradapter






    }
}