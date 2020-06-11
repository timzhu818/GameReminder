package com.example.gamereminder.retrofit

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Team(
    val idTeam: String? = null,
    val strTeam: String? = null,
    val strAlternate: String? = null,
    val strSport: String? = null,
    val strLeague: String? = null,
    val strStadium: String? = null,
    val strStadiumThumb: String? = null,
    val strDescriptionEN: String? = null,
    val strTeamBadge: String? = null
): Parcelable