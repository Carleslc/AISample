package me.carleslc.network

import me.carleslc.kotlin.extensions.time.getTimeFrom
import me.carleslc.neuron.Input
import me.carleslc.transforms.Transformation
import me.carleslc.neuron.Processor
import me.carleslc.neuron.ScoredOutput
import me.carleslc.neuron.Output
import java.util.LinkedList

internal class Synapse<I, O> (var evaluation: Evaluation<I, O>) {
	
	private val inputs: MutableList<Input<I>> = LinkedList()
	private val transforms: MutableList<Transformation<I, O>> = LinkedList()
	private val processors: MutableList<Processor<I, O>> = LinkedList()
	
	fun add(transform: Transformation<I, O>) {
		transforms.add(transform)
		inputs.forEach { processors.add(Processor(it.data, transform)) }
	}
	
	fun remove(transform: Transformation<I, O>) {
		transforms.remove(transform)
		processors.removeIf { it.transformation == transform }
	}
	
	fun add(input: Input<I>) {
		inputs.add(input)
		transforms.forEach { processors.add(Processor(input.data, it)) }
	}
	
	fun remove(input: Input<I>) {
		inputs.remove(input)
		processors.removeIf { it.data == input.data }
	}
	
	private fun reset() {
		if (processors.isEmpty() && !inputs.isEmpty() && !transforms.isEmpty()) {
			println("Reloading...")
			inputs.forEach { add(it) }
		}
		transforms.forEach { it.reset() }
	}
	
	fun compute(statusLimit: Int = 4): List<ScoredOutput<I, O>> {
		reset()
		val start = System.currentTimeMillis()
		println("Start Computation\n0%")
		val totalProcessors = processors.size.toDouble()
		var status = 0
		var lastStatus = status
		val statusStep = totalProcessors / statusLimit
		val results: MutableList<ScoredOutput<I, O>> = LinkedList()
		val passedInputs: MutableSet<I> = mutableSetOf()
		while (!processors.isEmpty()) {
			val iterator = processors.iterator()
			while (iterator.hasNext()) {
				if (status - lastStatus >= statusStep) {
					println("${ Math.floor(100 - (processors.size / totalProcessors)*100).toInt() }%")
					lastStatus = status
				}
				with (iterator.next()) {
					var remove = true
					if (!(data in passedInputs)) {
						val scored = evaluation.getScored(Output(data, result))
						results.add(scored)
						if (evaluation.pass(scored)) passedInputs.add(data)
						else if (transformation.isDynamic()) remove = false
					}
					if (remove) {
						iterator.remove()
						status++
					}
				}
			}
		}
		results.sortWith(ScoredOutput.reversedComparator)
		println("100%\nComputed in ${ getTimeFrom(start) }")
		return results
	}
	
}
