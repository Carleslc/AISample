package me.carleslc.game

import me.carleslc.kotlin.extensions.arrays.*
import me.carleslc.game.Player
import me.carleslc.game.Move
import java.util.Arrays

data class Move(val row: Int, val column: Int)

class Board private constructor(private val board: Matrix<Player?>) {
	
	companion object Factory {
		fun empty(m: Int = 3, n: Int = 3) = Board(matrixOfNulls(m, n))
		
		fun of(vararg rows: Array<Player?>) = Board(Array<Array<Player?>>(rows.size) { rows[it] })
	}
	
	val matrix = board // Alias to replace Board.board with Board.matrix, which is more readable and meaningful
	
	operator fun get(index: Int): Array<Player?> = board[index]
	
	operator fun get(vararg indices: Int): Player? = board[indices[0]][indices[1]]
	
	fun insideBounds(i: Int, j: Int) = i in board.rows && j in board.columns

	fun isValidMove(m: Move): Boolean = insideBounds(m.row, m.column) && board[m.row][m.column] == null
	
	fun move(player: Player, m: Move) {
		board[m.row][m.column] = player
	}
	
	override fun toString(): String {
		val joined = StringBuilder()
		for (i in board.indices) joined.appendln(board[i].joinToString("\t", transform={if (it == null) "." else it.toString()}))
		return joined.toString()
	}
	
	// Needed because if Board was a data class equals would compare array by identity and we need deep structural content equality here
	override fun equals(other: Any?): Boolean {
	    if (this === other) return true
	    if (other?.javaClass != javaClass) return false
	    other as Board
	    if (!(board contentDeepEquals other.board)) return false
	    return true
	}
	
	override fun hashCode() = Arrays.deepHashCode(board)
	
}
