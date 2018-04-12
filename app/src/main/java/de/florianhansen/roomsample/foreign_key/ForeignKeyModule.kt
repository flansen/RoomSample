package de.florianhansen.roomsample.embedded

import dagger.Module
import dagger.Provides

@Module
class ForeignKeyModule {

    @Provides
    fun viewModel(viewModel: ForeignKeyViewModelImpl): ForeignKeyViewModel = viewModel
}