package me.carleslc.memory

internal class OnlyReadDumper<I, O>(cache: Cache<I, O>) : Dumper<I, O>(cache) {
	
	override fun hasChanges() = false
	
}
