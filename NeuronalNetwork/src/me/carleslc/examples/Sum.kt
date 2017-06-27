package me.carleslc.examples

import me.carleslc.network.Evaluation
import me.carleslc.network.Network
import me.carleslc.utils.Execution
import me.carleslc.memory.RuntimeMemory
import me.carleslc.transforms.IncrementalTransformation

private const val PERMITTED_ERROR = 0.001
private const val PERFECT_SCORE = 0.0 // No error
private const val MIN_VALUE = -1e9
private const val STEP = 0.001

// Performs the Sum operation
fun main(args: Array<String>) {
	Network.name = "Sum"
	Execution<Pair<Double, Double>, Double>(Evaluation.from(::check, PERFECT_SCORE, -PERMITTED_ERROR),
			arrayOf(0.0 to -1.0, 2.0 to 2.1, 40.0 to 124.0, 1520014.667 to 8242207.77),
			arrayOf(RuntimeMemory(IncrementalTransformation<Pair<Double, Double>>(MIN_VALUE, STEP)))).execute()
}

fun check(input: Pair<Double, Double>, result: Double): Double {
	return -Math.abs(input.first + input.second - result)
}
