package com.android.github.domain

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Domain objects are plain Kotlin data classes that represent the things in our app. These are the
 * objects that should be displayed on screen, or manipulated by the app.
 */

@Parcelize
data class GitHubRepository(
    val id: Long,
    val name: String,
    val owner: String,
    val description: String?,
    val imgSrcUrl: String?,
    val language: String?,
    val starCounter: Long,
    val forkCounter: Long
) : Parcelable