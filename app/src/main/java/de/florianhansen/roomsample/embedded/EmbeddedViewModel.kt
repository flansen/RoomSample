package de.florianhansen.roomsample.embedded

import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.delay
import kotlinx.coroutines.experimental.launch
import java.util.concurrent.TimeUnit.SECONDS
import javax.inject.Inject


interface EmbeddedViewModel {
    fun addItem()
    val items: Flowable<List<EmbeddedListItem>>
    val isLoading: BehaviorSubject<Boolean>
}

class EmbeddedViewModelImpl @Inject constructor(private val userDao: UserDao) : EmbeddedViewModel {

    override val isLoading: BehaviorSubject<Boolean> = BehaviorSubject.create()

    override fun addItem() {
        launch(CommonPool) {
            postLoadingValue(true)

            //Some Time Intensive Network Request
            delay(2L, SECONDS)
            userDao.insert(User(null, "Florian Hansen", Address("Street", "City", 12345)))

            postLoadingValue(false)
        }
    }

    override val items: Flowable<List<EmbeddedListItem>> by lazy {
        userDao.getAll()
                .map { it.map { it.toItemViewModel() } }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    private fun postLoadingValue(newValue: Boolean) {
        launch(UI) {
            isLoading.onNext(newValue)
        }
    }
}

private fun User.toItemViewModel() = EmbeddedListItem(name, address?.city ?: "")
