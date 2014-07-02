package in.dogue.orthopraxy.world

import com.deweyvm.gleany.graphics.Color
import in.dogue.orthopraxy.input.Controls
import com.deweyvm.gleany.GleanyMath
import in.dogue.orthopraxy.audio.SoundManager


object Cursor {
  def create(i:Int, j:Int) = Cursor(i, j, Seq(), false, new Cross(i, j))
}

trait Pattern {
  def getPositions:Seq[(Int,Int)]
  def getSwap:Seq[((Int,Int),(Int,Int))]
  def other(i:Int, j:Int):Pattern
  def self(i:Int, j:Int):Pattern
}

class Cross(i:Int, j:Int) extends Pattern {
  def getPositions = List((i, j),
                          (i - 1, j),
                          (i + 1, j),
                          (i, j - 1),
                          (i, j + 1))
  def getSwap = Seq(((i - 1, j), (i + 1, j)),
                    ((i, j - 1), (i, j + 1)))
  def other(i:Int, j:Int) = new Donut(i,j)
  def self(i:Int, j:Int) = new Cross(i,j)
}

class Donut(i:Int, j:Int) extends Pattern {
  def getPositions = Vector(
    (i-1, j-1),
    (i, j-1),
    (i+1, j-1),
    (i+1, j),
    (i+1, j+1),
    (i, j+1),
    (i-1, j+1),
    (i-1, j))
  def getSwap = {
    val poses = getPositions
    for (k <- 0 until (poses.length - 1)) yield {
      val f0 = poses(k % poses.length)
      val f1 = poses((k + 1) % poses.length)
      (f0, f1)
    }
  }
  def other(i:Int, j:Int) = new Cross(i,j)
  def self(i:Int, j:Int) = new Donut(i,j)
}

case class Cursor(i:Int, j:Int, swap:Seq[((Int,Int), (Int,Int))], moved:Boolean, pat:Pattern) {

  def getPositions = pat.getPositions
  def update(cols:Int, rows:Int):Cursor = {
    val `i'` = i + Controls.AxisX.zip(5, 5)
    val `j'` = j + Controls.AxisY.zip(5, 5)
    val m = `i'` != i || `j'` != j
    val swap = if (Controls.Swap.justPressed) {
      pat.getSwap
    } else {
      Seq()
    }

    val newPat = if (Controls.Pattern.justPressed) {
      SoundManager.donut.play()
      pat.other(i, j)
    } else {
      pat.self(i, j)
    }

    if(m) {
      SoundManager.blip.play()
    }

    if(swap.length > 0) {
      SoundManager.blop.play()

    }

    copy (i=GleanyMath.clamp(`i'`, 0, cols-1),
          j=GleanyMath.clamp(`j'`, 0, rows-1),
          moved=m,
          pat=newPat,
          swap=swap)
  }

  def draw(b:Block):Block = {
    b.setBg(Color.Blue)
  }
}
