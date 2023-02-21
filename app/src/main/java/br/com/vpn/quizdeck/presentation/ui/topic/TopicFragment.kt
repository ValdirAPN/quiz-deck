package br.com.vpn.quizdeck.presentation.ui.topic

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.vpn.quizdeck.R
import br.com.vpn.quizdeck.databinding.FragmentTopicBinding
import br.com.vpn.quizdeck.domain.model.Deck
import br.com.vpn.quizdeck.domain.model.Topic
import br.com.vpn.quizdeck.presentation.ui.home.HomeFragmentDirections
import br.com.vpn.quizdeck.presentation.ui.home.TopicAdapter
import br.com.vpn.quizdeck.presentation.ui.home.TopicsFormModalBottomSheet
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TopicFragment : Fragment() {

    private var _binding: FragmentTopicBinding? = null
    private val binding get() = _binding!!

    private val args: TopicFragmentArgs by navArgs()

    private val viewModel: TopicViewModel by viewModels()

    private lateinit var decksAdapter: DeckAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentTopicBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val topic = args.topic

        binding.toolbar.title = topic.title
        binding.toolbar.setNavigationIcon(R.drawable.ic_back)
        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }

        binding.fabNewDeck.setOnClickListener {
            activity?.supportFragmentManager?.let {
                val decksFormModalBottomSheet = DecksFormModalBottomSheet.newInstance(
                    topicId = topic.id.toString(),
                    listener = object : DecksFormModalBottomSheet.DecksFormListener {
                        override fun onConfirm(deck: Deck) {
                            viewModel.addDeck(deck)
                        }
                    }
                )
                decksFormModalBottomSheet.show(it, TopicsFormModalBottomSheet.TAG)
            }
        }

        decksAdapter = DeckAdapter(mutableListOf()) { deck ->
        }

        binding.rvDecks.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = decksAdapter
        }

        viewModel.loadDecks(topicId = topic.id.toString())

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { uiState ->
                    uiState.decks.let { decks ->
                        if (decks.isEmpty()) {
                            binding.llNothingFound.visibility = View.VISIBLE
                        } else {
                            binding.llNothingFound.visibility = View.GONE
                            decksAdapter.addAll(decks)
                        }
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}