package core

import org.scalatest.flatspec.AnyFlatSpec

class ChamberTests extends AnyFlatSpec{

    // Invalid Configurations
    "A Chamber" should "throw NullPointerException if a null is passed to the constructor" in {
        assertThrows[NullPointerException] {
            new Chamber(null)
        }
    }

    it should "throw IllegalArgumentException if an empty string is passed to the constructor" in {
        assertThrows[IllegalArgumentException] {
            new Chamber("")
        }
    }

    it should "throw IllegalArgumentException if a string longer than 50 characters is passed to the constructor" in {
        assertThrows[IllegalArgumentException] {
            new Chamber("." * 51)
        }
    }

    it should "throw IllegalArgumentException if a string contains characters other than '.','L','R' is passed to the constructor" in {
        assertThrows[IllegalArgumentException] {
            new Chamber("L1R")
        }
    }

    it should "throw IllegalArgumentException speed is less than 1 or greater than 10" in {
        assertThrows[IllegalArgumentException] {
            new Chamber(".........").animate(-1)
        }
        assertThrows[IllegalArgumentException] {
            new Chamber(".........").animate(0)
        }
        assertThrows[IllegalArgumentException] {
            new Chamber(".........").animate(11)
        }
    }

    // Valid Configurations
    it should "animate correctly when provided valid configurations and speeds" in {
        // All '.'
        var chamber = new Chamber("........").animate(1)
        assert(chamber == List("........"))
        // All 'R'
        chamber = new Chamber("RRRRRRRR").animate(1)
        assert(chamber == List(
            "XXXXXXXX",
            ".XXXXXXX",
            "..XXXXXX",
            "...XXXXX",
            "....XXXX",
            ".....XXX",
            "......XX",
            ".......X",
            "........"
        ))
        // All 'L'
        chamber = new Chamber("LLLLLLLL").animate(1)
        assert(chamber == List(
            "XXXXXXXX",
            "XXXXXXX.",
            "XXXXXX..",
            "XXXXX...",
            "XXXX....",
            "XXX.....",
            "XX......",
            "X.......",
            "........"
        ))
        // Mix of 'L' and 'R'
        chamber = new Chamber("LRLRLRLR").animate(1)
        assert(chamber == List(
            "XXXXXXXX",
            ".XXXXXX.",
            "X.XXXX.X",
            ".X.XX.X.",
            "X.X..X.X",
            ".X....X.",
            "X......X",
            "........"
        ))
        // Mix of 'L' and '.'
        chamber = new Chamber("L.L.L.L.").animate(1)
        assert(chamber == List(
            "X.X.X.X.",
            ".X.X.X..",
            "X.X.X...",
            ".X.X....",
            "X.X.....",
            ".X......",
            "X.......",
            "........"
        ))
        // Mix of 'R' and '.'
        chamber = new Chamber("R.R.R.R.").animate(1)
        assert(chamber == List(
            "X.X.X.X.",
            ".X.X.X.X",
            "..X.X.X.",
            "...X.X.X",
            "....X.X.",
            ".....X.X",
            "......X.",
            ".......X",
            "........"
        ))
        // Mix of 'L', 'R', and '.'
        chamber = new Chamber("LR.LR.LR").animate(1)
        assert(chamber == List(
            "XX.XX.XX",
            "..X..X..",
            ".X.XX.X.",
            "X..XX..X",
            "..X..X..",
            ".X....X.",
            "X......X",
            "........"
        ))
        // Non-1 speed
        chamber = new Chamber("R...R...").animate(2)
        assert(chamber == List(
            "X...X...",
            "..X...X.",
            "....X...",
            "......X.",
            "........"
        ))
    }
}
