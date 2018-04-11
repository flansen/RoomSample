package de.florianhansen.roomsample

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import dagger.android.AndroidInjection
import io.reactivex.disposables.CompositeDisposable
import kotlin.reflect.KClass

abstract class BaseActivity : AppCompatActivity() {

    protected val disposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onDestroy() {
        disposable.dispose()
        super.onDestroy()
    }
}

fun <T : Activity> Activity.start(clazz: KClass<T>) {
    startActivity(Intent(this, clazz.java))
}

fun Boolean.toVisibility(): Int {
    return if (this) {
        View.VISIBLE
    } else {
        View.GONE
    }
}