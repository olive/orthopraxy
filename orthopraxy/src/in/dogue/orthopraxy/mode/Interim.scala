package in.dogue.orthopraxy.mode

import in.dogue.orthopraxy.graphics.{Tile, TileRenderer}
import in.dogue.orthopraxy.data.Code
import com.deweyvm.gleany.graphics.Color

object Interim {
  def create(cols:Int, rows:Int, dest:Mode, speed:Int) = {
    Interim(cols, rows, dest, speed, 0)
  }
}
case class Interim(cols:Int, rows:Int, dest:Mode, speed:Int, t:Int) extends Mode {
  def update = {
    if (t/speed > rows*2) {
      Transformer(cols, rows, this, dest, speed, 0)
    } else {
      copy(t=t+1)
    }
  }

  def draw(tr:TileRenderer):TileRenderer = {
    tr <++ (for (i <- 0 until cols; j <- 0 until rows) yield {
      (i, j, Tile(Code.random, Color.White, Color.Black))
    })
  }
}
