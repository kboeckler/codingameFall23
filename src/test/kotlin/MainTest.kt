import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class MainTest {

    @Test
    fun blipCoords_tl() {
        assertEquals(
            V(500, 500) to 2,
            blipCoords(Drone(1, V(1000, 1000), 0, 0), listOf(Blip(1, 2, RadarDir.TL)))[0]
        )
    }

    @Test
    fun blipCoords_tr() {
        assertEquals(
            V(5500, 500) to 2,
            blipCoords(Drone(1, V(1000, 1000), 0, 0), listOf(Blip(1, 2, RadarDir.TR)))[0]
        )
    }

    @Test
    fun blipCoords_bl() {
        assertEquals(
            V(500, 5500) to 2,
            blipCoords(Drone(1, V(1000, 1000), 0, 0), listOf(Blip(1, 2, RadarDir.BL)))[0]
        )
    }

    @Test
    fun blipCoords_br() {
        assertEquals(
            V(5500, 5500) to 2,
            blipCoords(Drone(1, V(1000, 1000), 0, 0), listOf(Blip(1, 2, RadarDir.BR)))[0]
        )
    }
}
