package me.carleslc.neuron

import java.io.Serializable

abstract class Neuron<T> (open var data: T): Serializable
