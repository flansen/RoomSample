package de.florianhansen.roomsample.relation

import de.florianhansen.roomsample.common.SimpleListItem
import de.florianhansen.roomsample.persistance.SampleDatabase
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.subjects.BehaviorSubject
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.delay
import kotlinx.coroutines.experimental.launch
import javax.inject.Inject

interface RelationViewModel {
    fun addItem()

    val items: Flowable<List<SimpleListItem>>
    val isLoading: BehaviorSubject<Boolean>
}

class RelationViewModelImpl @Inject constructor(private val db: SampleDatabase) : RelationViewModel {
    override val isLoading: BehaviorSubject<Boolean> = BehaviorSubject.create()

    override fun addItem() {
        isLoading.onNext(true)
        launch(CommonPool) {
            delay(1)

            db.runInTransaction {
                db.gitDao().let {
                    val user = GitUser(null, "Florian Hansen")
                    val id = it.saveUser(user)
                    val repos = listOf(
                            Repo(null, "First Repo", id),
                            Repo(null, "Second Repo", id)
                    )
                    it.saveRepos(repos)
                }
            }
            postLoadingValue(false)
        }
    }

    override val items: Flowable<List<SimpleListItem>> by lazy {
        db.gitDao().getUsersAndRepos()
                .map {
                    return@map it.map { it.toSimpleListItem() }
                }
                .observeOn(AndroidSchedulers.mainThread())
    }

    private fun postLoadingValue(newValue: Boolean) {
        launch(UI) {
            isLoading.onNext(newValue)
        }
    }
}

private fun UserAndRepo.toSimpleListItem(): SimpleListItem {
    val repoNames = repos.joinToString(", ") { it.name }
    return SimpleListItem(user?.name ?: "", repoNames)
}