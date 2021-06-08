package com.baserasumit.newsapp

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.browser.customtabs.CustomTabsIntent
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import kotlinx.android.synthetic.main.activity_main.*
import java.util.ArrayList

class MainActivity : AppCompatActivity(), onClicked {
    private lateinit var mAdapter: NewsAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerView.layoutManager = LinearLayoutManager(this)
         fetchData()
        mAdapter  = NewsAdapter(this)
        recyclerView.adapter = mAdapter
    }
    private fun fetchData() {

// ...

// Instantiate the RequestQueue.

        val url = "https://newsapi.org/v2/top-headlines?country=in&apiKey=3ba1016919e9461cb961f6ecfa53efe7"
// Request a string response from the provided URL.
        val jsonObjectRequest = object : JsonObjectRequest(
            Request.Method.GET, url,null,
           Response.Listener{
           val newsJsonArray = it.getJSONArray("articles")
               val newsArray = ArrayList<news>()
               for (i in 0 until newsJsonArray.length()){
                   val newsJsonObject = newsJsonArray.getJSONObject(i)
                   val myNews = news(
                       newsJsonObject.getString("title"),
                       newsJsonObject.getString("author"),
                       newsJsonObject.getString("url"),
                       newsJsonObject.getString("urlToImage")
                   )
                   newsArray.add(myNews)
               }
               mAdapter.updateNews(newsArray)
            },
            Response.ErrorListener {

            })
       {
            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers["User-Agent"] = "Mozilla/5.0"
                return headers
           }
        }

// Add the request to the RequestQueue.
         MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)
        }

    override fun clickHandel(item:news) {

        val builder=  CustomTabsIntent.Builder();
       val  customTabsIntent = builder.build()
        customTabsIntent.launchUrl(this, Uri.parse(item.url))
    }
}