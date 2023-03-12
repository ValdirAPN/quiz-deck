package br.com.vpn.quizdeck.presentation.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.core.content.ContextCompat
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.vpn.quizdeck.R
import br.com.vpn.quizdeck.databinding.FragmentHomeBinding
import br.com.vpn.quizdeck.domain.model.Topic
import br.com.vpn.quizdeck.presentation.ui.common.DividerItemDecoration
import br.com.vpn.quizdeck.presentation.ui.common.EndOffsetItemDecoration
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HomeViewModel by viewModels()

    private lateinit var topicsAdapter: TopicAdapter

    private var topicsFormModalBottomSheet: TopicsFormModalBottomSheet? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        registerForContextMenu(binding.rvTopics)

        setupRecyclerView()

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { uiState ->
                    uiState.topics.let {
                        if (it.isEmpty()) {
                            binding.llNothingFound.visibility = View.VISIBLE
                            topicsAdapter.clear()
                        } else {
                            binding.llNothingFound.visibility = View.GONE
                            topicsAdapter.addAll(uiState.topics)
                        }
                    }
                }
            }
        }

        binding.fabNewTopic.setOnClickListener {
            showTopicsFormModalBottomSheet()
        }
    }

    private fun setupRecyclerView() {
        ContextCompat.getDrawable(requireContext(), R.drawable.recyclerview_divider)?.let {
            val dividerItemDecoration = DividerItemDecoration(it)
            binding.rvTopics.addItemDecoration(dividerItemDecoration)
        }

        val endOffsetItemDecoration = EndOffsetItemDecoration(EndOffsetItemDecoration.ABOVE_FAB_OFFSET)
        binding.rvTopics.addItemDecoration(endOffsetItemDecoration)

        topicsAdapter = TopicAdapter(
            mutableListOf(),
            { topic -> onTopicClick(topic) },
            { position -> onOptionsMenuClick(position) }
        )

        binding.rvTopics.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = topicsAdapter
        }
    }

    private fun onOptionsMenuClick(position: Int) {
        val popupMenu = PopupMenu(requireContext(), binding.rvTopics[position].findViewById(R.id.btnOptions))
        popupMenu.inflate(R.menu.item_option)
        popupMenu.setOnMenuItemClickListener {
            val topic = topicsAdapter.dataSet[position]
            when(it.itemId) {
                R.id.edit -> {
                    showTopicsFormModalBottomSheet(topic)
                    true
                }
                R.id.delete -> {
                    viewModel.deleteTopic(topic)
                    true
                }
                else -> { true }
            }
        }
        popupMenu.show()
    }

    private fun showTopicsFormModalBottomSheet(topic: Topic? = null) {
        activity?.supportFragmentManager?.let {
            topicsFormModalBottomSheet?.dismiss()
            topicsFormModalBottomSheet = TopicsFormModalBottomSheet.newInstance(
                topic = topic,
                listener = object : TopicsFormModalBottomSheet.TopicsFormListener {
                    override fun onConfirm(topicData: Topic) {
                        if (topic == null) {
                            viewModel.addTopic(topicData)
                        } else {
                            viewModel.updateTopic(topicData)
                        }
                    }
                }
            )
            topicsFormModalBottomSheet?.show(it, TopicsFormModalBottomSheet.TAG)
        }
    }

    private fun onTopicClick(topic: Topic) {
        val action = HomeFragmentDirections.actionOpenTopic(topic)
        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val TAG = "HomeFragment"
    }
}