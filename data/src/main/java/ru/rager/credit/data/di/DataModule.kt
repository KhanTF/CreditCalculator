package ru.rager.credit.data.di

import dagger.Module

@Module(includes = [RepositoryModule::class, DatabaseModule::class])
class DataModule