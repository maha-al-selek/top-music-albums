package com.maha.topmusicalbums.di

import com.maha.topmusicalbums.data.api.ApiService
import com.maha.topmusicalbums.data.repository.DataRepository
import com.maha.topmusicalbums.model.db.RealmAlbum
import com.maha.topmusicalbums.model.db.RealmGenre
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideRealm(): Realm {
        val config = RealmConfiguration.Builder(schema = setOf(RealmAlbum::class,RealmGenre::class))
            .name("db.realm")
            .deleteRealmIfMigrationNeeded()
            .build()
        return Realm.open(config)
    }

    @Provides
    @Singleton
    fun provideAlbumRepository(apiService: ApiService, realm: Realm): DataRepository {
        return DataRepository(apiService, realm)
    }
}