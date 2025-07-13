package com.example.myanimelist.ui

import android.os.Bundle
import androidx.navigation.NavDirections
import com.example.myanimelist.R

object NavGraphDirections {
    fun actionGlobalDetail(malId: Int, mediaType: String): NavDirections {
        return object : NavDirections {
            override val actionId: Int = R.id.action_global_detail
            override val arguments: Bundle
                get() = Bundle().apply {
                    putInt("malId", malId)
                    putString("mediaType", mediaType)
                }
        }
    }
}