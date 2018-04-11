package de.florianhansen.roomsample.embedded

import javax.inject.Inject


interface EmbeddedViewModel {}

class EmbeddedViewModelImpl @Inject constructor() : EmbeddedViewModel {

}