import kotlin.math.max
import kotlin.math.min

class XOGame {

    private var board = arrayOf("_", "_", "_", "_", "_", "_", "_", "_", "_")
    private val rowStep = arrayOf(0, 3, 6)
    private val colStep = arrayOf(0, 1, 2)
    private val boardRange = 0.rangeTo(8)
    val huPlayer = "O";
    val aiPlayer = "X";
    val emptyCell = "_"
    private var playerTurn : Boolean = false

    fun resetTheBoard(){
        board = arrayOf("_", "_", "_", "_", "_", "_", "_", "_", "_")
    }

    fun getBoardRange(): IntRange {
        return boardRange
    }
    fun getPlayerTurn():String{
        return if(!playerTurn) "X" else "O"
    }


    fun isMoveLeft(): Boolean {
        for (i in boardRange) {
            if (isValidCell(i)) {
                return true
            }
        }
        return false
    }

    fun isValidCell(i: Int): Boolean {
        return board[i] == emptyCell
    }

    fun evaluatedTheBoard(): Int {

        for (i in rowStep) {
            if (board[i] == board[i + 1] && board[i + 1] == board[i + 2]) {
                if (board[i] == aiPlayer) return 10
                else if (board[i] == huPlayer) return -10
            }
        }

        for (col in colStep) {
            if (board[col] == board[col + 3] && board[col + 3] == board[col + 6])
                if (board[col] == aiPlayer) return 10
                else if (board[col] == huPlayer) return -10
        }
        if (board[0] == board[4] && board[4] == board[8]) {
            if (board[0] == aiPlayer) return 10
            else if (board[0] == huPlayer) return -10
        }

        if (board[2] == board[4] && board[4] == board[6]) {
            if (board[2] == aiPlayer) return 10
            else if (board[2] == huPlayer) return -10
        }
        return 0
    }


    private fun isXWin(): Boolean {
        return evaluatedTheBoard() == 10
    }

    private fun isOWin(): Boolean {
        return evaluatedTheBoard() == -10
    }

    fun getGameState():String {
        if (isXWin()) return "X is the Winner"
        else if (isOWin()) return "O is the Winner"
        if(!isMoveLeft()) return "Tie"
        return  ""
    }

    fun playAt(idx: Int, player: String):Boolean {
        if(player == emptyCell){
            board[idx] = player
        }
        else if(idx in boardRange && isValidCell(idx)) {
            board[idx] = player
            playerTurn = !playerTurn
            return  true
        }
        return false
    }

    fun displayBoard() {
        for (i in boardRange) {
            print(board[i] + " ")
            if ((i + 1) % 3 == 0) print("\n")
        }
    }

}