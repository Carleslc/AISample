package me.carleslc.game

enum class Player {
	X, O;
	
	fun opponent(): Player = if (this == X) O else X
}
