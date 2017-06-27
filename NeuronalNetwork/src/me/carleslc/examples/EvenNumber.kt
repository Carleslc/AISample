package me.carleslc.examples

import me.carleslc.extensions.*
import me.carleslc.network.Network
import me.carleslc.utils.Execution
import me.carleslc.network.Evaluation
import me.carleslc.transforms.StaticTransformation

// Check if a number is even or not
fun main(args: Array<String>) {
	Network.name = "Even Numbers"
	Execution<Int, Boolean>(Evaluation.from(::check),
			(0..15).toList().toTypedArray(),
			arrayOf(StaticTransformation(true), StaticTransformation(false))).execute()
}

fun check(n: Int, even: Boolean): Double = (even == n.even()).toDouble()
