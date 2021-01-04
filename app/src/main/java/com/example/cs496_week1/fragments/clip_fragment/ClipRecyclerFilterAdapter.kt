package com.example.cs496_week1.fragments.clip_fragment

import android.app.AlertDialog
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.cs496_week1.R
import io.realm.OrderedRealmCollection
import io.realm.Realm
import io.realm.RealmRecyclerViewAdapter
import java.lang.Exception

class ClipRecyclerFilterAdapter(
    val context: Context,
    data: OrderedRealmCollection<ClipRealmData>?,
    autoUpdate: Boolean
) :
    RealmRecyclerViewAdapter<ClipRealmData, ClipRecyclerFilterAdapter.ViewHolder>(
        data, autoUpdate
    ) {
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemTitle: TextView = itemView.findViewById(R.id.title)
        val itemContent: TextView = itemView.findViewById(R.id.content)
        val itemIndex: TextView = itemView.findViewById(R.id.index)
        val goToUrl: LinearLayout = itemView.findViewById(R.id.url_card)
        val shareButton: ImageButton = itemView.findViewById(R.id.share)
        val editButton: ImageButton = itemView.findViewById(R.id.revice)
        val copyButton: ImageButton = itemView.findViewById(R.id.copy)
        val itemTag: TextView = itemView.findViewById(R.id.tag)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_url_card_view, parent, false)
        return ViewHolder(view)
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val clipData = data?.get(position)
        holder.itemTitle.text = clipData!!.title
        holder.itemContent.text = clipData!!.content
        holder.itemIndex.text = (clipData!!.trick_id + 1).toString()
        holder.itemTag.text = clipData!!.tag

        val logo = context.resources.getDrawable(R.drawable.logo)
        logo.setTint(
            context.resources.obtainTypedArray(R.array.tag_color).getColor(clipData!!.tag_color, 0)
        )
        holder.itemTag.background = logo

        // click listener
        holder.goToUrl.setOnClickListener {
            goToUrl(context, clipData!!.url)
        }
        holder.goToUrl.setOnLongClickListener {
            val index = holder.itemIndex.text.toString().toInt()
            AlertDialog.Builder(context).setTitle("삭제하시겠습니까?")
                .setNegativeButton("아니오") { _, _ -> }
                .setPositiveButton("예") { _, _ -> deleteDB(index - 1) }
                .show()
            true
        }
        holder.shareButton.setOnClickListener {
            val sendIntent = Intent()
            sendIntent.action = Intent.ACTION_SEND
            sendIntent.putExtra(Intent.EXTRA_TEXT, clipData!!.url)
            sendIntent.type = "text/plain"

            val shareIntent = Intent.createChooser(sendIntent, null)
            context.startActivity(shareIntent)
        }
//        holder.editButton.setOnClickListener {
//            Log.d("Status", "Edit button was clicked")
//            val intent = Intent(context, AddUrlActivity::class.java)
//            val editData = arrayListOf(
//                clipData!!.title,
//                clipData!!.content,
//                clipData!!.url,
//                clipData!!.tag,
//                clipData!!.id.toString(),
//                clipData!!.trick_id.toString(),
//                clipData!!.tag_color.toString()
//            )
//            //context.onClickButton(intent, editData)
//        }
        holder.copyButton.setOnClickListener {
            val clipboardManager =
                context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clipData = ClipData.newPlainText("url", clipData!!.url)
            clipboardManager.setPrimaryClip(clipData)
            Toast.makeText(context, "클립보드에 복사되었습니다.", Toast.LENGTH_SHORT).show()
        }
        holder.itemTag.setOnClickListener {
            val intent = Intent(context, ClipFilterActivity::class.java)
            val label = holder.itemTag.text
            intent.putExtra("tag_label", label)
            context.startActivity(intent)
        }
    }

    private fun goToUrl(context: Context, url: String) {
        try {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(url)
            context.startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(context, "URL이 올바르지 않습니다.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun deleteDB(index: Int) {
        try {
            val realm = Realm.getDefaultInstance()
            val data = realm.where(ClipRealmData::class.java).equalTo("trick_id", index).findFirst()
            realm.executeTransaction {
                data?.deleteFromRealm()
            }
            updateDB(index)
        } catch (e: Exception) {
            Log.d("Status", "There are some errors")
        }
    }

    private fun updateDB(index: Int) {
        try {
            val realm = Realm.getDefaultInstance()
            val start = index + 1
            val end = this.itemCount
            for (i in start..end) {
                val data = realm.where(ClipRealmData::class.java).equalTo("trick_id", i).findFirst()
                realm?.executeTransaction {
                    data?.trick_id = i - 1
                }
            }
        } catch (e: Exception) {
            Log.d("Status", "There are some errors")
        }
    }
}