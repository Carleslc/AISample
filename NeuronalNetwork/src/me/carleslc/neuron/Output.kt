package me.carleslc.neuron

data class Output<I, O> (val input: I, override var data: O) : Neuron<O>(data)

data class ScoredOutput<I, O> (val output: Output<I, O>, val score: Double) : Comparable<ScoredOutput<I, O>> {
	
	companion object {
		val comparator = compareBy<ScoredOutput<*,*>> { it.score }
				.thenBy { comparableField(it.output.input) }
				.thenBy { comparableField(it.output.data) }
		
		val reversedComparator = compareByDescending<ScoredOutput<*,*>> { it.score }
				.thenBy { comparableField(it.output.input) }
				.thenBy { comparableField(it.output.data) }
		
		private fun <T> comparableField(field: T): Comparable<*>? = if (field is Comparable<*>) field else null
	}
	
	override fun compareTo(other: ScoredOutput<I, O>) = comparator.compare(this, other)
}
