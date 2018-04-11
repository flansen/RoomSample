package de.florianhansen.roomsample.embedded

import android.os.Bundle
import de.florianhansen.roomsample.BaseActivity
import de.florianhansen.roomsample.R
import javax.inject.Inject

class EmbeddedActivity : BaseActivity() {

    @Inject
    lateinit var viewModel: EmbeddedViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_embedded)
        println("moep")
    }
}
