package com.example.kasirpintartest.di

import com.example.kasirpintartest.data.Repository
import com.example.kasirpintartest.data.RepositoryImpl
import dagger.Binds
import dagger.Module

@Module
abstract class RepoModule {
    @Binds
    abstract fun provideRepository(repositoryImpl: RepositoryImpl): Repository
}