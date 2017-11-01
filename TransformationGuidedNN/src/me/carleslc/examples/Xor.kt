package me.carleslc.examples

import me.carleslc.network.Network
import me.carleslc.utils.Execution
import me.carleslc.network.Evaluation
import me.carleslc.transforms.StaticTransformation

val xorMap: Map<Pair<Int, Int>, Int> = mapOf(Pair(0 to 0, 0), Pair(0 to 1, 1), Pair(1 to 0, 1), Pair(1 to 1, 0))

// Performs the Xor operation
fun main(args: Array<String>) {
	Network.name = "XOR"
	Execution<Pair<Int, Int>, Int>(Evaluation.from(::check),
			arrayOf(1 to 0, 1 to 1),
			arrayOf(StaticTransformation(0), StaticTransformation(1))).execute()
}

fun check(input: Pair<Int, Int>, output: Int): Double {
	return if (xorMap[input] == output) 1.0 else 0.0
}
