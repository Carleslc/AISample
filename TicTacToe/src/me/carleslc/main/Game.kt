package me.carleslc.main

import me.carleslc.kotlin.extensions.arrays.*
import me.carleslc.kotlin.extensions.utils.*
import me.carleslc.game.*
import me.carleslc.network.*
import me.carleslc.transforms.ValidMoveTransform
import me.carleslc.memory.RuntimeMemory
import me.carleslc.utils.Execution
import java.util.Random

fun main(args: Array<String>) {
	Network.name = "Tic-Tac-Toe"
	Execution<TicTacToe, Move>(Evaluation.from(::check),
			1000.timesToArrayOf { TicTacToe(board=randomBoard(limitMoves=8)) },
			A[ValidMoveTransform()]).execute(all=false, show=false, callback={
				println(it.filter { justWon(it.key, it.value as Move) })
			})
}

fun check(input: TicTacToe, result: Move): Score {
	return when (input.status(result)) {
		Status.WINNER -> 1.0
		Status.LOOSER -> 0.0
		else -> 0.5
	}
}

fun justWon(game: TicTacToe, move: Move): Boolean {
	return game.status() == Status.PLAYING && game.status(move) == Status.WINNER
}

fun randomBoard(limitMoves: Int = 9): Board {
	val random = Random()
	val board = Board.empty()
    val moves = random.nextInt(1 + limitMoves)
	var player = if (random.nextBoolean()) Player.X else Player.O
	moves.times {
		board.move(player, ValidMoveTransform.firstValidMove(board, random.nextInt(3), random.nextInt(3)))
		player = player.opponent()
	}
	return board
}
