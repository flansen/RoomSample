package de.florianhansen.roomsample.foreign_key

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import de.florianhansen.roomsample.BaseActivity
import de.florianhansen.roomsample.R
import de.florianhansen.roomsample.common.SimpleListAdapter
import de.florianhansen.roomsample.embedded.ForeignKeyViewModel
import de.florianhansen.roomsample.toVisibility
import io.reactivex.rxkotlin.addTo
import kotlinx.android.synthetic.main.list_activity.*
import javax.inject.Inject

class ForeignKeyActivity : BaseActivity() {

    @Inject
    lateinit var viewModel: ForeignKeyViewModel

    private val adapter by lazy { SimpleListAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.list_activity)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        recycler.adapter = adapter
        recycler.layoutManager = LinearLayoutManager(this)

        viewModel.items.subscribe { adapter.items = it }.addTo(disposable)
        viewModel.isLoading.subscribe { progress.visibility = it.toVisibility() }.addTo(disposable)
    }
}
