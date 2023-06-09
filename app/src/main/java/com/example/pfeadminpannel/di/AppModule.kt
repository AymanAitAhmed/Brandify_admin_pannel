package com.example.pfeadminpannel.di

import android.content.Context
import com.example.pfeadminpannel.components.Constants
import com.example.pfeadminpannel.data.ListSortingPreferences
import com.example.pfeadminpannel.data.localServerApi.LocalServerApi
import com.example.pfeadminpannel.data.localServerApi.LocalServerRepository
import com.example.pfeadminpannel.data.localServerApi.LocalServerRepositoryImpl
import com.example.pfeadminpannel.data.facebookApi.FacebookApi
import com.example.pfeadminpannel.data.facebookApi.FacebookRepository
import com.example.pfeadminpannel.data.facebookApi.FacebookRepositoryImpl
import com.example.pfeadminpannel.views.allUsers.ListSortingType
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@Module
@InstallIn(ViewModelComponent::class)
class AppModule {

    @Provides
    fun provideOkHTTPClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(1,TimeUnit.MINUTES)
            .readTimeout(30,TimeUnit.SECONDS)
            .writeTimeout(30,TimeUnit.SECONDS)
            .build()
    }

    @Provides
    fun provideLocalServerApi(): LocalServerApi {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(Constants.LOCAL_BASE_URL)
            .client(provideOkHTTPClient())
            .build()
            .create(LocalServerApi::class.java)

    }

    @Provides
    fun provideLocalServerRepository() : LocalServerRepository = LocalServerRepositoryImpl(
        provideLocalServerApi()
    )

    @Provides
    fun provideFacebookApi(): FacebookApi{
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(Constants.FACEBOOK_BASE_URL)
            .client(provideOkHTTPClient())
            .build()
            .create(FacebookApi::class.java)
    }

    @Provides
    fun provideFacebookRepository():FacebookRepository = FacebookRepositoryImpl(
        provideFacebookApi()
    )

    @Provides
    fun provideSortingListPreferences(@ApplicationContext context: Context): ListSortingPreferences{
        return ListSortingPreferences(context)
    }

}