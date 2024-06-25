package com.maha.topmusicalbums.model.db

import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.types.RealmList
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey

open class RealmAlbum(
    @PrimaryKey
    var id: Int = -1,
    var artistName: String = "",
    var name: String = "",
    var albumThumbnail: String = "",
    var genre: RealmList<RealmGenre> = realmListOf(),
    var releaseDate: String = "",
    var url: String = "",
    var copyright: String = ""
) : RealmObject {
    constructor() : this(-1, "", "", "", realmListOf(), "", "", "")
}

open class RealmGenre(
    var genreId: Int = -1,
    var name: String = ""
) : RealmObject {
    constructor() : this(-1, "")
}
