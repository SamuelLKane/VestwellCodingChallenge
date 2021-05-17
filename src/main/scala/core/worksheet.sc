var lList = List(
    "..X..X..".split("").toList,
    "X..X....".split("").toList,
    ".X......".split("").toList,
    "........".split("").toList
)

var rList = List(
    "..X..X..".split("").toList,
    "....X..X".split("").toList,
    "......X.".split("").toList,
    "........".split("").toList
)


var default = List("." * rList.head.size)

rList.zipAll(lList,default,default)
    .map(t => t._1 zip t._2 map{ case (a, b) => a + b })
    .map(l => l.flatMap(s => if (s.contains("X")) "X" else ".").mkString(""))

