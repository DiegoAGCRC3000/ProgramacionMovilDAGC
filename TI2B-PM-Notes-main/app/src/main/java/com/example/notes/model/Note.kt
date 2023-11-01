package com.example.notes.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class Note(
    @StringRes val nameRes: Int,
    @StringRes val descriptionRes: Int
)
