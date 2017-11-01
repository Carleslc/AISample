package me.carleslc.neuron

data class Input<I> (override var data: I) : Neuron<I>(data)
