package com.rakuten.demo.di

import com.rakuten.demo.data.repository.IRecentPhotosRepository
import com.rakuten.demo.data.repository.RecentPhotosRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class RecentPhotosRepositoryModule {

    @Binds
    @Singleton
    abstract fun bindsRecentPhotosRepository(recentPhotosRepositoryImpl: RecentPhotosRepositoryImpl): IRecentPhotosRepository
}