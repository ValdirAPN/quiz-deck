package br.com.vpn.quizdeck.presentation.ui.deck

import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.recyclerview.widget.RecyclerView
import br.com.vpn.quizdeck.R
import br.com.vpn.quizdeck.databinding.CardListItemBinding
import br.com.vpn.quizdeck.domain.model.Card

class CardAdapter(
    val dataSet: MutableList<Card>,
    private val onOptionsMenuClick: (Int) -> Unit
) : RecyclerView.Adapter<CardAdapter.ViewHolder>() {

    inner class ViewHolder(
        private val binding: CardListItemBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(card: Card) {
            binding.tvTitle.text = card.statement

            binding.btnOptions.setOnClickListener {
                onOptionsMenuClick(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            CardListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(dataSet[position])
    }

    override fun getItemCount() = dataSet.size

    fun addAll(newDataSet: List<Card>) {
        dataSet.clear()
        dataSet.addAll(newDataSet)
        notifyDataSetChanged()
    }

    fun clear() {
        dataSet.clear()
        notifyDataSetChanged()
    }
}