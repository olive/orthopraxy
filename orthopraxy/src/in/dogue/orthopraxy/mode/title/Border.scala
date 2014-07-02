package in.dogue.orthopraxy.mode.title

import in.dogue.orthopraxy.graphics.{Tile, TileRenderer}
import in.dogue.orthopraxy.data.Code
import com.deweyvm.gleany.graphics.Color
import in.dogue.orthopraxy.world.Widget

object Border {
  val vert      = makeSprite(Code.═)
  val horiz     = makeSprite(Code.║)
  val upRight   = makeSprite(Code.╚)
  val upLeft    = makeSprite(Code.╝)
  val downRight = makeSprite(Code.╔)
  val downLeft  = makeSprite(Code.╗)
  val middle    = makeSprite(Code.` `)
  def makeSprite(c:Code) = Tile(c, Color.Black, Color.White)
  def create(cols:Int, rows:Int) = {
    val top = for (i <- 0 until cols) yield (i, 0)
    val right = for (j <- 1 until rows) yield (cols - 1, j)
    val bottom = (for (i <- 1 until cols - 1) yield (i, rows - 1)).reverse
    val left = (for (j <- 1 until rows ) yield (0, j)).reverse
    val edges = top ++ right ++ bottom ++ left
    Border(cols, rows, Counter.create(edges.length), edges)
  }
}

case class Border(cols:Int, rows:Int, ctr:Counter, edges:Seq[(Int,Int)]) extends Widget{
   import Border._



   def update = copy(ctr = ctr.update)
   def draw(p:Int, q:Int)(tr:TileRenderer):TileRenderer = {
     var k = 0
     val elts = for ((i, j) <- edges) yield {
       val tile = if (i == 0 && j == 0) {
         downRight
       } else if (i == 0 && j == rows - 1 ) {
         upRight
       } else if (i == cols - 1 && j == 0) {
         downLeft
       } else if (i == cols - 1 && j == rows - 1) {
         upLeft
       } else if (i == 0 || i == cols - 1) {
         horiz
       } else if (j == 0 || j == rows - 1) {
         vert
       } else {
         throw new RuntimeException()
       }
       val t = if (k == ctr.i && k == ctr.j) {
         new Tile(ctr.symbol, Color.White, Color.Pink)
       } else if (k == ctr.i || k == ctr.j) {
         val code = if (k % 2 == 0) Code.♪ else Code.♫
         new Tile(code, Color.White, Color.Black)
       } else {
         tile
       }
       k += 1
       (i + p, j + q, t)

     }

     tr <++ elts
   }
 }
