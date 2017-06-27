package me.carleslc.network

import me.carleslc.memory.Cache
import me.carleslc.memory.RuntimeCache
import me.carleslc.neuron.Output
import me.carleslc.memory.Dumper
import me.carleslc.memory.OnlyReadDumper
import me.carleslc.neuron.ScoredOutput

public typealias Score = Double

abstract class Evaluation<I, O> (val maxScore: Score, val minScore: Score, private val save: Boolean, private val path: String) {
	
	companion object Factory {
		
		private var instance: Evaluation<*,*>? = null
		fun <I, O> instance() = instance as Evaluation<I, O>
		
		fun <I, O> from(evalFunction: (I, O) -> Score,
						maxScore: Score = 1.0, minScore: Score = maxScore,
						save: Boolean = false, path: String = ""): Evaluation<I, O> {
			val evaluation = object: Evaluation<I, O>(maxScore, minScore, save, path) {
				override fun eval(input: I, output: O): Score = evalFunction(input, output)
			}
			instance = evaluation
			return evaluation
		}
		
	}

	val validScoreRange = if (maxScore >= minScore) minScore .. maxScore else maxScore .. minScore 
	
	private val cache: Cache<Output<I, O>, Double> = RuntimeCache()
	private val dumper = if (save) Dumper(cache, path) else OnlyReadDumper(cache)
	
	final internal fun evaluate(output: Output<I, O>): Score {
		with (output) {
			var cachedScore = cache.get(this)
			if (cachedScore == null) {
				cachedScore = eval(input, data)
				cache.put(output, cachedScore)
			}
			return cachedScore
		}
	}
	
	final fun evaluate(input: I, output: O): Score = evaluate(Output(input, output)) // Caching
	
	protected abstract fun eval(input: I, output: O): Score // No caching
	
	final internal fun getScored(output: Output<I, O>) = ScoredOutput(output, evaluate(output))
	
	fun pass(score: Double): Boolean = score in validScoreRange
	
	final fun pass(input: I, output: O): Boolean = pass(evaluate(input, output))
	
	final internal fun pass(scored: ScoredOutput<I, O>) = pass(scored.score)
	
}
