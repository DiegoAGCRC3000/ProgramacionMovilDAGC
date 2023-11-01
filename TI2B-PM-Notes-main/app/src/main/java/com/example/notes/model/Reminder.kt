package com.example.notes.model

import androidx.annotation.StringRes

data class Reminder(
    @StringRes val nameRes: Int,
    @StringRes val descriptionRes: Int,
    @StringRes val status: Int,
    val dateRes: Int,
    val hourRes: Int,
)
