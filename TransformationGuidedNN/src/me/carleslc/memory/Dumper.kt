package me.carleslc.memory

import me.carleslc.kotlin.extensions.time.getTimeFrom
import me.carleslc.network.Network
import java.io.File
import java.io.ObjectInputStream
import java.io.FileInputStream
import java.io.EOFException
import java.io.ObjectOutputStream
import java.io.FileOutputStream

internal open class Dumper<I, O>(val cache: Cache<I, O>, val path: String = "") {
	
	companion object {
		private var counter = 0
		
		fun String.fileize() = toLowerCase().replace(Regex("\\s"), "_")
	}
	
	private val dir = File("mem/${ Network.name.fileize() }")
	private val file = File(dir, if (path.isEmpty()) "${ counter++ }" else path)
	
	init {
		if (file.exists()) {
			val start = System.currentTimeMillis()
			val name = file.nameWithoutExtension
			println("Loading Memory $name...")
			try {
				val saved = ObjectInputStream(FileInputStream(file)).readObject() as Cache<I, O>
				saved.items().forEach { cache.put(it.key, it.value) }
				cache.checkpoint()
			}
			catch (endOfFile: EOFException) {}
			println("Loaded $name in ${ getTimeFrom(start) }")
		}
		
		Memory.dumpers.add(this)
	}
	
	open fun hasChanges() = cache.hasChanges()
	
	fun dump() {
		if (hasChanges()) {
			dir.mkdirs()
			ObjectOutputStream(FileOutputStream(file, false)).writeObject(cache)
		}
	}
	
}
