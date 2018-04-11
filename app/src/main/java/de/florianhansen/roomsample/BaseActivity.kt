package de.florianhansen.roomsample

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import dagger.android.AndroidInjection
import kotlin.reflect.KClass

abstract class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
    }
}

fun <T : Activity> Activity.start(clazz: KClass<T>) {
    startActivity(Intent(this, clazz.java))
}