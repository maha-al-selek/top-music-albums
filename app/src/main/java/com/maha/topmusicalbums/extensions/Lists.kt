package com.maha.topmusicalbums.extensions

import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.types.RealmList
import com.maha.topmusicalbums.model.api.Genre
import com.maha.topmusicalbums.model.db.RealmGenre

fun List<Genre>.toRealmList(): RealmList<RealmGenre> {
    val realmList = realmListOf<RealmGenre>()
    this.forEach { genre ->
        realmList.add(RealmGenre().apply {
            genreId = genre.genreId
            name = genre.name
        })
    }
    return realmList
}
