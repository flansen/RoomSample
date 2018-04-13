package de.florianhansen.roomsample.relation

import dagger.Module
import dagger.Provides

@Module
class RelationModule {

    @Provides
    fun viewModel(viewModel: RelationViewModelImpl): RelationViewModel = viewModel
}