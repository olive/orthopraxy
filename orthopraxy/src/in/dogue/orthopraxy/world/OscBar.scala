package in.dogue.orthopraxy.world

import in.dogue.orthopraxy.graphics.{Tile, TileRenderer}
import in.dogue.orthopraxy.data.Code
import com.deweyvm.gleany.graphics.Color
import in.dogue.orthopraxy.audio.SoundManager


object OscBar {
  def create(screenHeight:Int, height:Int) = {
    OscBar(screenHeight:Int, height, 360, 0, Color.Green, 1, 1, 0, 0)
  }
}

case class OscBar(screenHeight:Int, barMaxHeight:Int, speed:Float, t:Int, color:Color, prevDir:Int, dir:Int, barHeightValue:Double, prevBarHeight:Double) {


  val barHeight = {
    Math.abs(barMaxHeight*Math.sin(t/speed))
  }

  val bhInt = barHeight.asInstanceOf[Int]

  val x = (0 until bhInt) map { i =>
    val code = if (i == 0) {
      if (dir == 1) Code.▄ else Code.▀
    } else if (i == bhInt - 1 && (barHeight*2).asInstanceOf[Int] % 2 == 0){
      if (dir == 1) Code.▀ else Code.▄
    } else {
      Code.█
    }

    (i, Tile(code, Color.Black, color))
  }

  val y = (0 until screenHeight) map { i =>
    (i, Tile(Code.` `, Color.Black, Color.Black))
  }

  def update(diff:BoardWatcher):OscBar = {
    val (newIndex, num) = diff.highestCombo
    val prevDir = dir
    val newDir = if (List(Code.♪, Code.♫).forall{ s => diff.hasSymbol(s, 7)}) {
      -1
    } else {
      1
    }
    val prevDiff = barHeightValue - prevBarHeight
    val result = copy(t=t+num,
                      color=Block.getColor(newIndex),
                      prevDir=prevDir,
                      dir=newDir,
                      barHeightValue=barHeight,
                      prevBarHeight=barHeightValue)
    val newDiff = result.barHeightValue - result.prevBarHeight
    if (Math.signum(prevDiff) != Math.signum(newDiff) && newDiff < 0 && num > 0) {
      SoundManager.thump.play()
    }
    if (newDir != prevDir) {
      if (newDir == -1) {
        SoundManager.boom.play()
      } else {
        SoundManager.bad.play()
      }
    }
    result
  }

  def draw(i:Int, j:Int)(r:TileRenderer):TileRenderer = {

    def f(k:Int) = {
      if (dir == 1) {
        j + k
      } else {
        screenHeight - (j + k) - 1
      }
    }

    r <++ y.map {case (k, t) => (i, f(k), t)} <++ x.map {case (k, t) => (i, f(k), t)}
  }
}
