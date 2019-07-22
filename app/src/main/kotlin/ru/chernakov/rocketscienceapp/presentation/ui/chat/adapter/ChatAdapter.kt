package ru.chernakov.rocketscienceapp.presentation.ui.chat.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import ru.chernakov.rocketscienceapp.R
import ru.chernakov.rocketscienceapp.data.model.chat.Message
import ru.chernakov.rocketscienceapp.presentation.ui.chat.adapter.holder.ChatViewHolder

class ChatAdapter(options: FirebaseRecyclerOptions<Message>) :
    FirebaseRecyclerAdapter<Message, ChatViewHolder>(options) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.list_item_message, parent, false)
        return ChatViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ChatViewHolder, position: Int, message: Message) {
        viewHolder.bind(message)
    }
}