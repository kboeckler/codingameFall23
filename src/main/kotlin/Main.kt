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

    while (true) {
        val myDrones = mutableListOf<Drone>()
        val creatures = mutableListOf<Creature>()
        val creaturesScanned = mutableSetOf<Int>()

        val myScore = input.nextInt()
        val foeScore = input.nextInt()
        val myScanCount = input.nextInt()
        for (i in 0 until myScanCount) {
            val creatureId = input.nextInt()
            creaturesScanned.add(creatureId)
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
            val output = closest(
                drone,
                creatures.filterNot { creaturesScanned.contains(it.id) })?.let { "MOVE ${it.c.x} ${it.c.y} 0" }
                ?: "WAIT 0"
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
