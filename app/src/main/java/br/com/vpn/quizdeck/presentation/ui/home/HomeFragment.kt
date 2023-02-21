package br.com.vpn.quizdeck.presentation.ui.home

import android.os.Bundle
import android.text.Layout.Directions
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import br.com.vpn.quizdeck.presentation.ui.topic.TopicFragment
import br.com.vpn.quizdeck.presentation.ui.topic.TopicFragmentArgs
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HomeViewModel by viewModels()

    private lateinit var topicsAdapter: TopicAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        topicsAdapter = TopicAdapter(mutableListOf()) { topic ->
            val action = HomeFragmentDirections.actionOpenTopic(topic)
            findNavController().navigate(action)
        }

        binding.rvTopics.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = topicsAdapter
        }

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { uiState ->
                    uiState.topics.let {
                        if (it.isEmpty()) {
                            binding.llNothingFound.visibility = View.VISIBLE
                        } else {
                            binding.llNothingFound.visibility = View.GONE
                            topicsAdapter.addAll(uiState.topics)
                        }
                    }
                }
            }
        }

        binding.fabNewTopic.setOnClickListener {
            activity?.supportFragmentManager?.let {
                val topicsFormModalBottomSheet = TopicsFormModalBottomSheet.newInstance(
                    object : TopicsFormModalBottomSheet.TopicsFormListener {
                        override fun onConfirm(topic: Topic) {
                            viewModel.addTopic(topic)
                        }
                    }
                )
                topicsFormModalBottomSheet.show(it, TopicsFormModalBottomSheet.TAG)
            }
        }

//        binding.buttonFirst.setOnClickListener {
//            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
//        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val TAG = "HomeFragment"
    }
}