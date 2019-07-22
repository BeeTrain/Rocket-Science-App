package ru.chernakov.rocketscienceapp.presentation.ui.chat.adapter.holder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.list_item_message.view.*
import ru.chernakov.rocketscienceapp.R
import ru.chernakov.rocketscienceapp.data.model.chat.Message


class ChatViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(message: Message) {
        itemView.apply {
            if (message.text.isNotEmpty()) {
                tvMessage.text = message.text
                tvMessage.visibility = View.VISIBLE
                ivMessage.visibility = View.GONE
            } else if (message.imageUrl.isNotEmpty()) {
                val imageUrl = message.imageUrl
                if (imageUrl.startsWith("gs://")) {
                    val storageReference = FirebaseStorage.getInstance()
                        .getReferenceFromUrl(message.imageUrl)
                    storageReference.downloadUrl.addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            val downloadUrl = task.result.toString()
                            Glide.with(ivMessage)
                                .load(downloadUrl)
                                .into(ivMessage)
                        }
                    }
                } else {
                    Glide.with(ivMessage)
                        .load(message.imageUrl)
                        .into(ivMessage)
                }
                ivMessage.visibility = View.VISIBLE
                tvMessage.visibility = View.GONE
            }
            tvAuthor.text = message.name
            Glide.with(ivAuthor)
                .load(context.getDrawable(R.drawable.ic_account))
                .into(ivAuthor)
            if (message.photoUrl.isNotEmpty()) {
                Glide.with(ivAuthor)
                    .load(message.photoUrl)
                    .into(ivAuthor)
            }
        }
    }
}