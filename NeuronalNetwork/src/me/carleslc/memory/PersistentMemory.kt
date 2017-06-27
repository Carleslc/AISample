package me.carleslc.memory

import me.carleslc.transforms.Transformation

class PersistentMemory<I, O>
	(transformation: Transformation<I, O>, private val path: String = "") : RuntimeMemory<I, O>(transformation) {
	
	private val dumper = Dumper(cache, path)
	
}
