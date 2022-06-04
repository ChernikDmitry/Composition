package com.example.composition.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.composition.R
import com.example.composition.databinding.FragmentGameBinding
import com.example.composition.domain.entity.GameResult
import com.example.composition.domain.entity.GameSettings
import com.example.composition.domain.entity.Level

class GameFragment : Fragment() {
    private lateinit var viewModel: GameViewModel
    private lateinit var level: Level

    private var _binding: FragmentGameBinding? = null
    private val binding: FragmentGameBinding
        get() = _binding ?: throw RuntimeException("FragmentGameBinding == null")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parseArgs()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGameBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[GameViewModel::class.java]
        viewModel.generateQuestion(6)
        observeViewModel()


        binding.tvOption1.setOnClickListener() {
            val settings = GameSettings(10, 10, 10, 10)
            val gameResult = GameResult(true, 5, 5, settings)
            launchGameFinishedFragment(gameResult)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
        private fun observeViewModel(){
            viewModel.question.observe(viewLifecycleOwner){
                val question = it
                binding.tvOption1.setText(question.options[0])
                binding.tvOption1.setText(question.options[1])
                binding.tvOption1.setText(question.options[2])
                binding.tvOption1.setText(question.options[3])
                binding.tvOption1.setText(question.options[4])
                binding.tvOption1.setText(question.options[5])
                binding.tvSum.setText(question.sum)
                binding.tvLeftNumber.setText(question.visibleNumber)
            }
        }

    private fun parseArgs() {
        requireArguments().getParcelable<Level>(KEY_LEVEL)?.let {
            level = it
        }
    }

    private fun launchGameFinishedFragment(gameResult: GameResult) {
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.main_container, GameFinishedFragment.newInstance(gameResult))
            .addToBackStack(null)
            .commit()
    }


    companion object {
        private const val KEY_LEVEL = "level"
        const val NAME = "GameFragment"
        fun newInstance(level: Level): GameFragment {
            return GameFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(KEY_LEVEL, level)
                }
            }
        }
    }
}