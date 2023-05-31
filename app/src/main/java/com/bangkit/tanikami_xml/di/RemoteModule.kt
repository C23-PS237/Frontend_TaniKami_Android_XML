package com.bangkit.tanikami_xml.di

import com.bangkit.tanikami_xml.data.repository.ProductRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RemoteModule {

    @Provides
    @Singleton
    fun provideRepository() : ProductRepository {
        return ProductRepository()
    }
}