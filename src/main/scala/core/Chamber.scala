package core

/**
 * A representation of a single line particle chamber.
 *
 * This class takes an initial configuration of particles who have their direction encoded into them and computes the
 * series of animation frames for that configuration until all particles have left the chamber
 *
 * @param initialConfig a string with a length between 1 and 50 characters (inclusive) and containing only the characters 'L', 'R', and '.'
 */
class Chamber(val initialConfig: String) {
    var speed = 0

    require((initialConfig.size >= 1 && initialConfig.size <= 50),
    "Provided string is invalid. Strings should be between 1 and 50 characters inclusive")
    require(initialConfig.matches("^[\\.RL]+$"),
        "Provided string is invalid. Strings should only contain the characters '.', 'R', and 'L'")

    /**
     * Takes the provided speed an animates the chamber until it is empty
     * @param speed An integer value between 1 and 10 (inclusive)
     * @return a list of compiled animation frames
     */
    def animate(speed: Int): List[String] = {
        if(!(speed >= 1 && speed <= 10)) throw new IllegalArgumentException("Speed must be between 1 and 10 (inclusive)");
        // Setting this 'globally' just so I don't have to pass it around
        this.speed = speed

        // separate lists into two, one of all Rs, one of all Ls. Then make all Ls and Rs into Xs
        val lList = initialConfig.replace("R",".").replace("L","X").split("").toList
        val rList = initialConfig.replace("L",".").replace("R", "X").split("").toList
        return transformForOutput(
            animateRecursiveRight(rList),
            animateRecursiveLeft(lList)
        )
    }

    /**
     * Compute one animation frame to the right
     * @param currState the current state of the animation
     * @return the next frame in the animation (all Xs moved speed positions to the right)
     */
    private def animateStepRight(currState: List[String]): List[String] = {
        return ("." * this.speed).split("").toList ::: currState.splitAt(currState.size - this.speed)._1
    }

    /**
     * Recursively compute all the animation frames for right movements
     * @param currState the current state of the animation
     * @return a list of animation frames for all the right movements
     */
    private def animateRecursiveRight(currState: List[String]): List[List[String]] = {
        if(currState.indexOf("X") == -1) {
            return List(currState)
        } else {
            return currState +: animateRecursiveRight(animateStepRight(currState))
        }
    }

    /**
     * Compute one animation frame to the left
     * @param currState the current state of the animation
     * @return the next frame in the animation (all Xs moved speed positions to the left)
     */
    private def animateStepLeft(currState: List[String]): List[String] = {
        return currState.splitAt(this.speed)._2 ::: ("." * this.speed).split("").toList
    }

    /**
     * Recursively compute all the animation frames for left movements
     * @param currState the current state of the animation
     * @return a list of animation frames for all the right movements
     */
    private def animateRecursiveLeft(currState: List[String]): List[List[String]] = {
        if(currState.indexOf("X") == -1) {
            return List(currState)
        } else {
            return currState +: animateRecursiveLeft(animateStepLeft(currState))
        }
    }

    /**
     * Transform down the left and right animation frames into a single series of animation frames
     * @param rightList a list of animation frames for all the right movements
     * @param leftList a list of animation frames for all the left movements
     * @return a list of compiled animation frames from the left and right movements
     */
    private def transformForOutput(rightList: List[List[String]], leftList: List[List[String]]): List[String] = {
        val default = ("." * List(rightList.head.size, leftList.head.size).max).split("").toList

        return rightList.zipAll(leftList, default, default)
            .map(t => t._1 zip t._2 map{ case (a, b) => a + b })
            .map(l => l.flatMap(s => if (s.contains("X")) "X" else ".").mkString(""))
    }
}
