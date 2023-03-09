package br.com.vpn.quizdeck.presentation.ui.deck

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
import br.com.vpn.quizdeck.databinding.FragmentDeckBinding
import br.com.vpn.quizdeck.domain.model.Card
import br.com.vpn.quizdeck.domain.model.Deck
import br.com.vpn.quizdeck.presentation.ui.home.HomeFragmentDirections
import br.com.vpn.quizdeck.presentation.ui.home.TopicsFormModalBottomSheet
import br.com.vpn.quizdeck.presentation.ui.topic.DecksFormModalBottomSheet
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DeckFragment : Fragment() {

    private var _binding: FragmentDeckBinding? = null
    private val binding get() = _binding!!

    private val args: DeckFragmentArgs by navArgs()

    private val viewModel: DeckViewModel by viewModels()

    private lateinit var cardsAdapter: CardAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDeckBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val deck = args.deck

        binding.toolbar.title = deck.title
        binding.toolbar.setNavigationIcon(R.drawable.ic_back)
        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }

        binding.fabNewDeck.setOnClickListener {
            activity?.supportFragmentManager?.let {
                val decksFormModalBottomSheet = CardsFormModalBottomSheet.newInstance(
                    deckId = deck.id.toString(),
                    listener = object : CardsFormModalBottomSheet.CardsFormListener {
                        override fun onConfirm(card: Card) {
                            viewModel.addCard(card)
                        }
                    }
                )
                decksFormModalBottomSheet.show(it, TopicsFormModalBottomSheet.TAG)
            }
        }

        binding.btnStartMatch.setOnClickListener {
            val action = DeckFragmentDirections.openMatchFragment(deck)
            findNavController().navigate(action)
        }

        cardsAdapter = CardAdapter(mutableListOf())

        binding.rvCards.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = cardsAdapter
        }

        viewModel.loadCards(deckId = deck.id.toString())

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { uiState ->
                    uiState.cards.let { cards ->
                        if (cards.isEmpty()) {
                            binding.llNothingFound.visibility = View.VISIBLE
                            binding.btnStartMatch.isEnabled = false
                        } else {
                            binding.llNothingFound.visibility = View.GONE
                            binding.btnStartMatch.isEnabled = true
                            cardsAdapter.addAll(cards)
                            deck.cards = cards
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