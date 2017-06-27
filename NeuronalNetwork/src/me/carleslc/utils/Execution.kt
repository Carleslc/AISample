package me.carleslc.utils

import me.carleslc.transforms.Transformation
import me.carleslc.network.Evaluation
import me.carleslc.network.Network
import me.carleslc.memory.Memory

class Execution<I, O>(private val evaluation: Evaluation<I, O>,
					  private val inputs: Array<I>,
					  private val transforms: Array<Transformation<I, O>>) {
	
	fun execute(all: Boolean = false, statusLimit: Int = 4, show: Boolean = true, callback: ((Map<I, Any?>) -> Any?)? = null) {
		val network = Network<I, O>(evaluation)
		println("Filling inputs...")
		network.add(*inputs)
		network.add(*transforms)
		val results = if (all) network.getAllOutputs(statusLimit) else network.getOutputs(statusLimit)
		if (show) println("\n$results")
		callback?.invoke(results)
		Memory.save()
	}
	
}
