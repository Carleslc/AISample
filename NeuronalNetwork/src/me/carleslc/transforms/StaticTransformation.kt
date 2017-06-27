package me.carleslc.transforms

class StaticTransformation<I, O>(private val value: O) : Transformation<I, O> {
	
	override operator fun invoke(input: I): O = value
	
}
