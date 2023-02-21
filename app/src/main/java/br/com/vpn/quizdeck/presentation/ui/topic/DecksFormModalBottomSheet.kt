package br.com.vpn.quizdeck.presentation.ui.topic

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import br.com.vpn.quizdeck.databinding.DecksFormModalBottomSheetBinding
import br.com.vpn.quizdeck.domain.model.Deck
import br.com.vpn.quizdeck.domain.model.Topic
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.io.Serializable
import java.util.*

class DecksFormModalBottomSheet : BottomSheetDialogFragment() {

    private var _binding: DecksFormModalBottomSheetBinding? = null
    private val binding get() = _binding!!

    private lateinit var listener: DecksFormListener
    private lateinit var topicId: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DecksFormModalBottomSheetBinding.inflate(inflater)

        dialog?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        arguments?.let {
            topicId = it.getSerializable("topicId") as String
            listener = it.getSerializable("listener") as DecksFormListener
        }
        binding.btnCreate.setOnClickListener {
            binding.tiTitle.editText?.let {
                val title = it.text.toString()

                if (title.trim().isEmpty()) {
                    it.error = "O título não pode ser vazio!"
                    return@setOnClickListener
                }

                listener.onConfirm(
                    Deck(
                        id = UUID.randomUUID(),
                        title = title,
                        topicId = UUID.fromString(topicId)
                    )
                )
                dismiss()
            }
        }
    }

    interface DecksFormListener : Serializable {
        fun onConfirm(deck: Deck)
    }

    companion object {
        const val TAG = "TopicsFormModalBottomSheet"

        fun newInstance(topicId: String, listener: DecksFormListener) = DecksFormModalBottomSheet().apply {
            arguments = Bundle().apply {
                putSerializable("topicId", topicId)
                putSerializable("listener", listener)
            }
        }
    }
}