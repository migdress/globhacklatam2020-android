package com.reciapp.user.domain.useCases

import com.reciapp.user.data.models.login.LoginRequest
import com.reciapp.user.domain.repositories.LoginRemoteRepository
import com.reciapp.user.domain.repositories.LoginLocalRepository
import io.reactivex.Completable

/**
 * Created by Jorge Henao on 30/05/20.
 */
class LoginUC(
    private val loginCloudRepository: LoginRemoteRepository,
    private val loginLocalRepository: LoginLocalRepository
) {

    /**
     * Method used to build, generate and storage the login process
     */
    fun login(username: String, password: String): Completable {
        return loginCloudRepository.login(LoginRequest(username, password))
            .flatMapCompletable { loginResponse ->
                //almacenar informacion
                loginLocalRepository.saveUserInfo(loginResponse)
                Completable.complete()
            }
    }
}
