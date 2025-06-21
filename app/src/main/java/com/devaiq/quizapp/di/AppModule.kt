package com.devaiq.quizapp.di

import com.google.firebase.auth.FirebaseAuth
import com.devaiq.quizapp.data.firebase.AuthService
import com.devaiq.quizapp.data.firebase.FireStoreService
import com.devaiq.quizapp.data.repository.AuthRepositoryImpl
import com.devaiq.quizapp.data.repository.DifficultyRepositoryImpl
import com.devaiq.quizapp.data.repository.SubjectRepositoryImpl
import com.devaiq.quizapp.domain.repository.AuthRepository
import com.devaiq.quizapp.domain.repository.DifficultyRepository
import com.devaiq.quizapp.domain.repository.SubjectRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()

    @Provides
    @Singleton
    fun provideAuthService(auth: FirebaseAuth): AuthService = AuthService(auth)

    @Provides
    @Singleton
    fun provideAuthRepository(authService: AuthService): AuthRepository {
        return AuthRepositoryImpl(authService)
    }

    @Provides
    @Singleton
    fun provideFireStoreService(): FireStoreService = FireStoreService()

    @Provides
    @Singleton
    fun provideSubjectRepository(fireStoreService: FireStoreService
    ): SubjectRepository {
        return  SubjectRepositoryImpl(fireStoreService)
    }

    @Provides
    @Singleton
    fun provideDifficultyRepository(fireStoreService: FireStoreService): DifficultyRepository {
        return DifficultyRepositoryImpl(fireStoreService)
    }

}