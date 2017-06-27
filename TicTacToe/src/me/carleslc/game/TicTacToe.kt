package me.carleslc.game

import me.carleslc.extensions.*

enum class Status { PLAYING, WINNER, LOOSER, DRAW }

data class TicTacToe(var player: Player = Player.X,
					 val m: Int = 3, val n: Int = 3, val k: Int = 3,
					 val board: Board = Board.empty(m, n)) {
	
	init {
		if (m == 0 || n == 0 || k == 0) throw IllegalArgumentException("m, n and k cannot be zero")
		if (k > m && k > n) throw IllegalArgumentException("this game always will finish in a draw")
		if (board.array.size != m || board.array.any { it.size != n }) throw IllegalArgumentException("invalid board")
	}
	
	fun status(): Status {
		val rows = board.array.indices
		val columns = board[0].indices
		for (i in rows) {
			for (j in columns) {
				var checking = board[i][j]
				if (checking != null) {
					if (count(checking, i, j) == Status.WINNER) return endScore(checking)
				}
			}
		}
		return if (board.array.anyNull()) Status.PLAYING else Status.DRAW
	}
	
	fun status(m: Move): Status {
		val old = board[m.row][m.column]
		board.move(player, m)
		val status = status()
		board[m.row][m.column] = old
		return status
	}
	
	private fun endScore(winner: Player?): Status = if (winner == player) Status.WINNER else Status.LOOSER
	
	private fun count(current: Player, i: Int, j: Int): Status {
		if (count(current, i, j, 0, 1) >= k        // Right Row
			|| count(current, i, j, 1, 1) >= k     // Bottom Right Diagonal
			|| count(current, i, j, 1, -1) >= k    // Bottom Left Diagonal
			|| count(current, i, j, 1, 0) >= k) {  // Bottom Column
			return Status.WINNER
		}
		return Status.PLAYING
	}
	
	private fun count(player: Player?, startI: Int, startJ: Int, incI: Int, incJ: Int): Int {
		var count = 0
		var i = startI
		var j = startJ
		while (board.insideBounds(i, j) && board[i][j] == player) {
			count++
			i += incI
			j += incJ
		}
		return count
	}
	
	override fun toString(): String {
		val joined = StringBuilder().appendln().append(board).append("Status: ")
		val status = status()
		if (status == Status.PLAYING || status == Status.DRAW) joined.append(status)
		else joined.append("WINNER ${ if (status == Status.WINNER) Player.X else Player.O }")
		return joined.toString()
	}
	
}
