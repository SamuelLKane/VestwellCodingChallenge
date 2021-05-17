package core

class Chamber(val initialConfig: String) {
    var speed = 0

    require((initialConfig.size >= 1 && initialConfig.size <= 50),
    "Provided string is invalid. Strings should be between 1 and 50 characters inclusive")
    require(initialConfig.matches("^[\\.RL]+$"),
        "Provided string is invalid. Strings should only contain the characters '.', 'R', and 'L'")

    /**
     * Takes the provided speed an animates the chamber until it is empty
     * @param speed An integer value between 1 and 10 (inclusive)
     * @return
     */
    def animate(speed: Int): List[String] = {
        // Setting this 'globally' just so I don't have to pass it around
        this.speed = speed

        if(!(speed >= 1 && speed <= 10)) {
            throw new IllegalArgumentException("Speed must be between 1 and 10 (inclusive)");
        }
        // separate lists into two, one of all Rs, one of all Ls. Then make all Ls and Rs into Xs
        val lList = initialConfig.replace("R",".").replace("L","X").split("").toList
        val rList = initialConfig.replace("L",".").replace("R", "X").split("").toList
        return transformForOutput(
            animateRecursiveRight(rList),
            animateRecursiveLeft(lList)
        )
    }

    private def animateStepRight(currState: List[String]): List[String] = {
        return ("." * this.speed).split("").toList ::: currState.splitAt(currState.size - this.speed)._1
    }

    private def animateRecursiveRight(currState: List[String]): List[List[String]] = {
        if(currState.indexOf("X") == -1) {
            return List(currState)
        } else {
            return currState +: animateRecursiveRight(animateStepRight(currState))
        }
    }

    private def animateStepLeft(currState: List[String]): List[String] = {
        return currState.splitAt(this.speed)._2 ::: ("." * this.speed).split("").toList
    }

    private def animateRecursiveLeft(currState: List[String]): List[List[String]] = {
        if(currState.indexOf("X") == -1) {
            return List(currState)
        } else {
            return currState +: animateRecursiveLeft(animateStepLeft(currState))
        }
    }

    private def transformForOutput(rightList: List[List[String]], leftList: List[List[String]]): List[String] = {
        val default = ("." * List(rightList.head.size, leftList.head.size).max).split("").toList
        val i = rightList.zipAll(leftList, default, default)
        val j = i.map(t => t._1 zip t._2 map{ case (a, b) => a + b })
        val k = j.map(l => l.flatMap(s => if (s.contains("X")) "X" else ".").mkString(""))

        return rightList.zipAll(leftList, default, default)
            .map(t => t._1 zip t._2 map{ case (a, b) => a + b })
            .map(l => l.flatMap(s => if (s.contains("X")) "X" else ".").mkString(""))
    }
}
