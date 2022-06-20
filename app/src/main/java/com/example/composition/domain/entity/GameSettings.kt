package com.example.composition.domain.entity

import java.io.Serializable

data class GameSettings(
    val maxSumValue: Int,
    val minCountOfRightAnswers: Int,
    val minPercentOfRightAnswers: Int,
    val gameTimeInSeconds: Int
):Serializable{
    val minCountOfRightAnswersString:String
        get() = minCountOfRightAnswers.toString()
    val minPercentOfRightAnswersString:String
        get() = minPercentOfRightAnswers.toString()
}

