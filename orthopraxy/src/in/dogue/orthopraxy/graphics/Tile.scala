package in.dogue.orthopraxy.graphics

import com.deweyvm.gleany.graphics.Color
import in.dogue.orthopraxy.data.Code

case class Tile(code:Code, bgColor:Color, fgColor:Color) {
  def setBg(c:Color) = copy(bgColor = c)
}
