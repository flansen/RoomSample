package de.florianhansen.roomsample.embedded

import de.florianhansen.roomsample.common.SimpleListItem
import de.florianhansen.roomsample.foreign_key.Person
import de.florianhansen.roomsample.foreign_key.PersonDao
import de.florianhansen.roomsample.foreign_key.Pet
import io.reactivex.Flowable
import io.reactivex.subjects.BehaviorSubject
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.delay
import kotlinx.coroutines.experimental.launch
import java.util.concurrent.TimeUnit.SECONDS
import javax.inject.Inject


interface ForeignKeyViewModel {
    fun addItem()
    val items: Flowable<List<SimpleListItem>>
    val isLoading: BehaviorSubject<Boolean>
}

class ForeignKeyViewModelImpl @Inject constructor(private val personDao: PersonDao) : ForeignKeyViewModel {

    override val isLoading: BehaviorSubject<Boolean> = BehaviorSubject.create()

    override fun addItem() {
        isLoading.onNext(true)
        launch(CommonPool) {
            //Some Time Intensive Network Request
            delay(2L, SECONDS)

            val personId = personDao.savePerson(Person(name = "Gustav Günther"))
            val pets = listOf(
                    Pet(name = "Wau Wau", ownerId = personId),
                    Pet(name = "Miez Miez", ownerId = personId)
            )
            pets.forEach {
                personDao.savePet(it)
            }
            postLoadingValue(false)
        }
    }

    override val items: Flowable<List<SimpleListItem>> by lazy {
        Flowable.just(listOf(SimpleListItem("", "")))
    }

    private fun postLoadingValue(newValue: Boolean) {
        launch(UI) {
            isLoading.onNext(newValue)
        }
    }
}

private fun User.toItemViewModel() = SimpleListItem(name, address?.city
        ?: "")
