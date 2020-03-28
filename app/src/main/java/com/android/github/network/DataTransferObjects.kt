package com.android.github.network

import com.android.github.domain.GitHubRepository
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GitHubRepositoryContainer(val items: List<RepositoryDTO>)

@JsonClass(generateAdapter = true)
data class RepositoryDTO(
    val id: Long,
    val name: String,
    val owner: OwnerDTO,
    val description: String?,
    @Json(name = "stargazers_count") val starCounter: Long,
    val language: String?
)

@JsonClass(generateAdapter = true)
data class OwnerDTO(
    val login: String,
    @Json(name = "avatar_url") val avatarUrl: String
)

fun GitHubRepositoryContainer.asDomainModel(): List<GitHubRepository> {
    return items.map {
        GitHubRepository(
            id = it.id,
            name = it.name,
            description = it.description,
            owner = it.owner.login,
            imgSrcUrl = it.owner.avatarUrl
        )
    }
}