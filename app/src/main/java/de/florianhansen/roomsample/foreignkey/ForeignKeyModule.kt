package de.florianhansen.roomsample.foreignkey

import dagger.Module
import dagger.Provides

@Module
class ForeignKeyModule {

    @Provides
    fun viewModel(viewModel: ForeignKeyViewModelImpl): ForeignKeyViewModel = viewModel
}