package me.carleslc.transforms

import me.carleslc.neuron.ScoredOutput
import me.carleslc.network.Evaluation

interface Transformation<I, O> : (I) -> O {
	
	fun isDynamic() = false
	
	fun example(example: ScoredOutput<I, O>, evaluation: Evaluation<I, O>) { }
	
	fun reset() { }
	
}
