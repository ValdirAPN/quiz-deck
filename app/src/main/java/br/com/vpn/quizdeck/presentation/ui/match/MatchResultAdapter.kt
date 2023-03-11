package br.com.vpn.quizdeck.presentation.ui.match

import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import br.com.vpn.quizdeck.R
import br.com.vpn.quizdeck.databinding.CardListItemBinding
import br.com.vpn.quizdeck.databinding.MatchResultListItemBinding
import br.com.vpn.quizdeck.domain.model.Card

class MatchResultAdapter(
    private val dataSet: MutableList<Card>,
) : RecyclerView.Adapter<MatchResultAdapter.ViewHolder>() {

    inner class ViewHolder(
        private val binding: MatchResultListItemBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(card: Card) {
            binding.tvTitle.text = card.statement

            if (card.isCorrect.not()) {
                val backgroundColor = ContextCompat.getColor(binding.root.context, R.color.error_container)
                val statementColor = ContextCompat.getColor(binding.root.context, R.color.error)
                binding.root.backgroundTintList = ColorStateList.valueOf(backgroundColor)
                binding.tvTitle.setTextColor(statementColor)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            MatchResultListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(dataSet[position])
    }

    override fun getItemCount() = dataSet.size

    fun addAll(newDataSet: List<Card>) {
        dataSet.clear()
        dataSet.addAll(newDataSet)
        notifyItemRangeChanged(0, newDataSet.size)
    }
}