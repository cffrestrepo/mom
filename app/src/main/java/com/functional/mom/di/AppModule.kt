package com.functional.mom.di

import android.content.Context
import androidx.room.Room
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.squareup.picasso.OkHttp3Downloader
import com.squareup.picasso.Picasso
import com.functional.mom.commons.Constants
import com.functional.mom.commons.Constants.Companion.BASE_URL
import com.functional.mom.data.local.database.MarketDb
import com.functional.mom.data.remote.ProductDataRemoteImpl
import com.functional.mom.data.remote.RetrofitServicesInterface
import com.functional.mom.data.remote.sources.ProductDataRemoteSource
import com.functional.mom.repository.ProductRepositoryImpl
import com.functional.mom.repository.contracts.ProductRepositorySource
import com.functional.mom.repository.mappers.ProductMapper
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideRoomInstance(@ApplicationContext context: Context) = Room.databaseBuilder(
        context,
        MarketDb::class.java,
        Constants.NAME_DATA_BASE
    )// Wipes and rebuilds instead of migrating if no Migration object.
        .fallbackToDestructiveMigration()
        .build()

    @Singleton
    @Provides
    fun provideProductsDao(dataBase: MarketDb) = dataBase.productDao()

    @Singleton
    @Provides
    fun getOkHttpInstance(): OkHttpClient {
        return OkHttpClient.Builder()
            .build()
    }

    @Singleton
    @Provides
    fun getRetrofitInstance(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create()).build()
    }

    @Singleton
    @Provides
    fun getRetrofitServiceInterface(retrofit: Retrofit): RetrofitServicesInterface =
        retrofit.create(RetrofitServicesInterface::class.java)

    @Singleton
    @Provides
    fun getPicassoDownloader(okHttpClient: OkHttpClient): OkHttp3Downloader {
        return OkHttp3Downloader(okHttpClient)
    }

    @Provides
    fun providesIoDispatcher(): CoroutineDispatcher = Dispatchers.IO

    @Provides
    fun providesProductMapper(): ProductMapper = ProductMapper
}

@Module
@InstallIn(ActivityRetainedComponent::class)
abstract class DataRemoteModule {

    @Binds
    abstract fun provideProductDataRemoteImpl(productDataRemoteImpl: ProductDataRemoteImpl): ProductDataRemoteSource
}

@Module
@InstallIn(ActivityRetainedComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun provideProductRepositoryImpl(repository: ProductRepositoryImpl): ProductRepositorySource
}

@Module
@InstallIn(ActivityComponent::class)
object presentationModule {

    @Provides
    fun materialAlertDialogBuilder(@ActivityContext context: Context): MaterialAlertDialogBuilder =
        MaterialAlertDialogBuilder(context)

    @Provides
    fun getPicasso(@ActivityContext context: Context, downloader: OkHttp3Downloader): Picasso {
        return Picasso.Builder(context)
            .downloader(downloader)
            .indicatorsEnabled(true)
            .loggingEnabled(true)
            .build()
    }
}
