package me.carleslc.network

import me.carleslc.transforms.Transformation
import me.carleslc.extensions.getTimeFrom
import me.carleslc.neuron.Input
import me.carleslc.neuron.ScoredOutput

class Network<I, O> (evaluation: Evaluation<I, O>) {

    companion object {
		var name = "Network"
    }
	
	private val synapse = Synapse(evaluation)
	
	var evaluation = evaluation
			set(value) {
				evaluation = value
				synapse.evaluation = value
			}
	
	fun add(vararg transforms: Transformation<I, O>) = transforms.forEach { synapse.add(it) }
	
	fun remove(vararg transforms: Transformation<I, O>) = transforms.forEach { synapse.remove(it) }
	
	fun add(vararg inputs: I) = inputs.forEach { synapse.add(Input<I>(it)) }
	
	fun remove(vararg inputs: I) = inputs.forEach { synapse.remove(Input<I>(it)) }
	
	fun getOutputs(statusLimit: Int = 4): Map<I, O> {
		return writeResults(statusLimit) { outputs, scored ->
			with (scored.output) { if (!outputs.containsKey(input)) outputs[input] = data }
		}
	}
	
	fun getAllOutputs(statusLimit: Int = 4): Map<I, MutableList<Pair<O, Double>>> {
		return writeResults(statusLimit) { outputs, scored ->
			with (scored.output) {
				outputs[input]?.add(Pair(data, scored.score)) ?: outputs.put(input, mutableListOf(Pair(data, scored.score)))
			}
		}
	}
	
	private fun <T> writeResults(statusLimit: Int, fillFunction: (MutableMap<I, T>, ScoredOutput<I, O>) -> Any?): Map<I, T> {
		val outputs: MutableMap<I, T> = mutableMapOf()
		val computedOutputs = synapse.compute(statusLimit)
		println("Writing results...")
		val start = System.currentTimeMillis()
		computedOutputs.forEach { fillFunction(outputs, it) }
		println("Written in ${getTimeFrom(start)}")
		return outputs
	}
	
}
