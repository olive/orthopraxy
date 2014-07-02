package in.dogue.orthopraxy.graphics

import in.dogue.orthopraxy.data.Code
import com.deweyvm.gleany.graphics.Color
import in.dogue.orthopraxy.world.Widget

case class Text(s:String, bgColor:Color, fgColor:Color) extends Widget {
  val codes = s.map(Code.unicodeToCode)
  val tiles = codes.map{c => Tile(c, bgColor, fgColor)}

  def withFg(color:Color) = new Text(s, bgColor, color)
  def setString(x:String) = Text(x, bgColor, fgColor)
  def update = this
  def draw(i:Int, j:Int)(r:TileRenderer):TileRenderer = {
    r <++ tiles.zipWithIndex.map{case (t, k) => (i + k, j, t)}
  }
}
