package br.com.vpn.quizdeck.presentation.ui.match

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.vpn.quizdeck.MainActivity
import br.com.vpn.quizdeck.R
import br.com.vpn.quizdeck.databinding.FragmentMatchBinding
import br.com.vpn.quizdeck.presentation.ui.common.DividerItemDecoration
import br.com.vpn.quizdeck.presentation.ui.deck.CardAdapter
import br.com.vpn.quizdeck.presentation.ui.deck.DeckFragmentArgs
import br.com.vpn.quizdeck.presentation.ui.home.HomeFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MatchFragment : Fragment() {

    private var _binding: FragmentMatchBinding? = null
    private val binding get() = _binding!!

    private val args: DeckFragmentArgs by navArgs()

    private val viewModel: MatchViewModel by viewModels()

    private lateinit var matchResultAdapter: MatchResultAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMatchBinding.inflate(inflater, container, false)
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

        setupRecyclerView()

        initListeners()

        viewModel.loadCards(deck.cards)

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { uiState ->
                    uiState.cards.let { cards ->
                        if (cards.isNotEmpty()) {
                            binding.tvQuestion.text = deck.cards[uiState.currentCardIndex].statement
                            binding.tvAnswer.text = deck.cards[uiState.currentCardIndex].answer
                        }
                    }

                    if (uiState.hasFinished) {
                        MainActivity.mInterstitialAd?.show(requireActivity()) ?: run {
                            Log.d(TAG, "The interstitial ad wasn't ready yet.")
                        }
                        binding.matchContainer.visibility = View.GONE
                        binding.resultContainer.visibility = View.VISIBLE
                        matchResultAdapter.addAll(uiState.cards)
                    } else {
                        binding.matchContainer.visibility = View.VISIBLE
                        binding.resultContainer.visibility = View.GONE
                    }

                    if (uiState.showAnswer) {
                        binding.tvAnswer.visibility = View.VISIBLE
                        binding.btnSeeAnswer.icon = ContextCompat.getDrawable(view.context, R.drawable.ic_eye_off)
                    } else {
                        binding.tvAnswer.visibility = View.GONE
                        binding.btnSeeAnswer.icon = ContextCompat.getDrawable(view.context, R.drawable.ic_eye)
                    }

                    uiState.currentCardIndex.let { index ->
                        val teste: Float = index + 1F
                        val progress = ((teste / uiState.cards.size) * 100).toInt()
                        Log.d("TAG", "onViewCreated: $progress")
                        binding.piProgress.progress = progress
                        binding.tvCurrent.text =
                            resources.getString(
                                R.string.match_progress_indicator,
                                index + 1,
                                uiState.cards.size)
                    }
                }
            }
        }
    }

    private fun setupRecyclerView() {
        ContextCompat.getDrawable(requireContext(), R.drawable.recyclerview_divider)?.let {
            val dividerItemDecoration = DividerItemDecoration(it)
            binding.rvCards.addItemDecoration(dividerItemDecoration)
        }

        matchResultAdapter = MatchResultAdapter(mutableListOf())

        binding.rvCards.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = matchResultAdapter
        }
    }

    private fun initListeners() {
        binding.btnCorrectAnswer.setOnClickListener {
            viewModel.answer(isCorrect = true)
        }

        binding.btnWrongAnswer.setOnClickListener {
            viewModel.answer(isCorrect = false)
        }

        binding.btnSeeAnswer.setOnClickListener {
            viewModel.handleAnswerExibition()
        }

        binding.btnFinish.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    companion object {
        private const val TAG = "MatchFragment"
    }

}