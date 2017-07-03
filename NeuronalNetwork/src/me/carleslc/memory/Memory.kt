package me.carleslc.memory

import me.carleslc.transforms.Transformation
import me.carleslc.network.Evaluation
import me.carleslc.neuron.Output
import me.carleslc.neuron.ScoredOutput

open abstract class Memory<I, O> (val transformation: Transformation<I, O>): Transformation<I, O>, Cache<I, O> {
	
	companion object {
		internal val dumpers: MutableList<Dumper<*,*>> = mutableListOf()
		fun save() {
			if (dumpers.any { it.hasChanges() }) {
				println("Memorizing...")
				dumpers.forEach { it.dump() }
				println("Done")
			}
		}
	}
	
	val evaluation: Evaluation<I, O> = Evaluation.instance()
	
	final override fun isDynamic() = transformation.isDynamic()
	
	override operator fun invoke(input: I): O {
		var cached = get(input)
		if (cached != null) {
			if (transformation.isDynamic()) {
				val cachedOutput = Output(input, cached)
				val cachedScore = evaluation.evaluate(cachedOutput)
				transformation.example(ScoredOutput<I, O>(cachedOutput, cachedScore), evaluation)
				val output = transformation.invoke(input)
				val outputScore = evaluation.evaluate(input, output)
				if (outputScore > cachedScore) {
					cached = set(input, output)
				}
			}
		} else {
			cached = transformation.invoke(input)
			put(input, cached)
		}
		return cached!!
	}
	
	private fun set(input: I, output: O): O {
		remove(input)
		put(input, output)
		return output
	}
	
}
