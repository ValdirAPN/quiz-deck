package br.com.vpn.quizdeck.presentation.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.vpn.quizdeck.databinding.TopicListItemBinding
import br.com.vpn.quizdeck.domain.model.Topic

class TopicAdapter(
    private val dataSet: MutableList<Topic>
) : RecyclerView.Adapter<TopicAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: TopicListItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(topic: Topic) {
            binding.tvTitle.text = topic.title
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = TopicListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(dataSet[position])
    }

    override fun getItemCount() = dataSet.size

    fun addAll(newDataSet: List<Topic>) {
        dataSet.clear()
        dataSet.addAll(newDataSet)
        notifyItemRangeChanged(0, newDataSet.size)
    }
}
