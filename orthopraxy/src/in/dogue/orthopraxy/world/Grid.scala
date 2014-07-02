package in.dogue.orthopraxy.world

import in.dogue.orthopraxy.graphics.{Tile, TileRenderer}
import in.dogue.orthopraxy.data.{Code, Array2d}
import com.deweyvm.gleany.graphics.Color

object Grid {
  def create(cols:Int, rows:Int):Grid = {
    Grid(cols, rows, Cursor.create(0,0), Array2d.tabulate(cols, rows) {
      (i,j) => Block.create(Code.randomInt(15))
    }, false)
  }
}

case class Grid(cols:Int, rows:Int, c:Cursor, blocks:Array2d[Block], swapped:Boolean) {
  def update:Grid = {
    val cc = c.update(cols, rows)
    val didSwap = cc.swap.length > 0
    val nb = cc.swap.foldLeft(blocks) {case (acc, ((i, j), (p, q))) => acc.swap(i, j, p, q) }
    val ub = nb.map{case (i, j, b) => b.update(i, j, nb.getOption)}
    val cb = cc.getPositions.foldLeft(ub) { case (acc, (i, j)) => acc.update(i, j, cc.draw)}
    copy(c = cc, blocks = cb, swapped=didSwap)
  }

  def cursorBlock = blocks.get(c.i, c.j)

  def draw(r:TileRenderer):TileRenderer = {
    r <++ blocks.map({case (i, j, b) => b.getTile}).flatten
  }

}
