package com.devaiq.quizapp.utils

import android.content.Context
import com.devaiq.quizapp.data.model.QuestionModel
import com.google.common.reflect.TypeToken
import com.google.gson.Gson

fun readQuestionsFromAssets(context: Context): List<QuestionModel> {
    val jsonString = context.assets.open("darthard.json")
        .bufferedReader()
        .use { it.readText() }

    val type = object : TypeToken<List<QuestionModel>>() {}.type
    return Gson().fromJson(jsonString, type)
}