package me.carleslc.memory

import me.carleslc.transforms.Transformation

open class RuntimeMemory<I, O> (transformation: Transformation<I, O>) : Memory<I, O>(transformation) {
	
	override var changes = false
	
	protected val cache: Cache<I, O> = RuntimeCache()
	
	override fun get(input: I): O? = cache.get(input)

	override fun put(input: I, output: O) {
		cache.put(input, output)
		changes = true
	}
	
	override fun remove(input: I) {
		cache.remove(input)
		changes = true
	}
	
	override fun items() = cache.items()
	
}
