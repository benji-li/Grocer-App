package com.alpaca.android.grocer

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class UserInfo(
    var firstName: String? = "",
    var lastName: String? = ""
)