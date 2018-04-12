package de.florianhansen.roomsample.foreignkey

import de.florianhansen.roomsample.common.SimpleListItem
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
        personDao.getPersons()
                .map { persons ->
                    for (person: Person in persons) {
                        person.userId?.let { id ->
                            val pets = personDao.getPetsForUser(id)
                            person.pets = pets
                        }
                    }
                    return@map persons.map { it.toSimpleListItem() }
                }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    private fun postLoadingValue(newValue: Boolean) {
        launch(UI) {
            isLoading.onNext(newValue)
        }
    }
}

private fun Person.toSimpleListItem(): SimpleListItem {
    val petNames = pets?.map { it.name }?.joinToString(", ") ?: ""
    return SimpleListItem(name, petNames)
}
