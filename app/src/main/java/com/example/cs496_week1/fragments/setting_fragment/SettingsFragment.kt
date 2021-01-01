package com.example.cs496_week1.fragments.setting_fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cs496_week1.R
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException

class SettingsFragment : Fragment() {

    private var dataList: ArrayList<UrlInfo> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        try {
            val inputStream = resources.assets.open("url.json")
            val size = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            inputStream.close()
            var strJson = String(buffer, Charsets.UTF_8)

            val jsonObject = JSONObject(strJson)
            val jsonObject2 = JSONObject(loadJSONFileFromAsset())

            val urlArray = jsonObject.getJSONArray("urlInfo")
            for(i in 0 until urlArray.length()) {
                val baseInfo = urlArray.getJSONObject(i)
                val tempData = UrlInfo(
                    baseInfo.getString("title"),
                    baseInfo.getString("content"),
                    baseInfo.getString("url")
                )
                dataList.add(tempData)
            }
        } catch(e: JSONException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }

        val recycler_view: RecyclerView = view.findViewById(R.id.recycler_view)
        val add_button: ImageButton = view.findViewById(R.id.add_button)
        recycler_view.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = RecyclerAdapterUrl(view.context, dataList)
        }
        addButtonListener(add_button)
    }

    private fun addButtonListener(button: ImageButton) {
        button.setOnClickListener {
            val intent: Intent = Intent(activity, AddUrlActivity::class.java)
            startActivityForResult(intent, 1)
        }
    }

    private fun loadJSONFileFromAsset(): String {
        return try {
            val inputStream = resources.assets.open("url.json")
            val size = inputStream.available()
            val buffer = ByteArray(size)

            inputStream.read(buffer)
            inputStream.close()

            String(buffer, Charsets.UTF_8)
        } catch (ex: IOException){
            ex.printStackTrace()
            return ""
        }
    }
}