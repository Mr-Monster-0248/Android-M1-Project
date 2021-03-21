package fr.thibaultlepez.chill.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.BaseAdapter
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import fr.thibaultlepez.chill.R
import fr.thibaultlepez.chill.models.FireSession

class SessionListAdapter(
    private val context: Context,
    private val list: ArrayList<FireSession>
) : RecyclerView.Adapter<SessionListAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.card_session_title)
        val description: TextView = view.findViewById(R.id.card_session_description)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.layout_session_card, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.title.text = list[position].name
        holder.description.text = getOwnerName(list[position])

        holder.itemView.setOnClickListener {
            val intent = Intent()
        }
    }

    override fun getItemCount() = list.size


    private fun getOwnerName(elem: FireSession): String {
        val owner = elem.users.find { it.id == elem.ownerId }
        return owner?.username ?: ""
    }
}