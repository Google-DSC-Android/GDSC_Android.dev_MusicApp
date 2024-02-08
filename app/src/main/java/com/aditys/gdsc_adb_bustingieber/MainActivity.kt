package com.aditys.gdsc_adb_bustingieber

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class MainActivity : AppCompatActivity() {

    lateinit var myrecyclerview:RecyclerView
    lateinit var myAdapter: MyAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        myrecyclerview= findViewById(R.id.recyclerView)

        val retrofitBuilder= Retrofit.Builder()
            .baseUrl("https://deezerdevs-deezer.p.rapidapi.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiInterface::class.java)

        val retrofitData= retrofitBuilder.getData("eminem")

        retrofitData.enqueue(object : Callback<MyData?> {

            override fun onResponse(call: Call<MyData?>, response: Response<MyData?>) {
                val dataList=response.body()?.data!!
//                val textView= findViewById<TextView>(R.id.helloText)
//                textView.text= dataList.toString()

                myAdapter= MyAdapter(this@MainActivity, dataList)
                myrecyclerview.adapter=myAdapter
                myrecyclerview.layoutManager=LinearLayoutManager(this@MainActivity)
                Log.d("TAG: onResponse", "onResponse: " +response.body())
            }

            override fun onFailure(call: Call<MyData?>, t: Throwable) {
                Log.d("TAG: onFailure", "onFailure: " + t.message)
            }
        })
    }
}