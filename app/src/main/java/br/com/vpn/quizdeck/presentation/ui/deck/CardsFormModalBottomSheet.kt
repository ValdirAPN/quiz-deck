package br.com.vpn.quizdeck.presentation.ui.deck

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import br.com.vpn.quizdeck.databinding.CardsFormModalBottomSheetBinding
import br.com.vpn.quizdeck.domain.model.Card
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.io.Serializable
import java.util.*

class CardsFormModalBottomSheet : BottomSheetDialogFragment() {

    private var _binding: CardsFormModalBottomSheetBinding? = null
    private val binding get() = _binding!!

    private lateinit var listener: CardsFormListener
    private var card: Card? = null
    private lateinit var deckId: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = CardsFormModalBottomSheetBinding.inflate(inflater)

        dialog?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        arguments?.let {
            card = if (it.containsKey("card")) it.getSerializable("card") as Card else null
            deckId = it.getSerializable("deckId") as String
            listener = it.getSerializable("listener") as CardsFormListener
        }

        card?.let { it ->
            binding.tiStatement.editText?.setText(it.statement)
            binding.tiAnswer.editText?.setText(it.answer)
        }

        binding.btnCreate.setOnClickListener {
            val tiTitle = binding.tiStatement.editText
            val statement = tiTitle?.text.toString()

            val tiAnswer = binding.tiAnswer.editText
            val answer = tiAnswer?.text.toString()

            if (statement.trim().isEmpty()) {
                tiTitle?.error = "O enunciado não pode ser vazio!"
                return@setOnClickListener
            }

            if (answer.trim().isEmpty()) {
                tiAnswer?.error = "A resposta não pode ser vazia!"
                return@setOnClickListener
            }

            card = if (card == null) {
                Card(
                    id = UUID.randomUUID(),
                    statement = statement,
                    answer = answer,
                    deckId = UUID.fromString(deckId)
                )
            } else {
                card!!.copy(
                    statement = statement,
                    answer = answer
                )
            }

            listener.onConfirm(card!!)
            dismiss()
        }
    }

    interface CardsFormListener : Serializable {
        fun onConfirm(card: Card)
    }

    companion object {
        const val TAG = "CardsFormModalBottomSheet"

        fun newInstance(card: Card? = null, deckId: String, listener: CardsFormListener) = CardsFormModalBottomSheet().apply {
            arguments = Bundle().apply {
                putSerializable("listener", listener)
                putSerializable("deckId", deckId)
                if (card != null) putSerializable("card", card)
            }
        }
    }
}