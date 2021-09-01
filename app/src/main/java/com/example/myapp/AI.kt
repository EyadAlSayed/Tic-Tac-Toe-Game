import kotlin.math.max
import kotlin.math.min

class AI(xo: XOGame) {

    private val xoGame: XOGame = xo

    private fun miniMax(player: String): Int {

        val score = xoGame.evaluatedTheBoard()

        if (!xoGame.isMoveLeft()) return 0
        if (score != 0) return score

        var bestVal: Int
        if (player == xoGame.aiPlayer) {
            bestVal = -1000
            for (i in xoGame.getBoardRange()) {
                if (xoGame.isValidCell(i)) {
                    xoGame.playAt(i, xoGame.aiPlayer)
                    bestVal = max(bestVal, miniMax(xoGame.huPlayer))
                    xoGame.playAt(i, xoGame.emptyCell)
                }
            }
        } else {
            bestVal = 1000
            for (i in xoGame.getBoardRange()) {
                if (xoGame.isValidCell(i)) {
                    xoGame.playAt(i, xoGame.huPlayer)
                    bestVal = min(bestVal, miniMax(xoGame.aiPlayer))
                    xoGame.playAt(i, xoGame.emptyCell)
                }
            }
        }
        return bestVal
    }

    private fun getBestChoose(): Int {
        var bestVal = -1000
        var bestChoose: Int = -1

        for (i in xoGame.getBoardRange()) {
            if (xoGame.isValidCell(i)) {
                xoGame.playAt(i, xoGame.aiPlayer)
                val chooseVal = miniMax(xoGame.huPlayer)
                xoGame.playAt(i, xoGame.emptyCell)

                if (chooseVal > bestVal) {
                    bestChoose = i
                    bestVal = chooseVal
                }
            }

        }
        return bestChoose
    }

    fun play(): Int {

        val idx = getBestChoose()
        println("AI Choose : $idx")
        return if(xoGame.playAt(idx, xoGame.aiPlayer)) idx else -1
    }

}