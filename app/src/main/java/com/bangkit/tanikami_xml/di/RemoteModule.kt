package com.bangkit.tanikami_xml.di

//import com.bangkit.tanikami_xml.data.data_store.UserPreference
import com.bangkit.tanikami_xml.data.remote.retrofit.ApiConfig
import com.bangkit.tanikami_xml.data.remote.retrofit.ApiService
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
    fun provideApiGCPConnection(): ApiService = ApiConfig().getApiService()

    @Provides
    @Singleton
    fun provideRepository(apiServ: ApiService) : ProductRepository {
        return ProductRepository(apiServ)
    }
}