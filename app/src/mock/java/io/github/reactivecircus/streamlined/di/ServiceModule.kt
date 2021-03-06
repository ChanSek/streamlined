package io.github.reactivecircus.streamlined.di

import dagger.Module
import dagger.Provides
import dagger.Reusable
import io.github.reactivecircus.streamlined.remote.api.NewsApiService
import io.github.reactivecircus.streamlined.remote.di.MockRemoteComponent

@Module
object ServiceModule {

    @Provides
    @Reusable
    fun provideMockRemoteComponent(): MockRemoteComponent {
        return MockRemoteComponent.factory().create()
    }

    @Provides
    @Reusable
    fun provideNewsApiService(mockRemoteComponent: MockRemoteComponent): NewsApiService {
        return mockRemoteComponent.newsApiService
    }
}
