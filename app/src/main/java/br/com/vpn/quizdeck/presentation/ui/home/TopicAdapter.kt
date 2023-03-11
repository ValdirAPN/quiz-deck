package br.com.vpn.quizdeck.presentation.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.vpn.quizdeck.databinding.TopicListItemBinding
import br.com.vpn.quizdeck.domain.model.Topic

class TopicAdapter(
    val dataSet: MutableList<Topic>,
    private val onItemClick: (Topic) -> Unit,
    private val onOptionsMenuClick: (Int) -> Unit
) : RecyclerView.Adapter<TopicAdapter.ViewHolder>() {

    inner class ViewHolder(
        private val binding: TopicListItemBinding,
        private val onClick: (Int) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(topic: Topic) {
            binding.tvTitle.text = topic.title

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
            TopicListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding) {
            onItemClick(dataSet[it])
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(dataSet[position])
    }

    override fun getItemCount() = dataSet.size

    fun addAll(newDataSet: List<Topic>) {
        dataSet.clear()
        dataSet.addAll(newDataSet)
        notifyDataSetChanged()
    }

    fun clear() {
        dataSet.clear()
        notifyDataSetChanged()
    }
}
