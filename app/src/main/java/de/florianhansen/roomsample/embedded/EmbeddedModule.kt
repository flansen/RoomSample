package de.florianhansen.roomsample.embedded

import dagger.Module
import dagger.Provides

@Module
class EmbeddedModule {

    @Provides
    fun viewModel(embeddedViewModel: EmbeddedViewModelImpl): EmbeddedViewModel = embeddedViewModel
}