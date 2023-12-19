import java.util.*
import kotlin.math.abs

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
        val creatures = mutableListOf<Creature>()
        val creaturesSaved = mutableSetOf<Int>()

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
            creatures.add(Creature(creatureId, V(creatureX, creatureY), V(creatureVx, creatureVy)))
        }
        val radarBlipCount = input.nextInt()
        for (i in 0 until radarBlipCount) {
            val droneId = input.nextInt()
            val creatureId = input.nextInt()
            val radar = input.next()
        }
        for (i in 0 until myDroneCount) {
            val drone = myDrones[i]!!
            val output: String
            val creatureToScan = closest(
                drone,
                creatures.filterNot { scannedCreatures.contains(it.id) })
            if (drone.battery >= 15) {
                lightCountByDrone[i] = 3
            }
            if (creatureToScan != null) {
                scannedCreatures.add(creatureToScan.id)
                output = "MOVE ${creatureToScan.c.x} ${creatureToScan.c.y} 0"
            } else {
                val light = lightCountByDrone[i]!! > 0
                if (light) {
                    lightCountByDrone[i] = lightCountByDrone[i]!! - 1
                }
                val lightNum = if (light) "1" else "0"
                output = "WAIT $lightNum"
            }
            println(output) // MOVE <x> <y> <light (1|0)> | WAIT <light (1|0)>
        }
    }
}

private fun closest(drone: Drone, creatures: List<Creature>): Creature? {
    return creatures.minByOrNull {
        dist2(it.c, drone.c)
    }
}

private fun dist2(one: V, another: V): Int {
    return abs(one.x - another.x) + abs(one.y - another.y)
}

data class V(val x: Int, val y: Int)

data class Drone(val id: Int, val c: V, val emergency: Int, val battery: Int)

data class Creature(val id: Int, val c: V, val v: V)
