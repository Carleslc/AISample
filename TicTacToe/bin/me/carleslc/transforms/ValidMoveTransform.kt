package me.carleslc.transforms

import me.carleslc.extensions.*
import me.carleslc.game.*

class ValidMoveTransform : Transformation<TicTacToe, Move> {
	
	override operator fun invoke(game: TicTacToe): Move = firstValidMove(game.board)
	
	companion object {
		
		val CANNOT_MOVE = Move(-1,-1)
		
		fun firstValidMove(board: Board, startRow: Int = 0, startColumn: Int = 0): Move {
			with (board) {
				val start = startRow*array.columnSize + startColumn
				var move = firstValidMoveCollapsed(this, start, array.totalSize - 1)
				if (move != CANNOT_MOVE) return move
				move = firstValidMoveCollapsed(this, 0, start - 1)
				if (move != CANNOT_MOVE) return move
				throw RuntimeException("Cannot move")
			}
		}
		
		// Iterates the board as if it was a single-dimension array
		private fun firstValidMoveCollapsed(board: Board, start: Int, end: Int): Move {
			with (board) {
				val colSize = array.columnSize
				for (k in (start .. end)) {
					val testMove = Move(k/colSize, k%colSize)
					if (isValidMove(testMove)) return testMove
				}
			}
			return CANNOT_MOVE
		}
		
	}

}
