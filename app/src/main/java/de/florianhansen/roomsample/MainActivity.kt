package de.florianhansen.roomsample

import android.os.Bundle
import de.florianhansen.roomsample.embedded.EmbeddedActivity
import de.florianhansen.roomsample.foreign_key.ForeignKeyActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        startEmbedded.setOnClickListener { start(EmbeddedActivity::class) }
        startForeignKey.setOnClickListener { start(ForeignKeyActivity::class) }
    }
}
