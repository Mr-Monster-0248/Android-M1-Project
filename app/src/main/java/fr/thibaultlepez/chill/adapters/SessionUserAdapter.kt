package fr.thibaultlepez.chill.adapters

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.BaseAdapter
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import fr.thibaultlepez.chill.R
import fr.thibaultlepez.chill.activities.SessionOverviewActivity
import fr.thibaultlepez.chill.models.FireSession
import fr.thibaultlepez.chill.models.FireSessionUser

class SessionUserAdapter(
    private val context: Context,
    private val list: ArrayList<FireSessionUser>
) : RecyclerView.Adapter<SessionUserAdapter.ViewHolder>() {


    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.card_session_title)
        val description: TextView = view.findViewById(R.id.card_session_description)
        val card: CardView = view.findViewById(R.id.card_session_card)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.layout_session_card, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.title.text = list[position].username
        holder.description.text = if (list[position].done) { "Done." } else { "Voting..." }
    }

    override fun getItemCount() = list.size
}