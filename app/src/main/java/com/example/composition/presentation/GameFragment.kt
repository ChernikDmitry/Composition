package com.example.composition.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.composition.R
import com.example.composition.databinding.FragmentGameBinding
import com.example.composition.domain.entity.GameResult
import com.example.composition.domain.entity.GameSettings
import com.example.composition.domain.entity.Level

class GameFragment : Fragment() {
    private lateinit var level: Level
    private val viewModel by lazy {
        ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)
        )[GameViewModel::class.java]
    }

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
        viewModel.startGame(level)
        viewModel.question.observe(viewLifecycleOwner){
            with(binding) {
                tvOption1.setText(it.options[0])
                tvOption2.setText(it.options[1])
                tvOption3.setText(it.options[2])
                tvOption4.setText(it.options[3])
                tvOption5.setText(it.options[4])
                tvOption6.setText(it.options[5])
            }
        }
        viewModel.formattedTime.observe(viewLifecycleOwner){
           binding.tvTimer.setText(it)
        }
        viewModel.percentOfRightAnswers.observe(viewLifecycleOwner){
            binding.progressBar.setProgress(it,false)
        }
        viewModel.progressAnswers.observe(viewLifecycleOwner){
            binding.tvAnswersProgress.setText(it)
        }

        with(binding) {
            tvOption1.setOnClickListener(){
                viewModel.chooseAnswer(this.tvOption1.text.get(0).digitToInt())
            }
            tvOption2.setOnClickListener(){
                viewModel.chooseAnswer(this.tvOption2.text.get(0).digitToInt())
            }
            tvOption3.setOnClickListener(){
                viewModel.chooseAnswer(this.tvOption3.text.get(0).digitToInt())
            }
            tvOption4.setOnClickListener(){
                viewModel.chooseAnswer(this.tvOption4.text.get(0).digitToInt())
            }
            tvOption5.setOnClickListener(){
                viewModel.chooseAnswer(this.tvOption5.text.get(0).digitToInt())
            }
            tvOption6.setOnClickListener(){
                viewModel.chooseAnswer(this.tvOption6.text.get(0).digitToInt())
            }
        }



//        binding.tvOption1.setOnClickListener() {
//            val settings = GameSettings(10, 10, 10, 10)
//            val gameResult = GameResult(true, 5, 5, settings)
//            launchGameFinishedFragment(gameResult)
//        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun parseArgs() {
        level = requireArguments().getSerializable(KEY_LEVEL) as Level
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
                    putSerializable(KEY_LEVEL, level)
                }
            }
        }
    }
}