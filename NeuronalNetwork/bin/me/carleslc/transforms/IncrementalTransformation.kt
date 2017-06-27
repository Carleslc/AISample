package me.carleslc.transforms

import me.carleslc.neuron.ScoredOutput
import me.carleslc.network.Evaluation

class IncrementalTransformation<I>(val minValue: Double = 0.0, val step: Double = 1.0) : Transformation<I, Double> {
	
	private var counters: MutableMap<I, Double> = mutableMapOf()
	private var bests: MutableMap<I, ScoredOutput<I, Double>> = mutableMapOf()
	
	override fun isDynamic() = true
	
	override operator fun invoke(input: I): Double {
		val currentCount = counters[input]
		val count = currentCount?.plus(step) ?: minValue
		counters.put(input, count)
		return count
	}
	
	override fun reset() {
		counters = mutableMapOf()
	}
	
	override fun example(example: ScoredOutput<I, Double>, evaluation: Evaluation<I, Double>) {
		with (example) {
			if (evaluation.pass(this)) remove(output.input)
			else {
				val currentBest = bests[output.input]
				if (currentBest == null || currentBest == this) add(output.input, this)
				else if (this > currentBest) {
					val scoreDiff = score - currentBest.score
					val remainingScore = evaluation.maxScore - score
					val times = Math.round(remainingScore / scoreDiff) - 1
					add(output.input, this, output.data + times*step)
				}
			}
		}
	}
	
	private fun add(input: I, best: ScoredOutput<I, Double>, counter: Double = best.output.data) {
		bests.put(input, best)
		counters.put(input, counter)
	}
	
	private fun remove(input: I) {
		bests.remove(input)
	}
}
