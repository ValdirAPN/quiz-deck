package br.com.vpn.quizdeck.presentation.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import br.com.vpn.quizdeck.databinding.TopicsFormModalBottomSheetBinding
import br.com.vpn.quizdeck.domain.model.Topic
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.io.Serializable
import java.util.UUID

class TopicsFormModalBottomSheet : BottomSheetDialogFragment() {

    private var _binding: TopicsFormModalBottomSheetBinding? = null
    private val binding get() = _binding!!

    private lateinit var listener: TopicsFormListener

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = TopicsFormModalBottomSheetBinding.inflate(inflater)

        dialog?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        arguments?.let {
            listener = it.getSerializable("listener") as TopicsFormListener
        }
        binding.btnCreate.setOnClickListener {
            binding.tiTitle.editText?.let {
                val title = it.text.toString()

                if (title.trim().isEmpty()) {
                    it.error = "O título não pode ser vazio!"
                    return@setOnClickListener
                }

                listener.onConfirm(
                    Topic(
                        id = UUID.randomUUID(),
                        title = title
                    )
                )
                dismiss()
            }
        }
    }

    interface TopicsFormListener : Serializable {
        fun onConfirm(topic: Topic)
    }

    companion object {
        const val TAG = "TopicsFormModalBottomSheet"

        fun newInstance(listener: TopicsFormListener) = TopicsFormModalBottomSheet().apply {
            arguments = Bundle().apply {
                putSerializable("listener", listener)
            }
        }
    }
}