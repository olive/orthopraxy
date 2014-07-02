package in.dogue.orthopraxy.mode.title

import in.dogue.orthopraxy.graphics.{Tile, TileRenderer, Rect, Text}
import in.dogue.orthopraxy.data.Code
import com.deweyvm.gleany.graphics.Color
import in.dogue.orthopraxy.world.Widget

object Button {
  def create(text:Text) = {
    val rect = Rect.create(text.tiles.length, 1, Tile(Code.` `, Color.Black, Color.Black))
    Button(text, rect, 0)
  }
}

case class Button(text:Text, bg:Rect, t:Int) extends Widget {

   def setText(t:String) = copy(text=text.setString(t))
   def update = {
     copy(t = t+1)
   }

   def draw(i:Int, j:Int)(tr:TileRenderer) = {
     if (t % 30 < 15) {
       text.draw(i, j)(tr)
     } else {
       bg.draw(i, j)(tr)
     }
   }
 }
