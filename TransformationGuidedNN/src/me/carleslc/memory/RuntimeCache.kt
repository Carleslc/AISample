package me.carleslc.memory

class RuntimeCache<I, O> : Cache<I, O> {
	
	private val mem: MutableMap<I, O> = HashMap()
	
	override var changes = false
	
	override fun get(input: I): O? {
		return mem[input]
	}

	override fun put(input: I, output: O) {
		mem[input] = output
		changes = true
	}
	
	override fun remove(input: I) {
		mem.remove(input)
		changes = true
	}
	
	override fun items() = mem.entries
	
}
