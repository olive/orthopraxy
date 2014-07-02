package in.dogue.orthopraxy.mode.title

import in.dogue.orthopraxy.graphics.{Text, TileRenderer}
import in.dogue.orthopraxy.input.Controls
import com.deweyvm.gleany.graphics.Color
import in.dogue.orthopraxy.world.{Secrets, Widget, Voice}
import in.dogue.orthopraxy.mode.{Interim, Transformer, GameMode, Mode}
import in.dogue.orthopraxy.audio.SoundManager


object TitleMode {

  val codes = Vector(
    "OLOTA ATENAN",
    "BATOU CABATO",
    "ENTA   ABOTO",
    Secrets.BottomCodeString
  )

  val buttons = Vector(
    "PRAXIS",
    "GOLOGA",
    "SKINTA",
    "TERETA"
  )

  def create(cols:Int, rows:Int):TitleMode = {
    val text = new Text("ORTHOPRAXY", Color.White, Color.Blue)
    val blink = Button.create(new Text(buttons(0), Color.Black, Color.White))
    val border = Border.create(cols, rows)
    new TitleMode(cols, rows, Seq((0, 0, border), (3, 7, text)), new Voice, 0, 5, 11, 0, blink, 2, 3, None)
  }
}

case class TitleMode(cols:Int, rows:Int, widgets:Seq[(Int,Int, Widget)], voice:Voice, t:Int, buttonI:Int, buttonJ:Int, buttonIndex:Int, button:Button, codeI:Int, codeJ:Int, code:Option[Text]) extends Mode {

  def update = {
    if (t == 15) {
      voice.playTitle()
    }

    val newCode = if (Controls.Pattern.justPressed) {
      code match {
        case None => Some(new Text(TitleMode.codes(buttonIndex), Color.Black, Color.White))
        case Some(_) => None
      }
    } else {
      code
    }

    val newButtonIndex = if (Controls.Up.justPressed) {
      0
    } else if (Controls.Down.justPressed) {
      1
    } else if (Controls.Right.justPressed) {
      2
    } else if (Controls.Left.justPressed) {
      3
    } else {
      buttonIndex
    }

    val newButton = button.setText(TitleMode.buttons(newButtonIndex))

    if (Controls.Swap.justPressed) {
      val speed = 1
      val interim = Interim.create(cols, rows, GameMode.create, speed)
      SoundManager.floop.play()
      Transformer.create(cols, rows, this, interim, speed)
    } else {
      copy(t = t+1,
           widgets = widgets.map {case (i, j, w) => (i, j, w.update)},
           button = newButton.update,
           buttonIndex = newButtonIndex,
           code = newCode)
    }
  }
  private def drawButton = button.draw(buttonI, buttonJ) _

  def draw(tr:TileRenderer):TileRenderer = {
    val optDraw = Seq(code.map(_.draw(codeI, codeJ) _)).flatten
    tr <++< widgets.map{case (i, j, w) => w.draw(i, j) _} <+< drawButton <++< optDraw
  }



}
