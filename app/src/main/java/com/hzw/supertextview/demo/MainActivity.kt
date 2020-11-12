package com.hzw.supertextview.demo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private var listAdapter: ListAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        text.setOnClickListener {
            Toast.makeText(this, text.text.toString(), Toast.LENGTH_SHORT).show()
            setNewData()
        }

        val headerView = View.inflate(this, R.layout.list_header, null)
        val textView=  headerView.findViewById<TextView>(R.id.text);
        textView.setOnClickListener{
            Toast.makeText(this, textView.text.toString(), Toast.LENGTH_SHORT).show()
        }

        listAdapter = ListAdapter(mutableListOf("12", "22"))
        listAdapter!!.addHeaderView(headerView)
        list.adapter = listAdapter

//        setNewData()


    }

    private fun setNewData() {
        list.postDelayed(Runnable {
            val data = mutableListOf<String>()
            for (i in 0..20) {
                data.add("${i}")
            }
            listAdapter!!.setNewInstance(data)
        }, 2000)
    }
}
