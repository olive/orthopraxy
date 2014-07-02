package in.dogue.orthopraxy.world

import in.dogue.orthopraxy.graphics.{Tile, Rect, TileRenderer, Text}
import com.deweyvm.gleany.graphics.Color
import in.dogue.orthopraxy.data.Code
import in.dogue.orthopraxy.audio.SoundManager


object HeadText {
  val fg = Color.White
  val bg = Color.Black
  val obtain = new Text("OBTAIN", bg, fg)
  val praxis = new Text("PRAXIS", bg, fg)
  val skinta = new Text("SKINTA", bg, fg)
  val gologa = new Text("GOLOGA", bg, fg)
  def create(cols:Int) = {
    HeadText(cols, obtain,
                   praxis,
                   Rect.create(cols, 1, Tile(Code.` `, Color.Black, Color.Black)))
  }
}

case class HeadText(cols:Int, t1:Text, t2:Text, bg:Rect) {
  import HeadText._

  def updateRight(diff:BoardWatcher) = {
    val has7 = diff.hasCombo(7)
    if (has7 && t2.s != skinta.s) {
      SoundManager.boom.play()
      copy(t2=skinta)
    } else if (!has7 && t2.s != praxis.s) {
      SoundManager.bad.play()
      copy(t2=praxis)
    } else {
      this
    }
  }

  def updateLeft(diff:BoardWatcher) = {
    val hasShuff = diff.shuff.locked
    if (hasShuff && t1.s != gologa.s) {
      SoundManager.boom.play()
      copy(t1=gologa)
    } else {
      this
    }
  }

  def update(diff:BoardWatcher) = {
    updateLeft(diff).updateRight(diff)
  }
  def draw(i:Int, j:Int)(tr:TileRenderer):TileRenderer = {
    tr <+< bg.draw(i, j) <+< t1.draw(i + 1, j) <+< t2.draw(i+cols/2 + 1, j)
  }
}
