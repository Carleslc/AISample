package me.carleslc.examples

import me.carleslc.network.Network
import me.carleslc.utils.Execution
import me.carleslc.network.Evaluation
import me.carleslc.memory.PersistentMemory
import me.carleslc.transforms.IncrementalTransformation

// Check how many upper case letters contains a string
fun main(args: Array<String>) {
	Network.name = "Strings UpperCase"
	Execution<String, Double>(Evaluation.from(::check),
			arrayOf("hola".toUpperCase(), "Hola", "2", "UppERcAsE", "DIOOOOOOOOOOOOooOOOOOOs"),
			arrayOf(PersistentMemory(IncrementalTransformation<String>()))).execute(true)
}

fun check(s: String, n: Double): Double {
	val count = s.count { it.isUpperCase() }.toDouble()
	return if (count == n) 1.0 else n / count
}
