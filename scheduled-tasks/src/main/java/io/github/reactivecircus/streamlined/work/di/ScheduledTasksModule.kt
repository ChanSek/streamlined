package io.github.reactivecircus.streamlined.work.di

import android.content.Context
import androidx.work.Configuration
import androidx.work.ListenableWorker
import androidx.work.WorkManager
import dagger.Binds
import dagger.MapKey
import dagger.Module
import dagger.Provides
import dagger.Reusable
import dagger.multibindings.IntoMap
import io.github.reactivecircus.streamlined.work.scheduler.TaskScheduler
import io.github.reactivecircus.streamlined.work.scheduler.DefaultTaskScheduler
import io.github.reactivecircus.streamlined.work.worker.StorySyncWorker
import kotlin.reflect.KClass

@Module(includes = [ScheduledTasksModule.Providers::class])
abstract class ScheduledTasksModule {

    @Binds
    @Reusable
    abstract fun taskScheduler(impl: DefaultTaskScheduler): TaskScheduler

    @Binds
    @IntoMap
    @WorkerKey(StorySyncWorker::class)
    abstract fun storySyncWorker(factory: StorySyncWorker.Factory): ChildWorkerFactory

    @Module
    object Providers {

        @Provides
        @Reusable
        fun provideWorkManager(context: Context): WorkManager {
            return WorkManager.getInstance(context)
        }

        @Provides
        @Reusable
        fun provideWorkConfiguration(workerFactory: StreamlinedWorkerFactory): Configuration {
            return Configuration.Builder()
                .setWorkerFactory(workerFactory)
                .build()
        }
    }
}

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
@MapKey
internal annotation class WorkerKey(val value: KClass<out ListenableWorker>)