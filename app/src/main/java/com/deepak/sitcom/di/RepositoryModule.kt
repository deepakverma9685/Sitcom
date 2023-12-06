package com.deepak.sitcom.di

import com.deepak.sitcom.data.remote.ApiService
import com.deepak.sitcom.data.repository.TvShowsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Singleton
    @Provides
    fun provideMovieRepository(
        apiService: ApiService,
    ): TvShowsRepository {
        return TvShowsRepository(
            apiService
        )
    }

}