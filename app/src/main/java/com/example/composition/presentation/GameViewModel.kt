package com.example.composition.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.composition.data.GameRepositoryImpl
import com.example.composition.domain.entity.Question
import com.example.composition.domain.usecases.GenerateQuestionsUseCase

class GameViewModel : ViewModel() {
    private val repository = GameRepositoryImpl
    private val generateQuestionsUseCase =GenerateQuestionsUseCase(repository)
    private val _question = MutableLiveData<Question>()
    val question: LiveData<Question>
        get() = _question

    fun generateQuestion (muxSumValue:Int){
        _question.value = generateQuestionsUseCase.invoke(muxSumValue)
    }

}