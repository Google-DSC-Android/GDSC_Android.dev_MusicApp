package com.aditys.gdsc_adb_bustingieber

import android.app.Activity
import android.media.MediaPlayer
import android.provider.ContactsContract
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso



class MyAdapter(val context:Activity, val dataList: List<ContactsContract.Contacts.Data>):
    RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView= LayoutInflater.from(context).inflate(R.layout.each_item, parent, false);return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        //populate the data into the view
        val currentData= dataList[position]

        val mediaPlayer= MediaPlayer.create(context, currentData.preview.toUri())
        holder.title.text= currentData.title


        Picasso.get().load(currentData.album.cover).into(holder.image);

        holder.play.setOnClickListener{
            mediaPlayer.start()
        }
        holder.pause.setOnClickListener{
            mediaPlayer.stop()
        }


    }

    class MyViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        val image: ImageView
        val title: TextView
        val play: ImageButton
        val pause: ImageButton

        init{
            image= itemView.findViewById(R.id.musicImage)
            title= itemView.findViewById(R.id.musicTitle)
            play= itemView.findViewById(R.id.btnPlay)
            pause= itemView.findViewById(R.id.btnPause)
        }
    }


}




