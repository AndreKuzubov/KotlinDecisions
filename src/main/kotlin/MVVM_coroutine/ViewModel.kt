package MVVM_coroutine

import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.async

class ViewModel {

    val repository = Repository()
    val loginSubject = BehaviorSubject.create<Boolean>()


    init {
        loginSubject
                .subscribeOn(Schedulers.single())
                .subscribe {
                    if (it) {
                        sendClientSomeData()
                    }
                }
    }

    fun login(login: String?, password: String?): Job {
        return GlobalScope.async {
            try {
                if (password.isNullOrEmpty() || login.isNullOrEmpty())
                    throw Exception("null login or password")

                val token = repository.createConnectToken()
                val version = repository.getAuthVersion()

                if (version.await() <= 2 && token.await().isNotEmpty()) {
                    val loginResult = repository.login(login, password, token.await()).await()
                    if (loginResult)
                        println("login success")
                    loginSubject.onNext(loginResult)
                } else {
                    loginSubject.onNext(false)
                }
            } catch (ex: Exception) {
                loginSubject.onNext(false)
            }
        }
    }

    fun sendClientSomeData() {
        repository.sendClientSomeData()
    }


}