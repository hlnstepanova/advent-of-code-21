class Day6 {
    private val initialFish = inputString.split(",").map { LanternFish(it.toInt()) }

    init {
        println("----Day 6----")

        val daysPartI = 80
        val daysPartII = 256

        val partI = calculatePopulation(daysPartI)
        println("Answer I: ${partI.size}")
        println("Answer II: ${partII(partI, daysPartII - daysPartI)}")
    }

    fun calculatePopulation(days: Int): List<LanternFish> {
        var fish = initialFish

        for (day in 1..days) {
            val newFishArray = arrayListOf<LanternFish>()
            fish = fish.map { LanternFish(it.daysLeft - 1) }
            fish.forEach {
                if (it.daysLeft < 0) {
                    val oldFish = LanternFish(6)
                    val newFish = LanternFish(8)
                    newFishArray.add(oldFish)
                    newFishArray.add(newFish)
                } else {
                    newFishArray.add(LanternFish(it.daysLeft))
                }
            }
            fish = newFishArray
        }

        return fish
    }

    fun partII(input: List<LanternFish>, days: Int): Long {
        val map = mutableMapOf<Int, Int>()
        input.forEach {
            val sum = map[it.daysLeft]
            map[it.daysLeft] = (sum ?: 0) + 1
        }
        val daysLeftPossible = arrayListOf<Int>(0, 1, 2, 3, 4, 5, 6, 7, 8)
        var count = 0L
        daysLeftPossible.forEach {
            count += calculateOneFishRecursive(days, it) * (map[it] ?: 0)
        }
        return count
    }

    fun calculateOneFishRecursive(days: Int, daysLeft: Int): Long {
        return reproduce(days, daysLeft)
    }

    fun reproduce(daysLeft: Int, daysLeftUntilNextReproduction: Int): Long {
        val nextReproduction = daysLeft - daysLeftUntilNextReproduction - 1
        if (nextReproduction < 0) return 1
        return reproduce(nextReproduction, 6) + reproduce(nextReproduction, 8)
    }

    data class LanternFish(val daysLeft: Int)

    companion object {
        val inputString =
            """4,3,3,5,4,1,2,1,3,1,1,1,1,1,2,4,1,3,3,1,1,1,1,2,3,1,1,1,4,1,1,2,1,2,2,1,1,1,1,1,5,1,1,2,1,1,1,1,1,1,1,1,1,3,1,1,1,1,1,1,1,1,5,1,4,2,1,1,2,1,3,1,1,2,2,1,1,1,1,1,1,1,1,1,1,4,1,3,2,2,3,1,1,1,4,1,1,1,1,5,1,1,1,5,1,1,3,1,1,2,4,1,1,3,2,4,1,1,1,1,1,5,5,1,1,1,1,1,1,4,1,1,1,3,2,1,1,5,1,1,1,1,1,1,1,5,4,1,5,1,3,4,1,1,1,1,2,1,2,1,1,1,2,2,1,2,3,5,1,1,1,1,3,5,1,1,1,2,1,1,4,1,1,5,1,4,1,2,1,3,1,5,1,4,3,1,3,2,1,1,1,2,2,1,1,1,1,4,5,1,1,1,1,1,3,1,3,4,1,1,4,1,1,3,1,3,1,1,4,5,4,3,2,5,1,1,1,1,1,1,2,1,5,2,5,3,1,1,1,1,1,3,1,1,1,1,5,1,2,1,2,1,1,1,1,2,1,1,1,1,1,1,1,3,3,1,1,5,1,3,5,5,1,1,1,2,1,2,1,5,1,1,1,1,2,1,1,1,2,1""".trimIndent()
    }


}