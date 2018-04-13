package de.florianhansen.roomsample.foreignkey

import de.florianhansen.roomsample.common.SimpleListItem
import de.florianhansen.roomsample.persistance.SampleDatabase
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.subjects.BehaviorSubject
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.delay
import kotlinx.coroutines.experimental.launch
import java.util.concurrent.TimeUnit.SECONDS
import javax.inject.Inject


interface ForeignKeyViewModel {
    fun addItem()
    fun editItem()
    fun deleteItem()

    val items: Flowable<List<SimpleListItem>>
    val isLoading: BehaviorSubject<Boolean>
}

class ForeignKeyViewModelImpl @Inject constructor(private val db: SampleDatabase) : ForeignKeyViewModel {

    override val isLoading: BehaviorSubject<Boolean> = BehaviorSubject.create()

    private lateinit var itemCache: List<Person>

    override fun addItem() {
        isLoading.onNext(true)
        launch(CommonPool) {
            //Some Time Intensive Network Request
            delay(2L, SECONDS)

            //Prevent the person flowable firing before all data is saved.
            db.runInTransaction {
                val personId = db.personDao().savePerson(Person(name = "Gustav Günther"))
                val pets = listOf(
                        Pet(name = "Wau Wau", ownerId = personId),
                        Pet(name = "Miez Miez", ownerId = personId)
                )
                pets.forEach {
                    db.personDao().savePet(it)
                }
            }
            postLoadingValue(false)
        }
    }

    override fun editItem() {
        launch(CommonPool) {
            val updatedItem = itemCache[0].copy(name = "Sabrina Sauer")
            db.personDao().updatePerson(updatedItem)
        }
    }

    override fun deleteItem() {
        if (itemCache.isEmpty()) {
            return
        }

        launch(CommonPool) {
            db.personDao().deletePerson(itemCache[0])
        }
    }

    override val items: Flowable<List<SimpleListItem>> by lazy {
        db.personDao().getPersons()
                .map { persons ->
                    for (person: Person in persons) {
                        person.userId?.let { id ->
                            val pets = db.personDao().getPetsForUser(id)
                            person.pets = pets
                        }
                    }
                    itemCache = persons

                    return@map persons.map { it.toSimpleListItem() }
                }
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
