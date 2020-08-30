package utility

class TrackTime() {
    private var totalTime = 0L
    private var holdTime = 0L
    private val getTime = { System.currentTimeMillis() }

    fun start() {
        holdTime = getTime()
    }

    fun stop() {
        totalTime += getTime() - holdTime
    }

    fun elapsed() = String.format("%1\$tM min. %1\$tS sec. %1\$tL ms.", totalTime)

    fun resetTime() {
        totalTime = 0
    }
}