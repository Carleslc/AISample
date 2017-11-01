package me.carleslc.neuron

import me.carleslc.transforms.Transformation

internal data class Processor<I, O> (override var data: I, var transformation: Transformation<I, O>) : Neuron<I>(data) {
	
	val result: O
		get() = transformation(data)
	
}
