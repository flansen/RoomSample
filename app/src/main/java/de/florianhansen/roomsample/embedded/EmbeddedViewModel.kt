package de.florianhansen.roomsample.embedded

import de.florianhansen.roomsample.common.SimpleListItem
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.subjects.BehaviorSubject
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.delay
import java.util.concurrent.TimeUnit.SECONDS
import javax.inject.Inject


interface EmbeddedViewModel {
    fun addItem()
    val items: Flowable<List<SimpleListItem>>
    val isLoading: BehaviorSubject<Boolean>
}

class EmbeddedViewModelImpl @Inject constructor(private val userDao: UserDao) : EmbeddedViewModel {

    override val isLoading: BehaviorSubject<Boolean> = BehaviorSubject.create()

    override fun addItem() {
        async(UI) {
            isLoading.onNext(true)

            async {
                delay(2, SECONDS)
                userDao.insert(User(null, "Florian Hansen", Address("Street", "City", 12345)))
            }.await()

            isLoading.onNext(false)
        }
    }

    override val items: Flowable<List<SimpleListItem>> by lazy {
        userDao.getAll()
                .map { it.map { it.toItemViewModel() } }
                //Flowables work off the main thread
                .observeOn(AndroidSchedulers.mainThread())
    }
}

private fun User.toItemViewModel() = SimpleListItem(name, address?.city ?: "")
