package de.florianhansen.roomsample.embedded

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import de.florianhansen.roomsample.BaseActivity
import de.florianhansen.roomsample.R
import de.florianhansen.roomsample.toVisibility
import io.reactivex.rxkotlin.addTo
import kotlinx.android.synthetic.main.activity_embedded.*
import javax.inject.Inject

class EmbeddedActivity : BaseActivity() {

    @Inject
    lateinit var viewModel: EmbeddedViewModel

    private val adapter by lazy { EmbeddedAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_embedded)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val embeddedAdapter = adapter
        embedded_recycler.adapter = embeddedAdapter
        embedded_recycler.layoutManager = LinearLayoutManager(this)

        viewModel.items.subscribe { adapter.items = it }.addTo(disposable)
        viewModel.isLoading.subscribe { embedded_progress.visibility = it.toVisibility() }.addTo(disposable)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.add_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.action_add -> {
                viewModel.addItem()
                true
            }
            android.R.id.home -> {
                finish()
                true
            }
            else -> {
                false
            }
        }
    }
}
