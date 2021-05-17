package core

object Main extends App{
    val chamber1 = new Chamber("..R....").animate(2)
    val chamber2 = new Chamber("RR..LRL").animate(3)
    val chamber3 = new Chamber("LRLR.LRLR").animate(2)
    val chamber4 = new Chamber("RLRLRLRLRL").animate(10)
    val chamber5 = new Chamber("...").animate(1)

    printList(chamber1)
    printList(chamber2)
    printList(chamber3)
    printList(chamber4)
    printList(chamber5)

    def printList(l: List[String]): Unit = {
        println("------------------")
        l.foreach(s => println(s))
    }
}
