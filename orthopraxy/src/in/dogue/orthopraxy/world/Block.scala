package in.dogue.orthopraxy.world

import in.dogue.orthopraxy.data.Code
import in.dogue.orthopraxy.graphics.{Tile, TileRenderer}
import com.deweyvm.gleany.graphics.Color
import scala.util.Random

object Block {
  def create(c:Code) = {
    val isChaos = Random.nextFloat() > 0.89f
    Block(c, c, new Tile(c, Color.Black, Color.White), 0, isChaos)
  }

  def getColor(adj:Int) = adj match {
      case 0 => Color.Black
      case 1 => Color.Red
      case 2 => Color.Orange
      case 3 => Color.Yellow
      case 4 => Color.Green
      case 5 => Color.DarkBlue
      case 6 => Color.Purple
      case 7 => Color.Pink
      case 8 => Color.Brown
  }
}

case class Block(c:Code, d:Code, tile:Tile, adj:Int, isChaos:Boolean) {

  def getDisplayCode:Code = if (isChaos) Code.random else c

  def update(i:Int, j:Int, get:(Int,Int) => Option[Block]):Block = {
    var found = 0
    for (ii <- -1 to 1; jj <- -1 to 1) {
      if (!(ii == 0 && jj == 0)) {
        val n = get(i + ii, j + jj)
        if (n.exists(b=>b.c == c)) {
          found += 1
        }
      }
    }
    val color = Block.getColor(found)
    val chaos = if (found > 0) false else isChaos
    copy(isChaos=chaos, tile=new Tile(getDisplayCode, color, Color.White), adj=found)

  }
  def setBg(c:Color) = {
    copy(tile = tile.copy(bgColor=c))
  }
  def getTile:Tile = tile
}
