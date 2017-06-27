package me.carleslc.memory

import java.io.Serializable

interface Cache<I, O>: Serializable {
	
	var changes: Boolean
	
	fun get(input: I): O?

	fun put(input: I, output: O)
	
	fun remove(input: I)
	
	fun items(): Set<Map.Entry<I, O>>
	
	fun checkpoint() {
		changes = false
	}
	
	fun hasChanges() = changes
	
}
