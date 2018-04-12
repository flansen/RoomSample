package de.florianhansen.roomsample.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import de.florianhansen.roomsample.MainActivity
import de.florianhansen.roomsample.embedded.EmbeddedActivity
import de.florianhansen.roomsample.embedded.EmbeddedModule
import de.florianhansen.roomsample.foreignkey.ForeignKeyActivity
import de.florianhansen.roomsample.foreignkey.ForeignKeyModule

@Module
abstract class ActivityBuilder {

    @ContributesAndroidInjector(modules = [EmbeddedModule::class])
    abstract fun bindsEmbeddedActivity(): EmbeddedActivity

    @ContributesAndroidInjector
    abstract fun bindsMainActivity(): MainActivity

    @ContributesAndroidInjector(modules = [ForeignKeyModule::class])
    abstract fun bindsForeignKeyActivity(): ForeignKeyActivity
}