import java.util.*
import kotlin.math.abs

const val SIZE = 10_000

fun main(args: Array<String>) {
    val input = Scanner(System.`in`)
    val creatureCount = input.nextInt()
    for (i in 0 until creatureCount) {
        val creatureId = input.nextInt()
        val color = input.nextInt()
        val type = input.nextInt()
    }

    var lightCountByDrone = mutableMapOf<Int, Int>(0 to 3)
    var scannedCreatures = mutableSetOf<Int>()

    while (true) {
        val myDrones = mutableListOf<Drone>()
        val creatures = mutableMapOf<Int, Creature>()
        val creaturesSaved = mutableSetOf<Int>()
        val blips = mutableListOf<Blip>()

        val myScore = input.nextInt()
        val foeScore = input.nextInt()
        val myScanCount = input.nextInt()
        for (i in 0 until myScanCount) {
            val creatureId = input.nextInt()
            creaturesSaved.add(creatureId)
            scannedCreatures.add(creatureId)
        }
        val foeScanCount = input.nextInt()
        for (i in 0 until foeScanCount) {
            val creatureId = input.nextInt()
        }
        val myDroneCount = input.nextInt()
        for (i in 0 until myDroneCount) {
            val droneId = input.nextInt()
            val droneX = input.nextInt()
            val droneY = input.nextInt()
            val emergency = input.nextInt()
            val battery = input.nextInt()
            myDrones.add(Drone(droneId, V(droneX, droneY), emergency, battery))
        }
        val foeDroneCount = input.nextInt()
        for (i in 0 until foeDroneCount) {
            val droneId = input.nextInt()
            val droneX = input.nextInt()
            val droneY = input.nextInt()
            val emergency = input.nextInt()
            val battery = input.nextInt()
        }
        val droneScanCount = input.nextInt()
        for (i in 0 until droneScanCount) {
            val droneId = input.nextInt()
            val creatureId = input.nextInt()
        }
        val visibleCreatureCount = input.nextInt()
        for (i in 0 until visibleCreatureCount) {
            val creatureId = input.nextInt()
            val creatureX = input.nextInt()
            val creatureY = input.nextInt()
            val creatureVx = input.nextInt()
            val creatureVy = input.nextInt()
            creatures[creatureId] = Creature(creatureId, V(creatureX, creatureY), V(creatureVx, creatureVy))
        }
        val radarBlipCount = input.nextInt()
        for (i in 0 until radarBlipCount) {
            val droneId = input.nextInt()
            val creatureId = input.nextInt()
            val radar = input.next()
            blips.add(Blip(droneId, creatureId, RadarDir.valueOf(radar)))
        }
        val state = State(myDrones, creatures, scannedCreatures, lightCountByDrone, blips)
        val result = calculate(state)
        scannedCreatures = result.state.scannedCreatures.toMutableSet()
        lightCountByDrone = result.state.lightCountByDrone.toMutableMap()
        println(result.output.joinToString("\n"))
    }
}

data class State(
    val myDrones: List<Drone>,
    val creatures: Map<Int, Creature>,
    val scannedCreatures: Set<Int>,
    val lightCountByDrone: Map<Int, Int>,
    val blips: List<Blip>
)

data class Result(val output: List<String>, val state: State)

/**
 *  MOVE <x> <y> <light (1|0)> | WAIT <light (1|0)>
 */
private fun calculate(state: State): Result {
    val myLightCountByDrone = state.lightCountByDrone.toMutableMap()
    val myScannedCreates = state.scannedCreatures.toMutableSet()
    val output = mutableListOf<String>()

    state.creatures.values.map { it.id }.forEach { myScannedCreates.add(it) }

    for (i in state.myDrones.indices) {
        val drone = state.myDrones[i]
        val light = myLightCountByDrone[i]!! > 0
        if (light) {
            myLightCountByDrone[i] = myLightCountByDrone[i]!! - 1
        }
        val lightNum = if (light) "1" else "0"
        val creatureToScan = closestBy(
            drone,
            blipCoords(
                drone,
                state.blips
            ).filterNot { myScannedCreates.contains(it.second) }) { it.first }
        if (drone.battery >= 15) {
            myLightCountByDrone[i] = 3
        }
        if (creatureToScan != null) {
            output.add("MOVE ${creatureToScan.first.x} ${creatureToScan.first.y} $lightNum")
        } else {
            output.add("MOVE ${drone.c.x} 0 $lightNum")
        }
    }
    val resultingState = state.copy(lightCountByDrone = myLightCountByDrone, scannedCreatures = myScannedCreates)
    return Result(output, resultingState)
}

fun blipCoords(drone: Drone, blips: List<Blip>): List<Pair<V, Int>> {
    val coordsByDir =
        mapOf(
            RadarDir.TL to V(drone.c.x / 2, drone.c.y / 2),
            RadarDir.TR to V((SIZE - drone.c.x) / 2 + drone.c.x, drone.c.y / 2),
            RadarDir.BL to V(drone.c.x / 2, (SIZE - drone.c.y) / 2 + drone.c.y),
            RadarDir.BR to V((SIZE - drone.c.x) / 2 + drone.c.x, (SIZE - drone.c.y) / 2 + drone.c.y)
        )
    return blips.filter { it.droneId == drone.id }.map { coordsByDir[it.dir]!! to it.creatureId }
}

private fun <T> closestBy(drone: Drone, others: List<T>, selector: (T) -> V): T? {
    if (others.isEmpty()) {
        return null
    }
    return others.minBy {
        dist2(selector(it), drone.c)
    }
}

private fun dist2(one: V, another: V): Int {
    return abs(one.x - another.x) + abs(one.y - another.y)
}

data class V(val x: Int, val y: Int)

data class Drone(val id: Int, val c: V, val emergency: Int, val battery: Int)

data class Creature(val id: Int, val c: V, val v: V)

data class Blip(val droneId: Int, val creatureId: Int, val dir: RadarDir)

enum class RadarDir {
    TL,
    TR,
    BR,
    BL
}