package br.com.vpn.quizdeck.presentation.ui.topic

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.vpn.quizdeck.databinding.DeckListItemBinding
import br.com.vpn.quizdeck.databinding.TopicListItemBinding
import br.com.vpn.quizdeck.domain.model.Deck
import br.com.vpn.quizdeck.domain.model.Topic

class DeckAdapter(
    val dataSet: MutableList<Deck>,
    private val onItemClick: (Deck) -> Unit,
    private val onOptionsMenuClick: (Int) -> Unit
) : RecyclerView.Adapter<DeckAdapter.ViewHolder>() {

    inner class ViewHolder(
        private val binding: DeckListItemBinding,
        private val onClick: (Int) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(deck: Deck) {
            binding.tvTitle.text = deck.title

            binding.flContainer.setOnClickListener {
                onClick(adapterPosition)
            }

            binding.btnOptions.setOnClickListener {
                onOptionsMenuClick(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            DeckListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding) {
            onItemClick(dataSet[it])
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(dataSet[position])
    }

    override fun getItemCount() = dataSet.size

    fun addAll(newDataSet: List<Deck>) {
        dataSet.clear()
        dataSet.addAll(newDataSet)
        notifyDataSetChanged()
    }

    fun clear() {
        dataSet.clear()
        notifyDataSetChanged()
    }
}
