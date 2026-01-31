import com.google.mlkit.vision.pose.PoseLandmark

object Utils {

    @JvmStatic
    fun getAngle(a: PoseLandmark,
                 b: PoseLandmark,
                 c: PoseLandmark): Double {

        val abX = a.position.x - b.position.x
        val abY = a.position.y - b.position.y
        val cbX = c.position.x - b.position.x
        val cbY = c.position.y - b.position.y

        val dot = abX * cbX + abY * cbY
        val cross = abX * cbY - abY * cbX

        return kotlin.math.abs(
            Math.toDegrees(
                kotlin.math.atan2(cross, dot).toDouble()
            )
        )
    }
}

annotation class PoseLandmark
