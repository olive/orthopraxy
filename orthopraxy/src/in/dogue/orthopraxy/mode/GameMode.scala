package in.dogue.orthopraxy.mode

import in.dogue.orthopraxy.graphics.{Text, TileRenderer}
import in.dogue.orthopraxy.world._
import com.deweyvm.gleany.graphics.Color

object GameMode {
  val cols = 16
  val rows = 16


  def create:Mode = {
    GameMode(
      Grid.create(14,14),
      OscBar.create(rows, 14),
      ArrowScroll.create(14),
      ShuffleBar.create(14),
      HeadText.create(cols),
      new Text("7", Color.Black, Color.White),
      new Voice
    )
  }
}

case class GameMode(grid:Grid, oscg:OscBar, nums:ArrowScroll, pbar:ShuffleBar, head:HeadText, blip:Text, voice:Voice) extends Mode {
  import GameMode._

  def update = {
    val oldGrid = grid
    val newGrid = grid.update
    val diff = BoardWatcher(oldGrid, newGrid, newGrid.c, pbar, nums)
    voice.sound(diff)
    val result = copy(grid=newGrid,
         oscg=oscg.update(diff),
         nums=nums.update(diff),
         pbar=pbar.update(diff),
         head=head.update(diff))
    result
  }
  def draw(tr:TileRenderer):TileRenderer = {
    tr.<+<(head.draw(0, 0))
      .<+<(oscg.draw(cols - 1, 1))
      .<+<(nums.draw(0, 1))
      .<+<(pbar.draw(1, rows - 1))
      .<+<(blip.draw(0,rows - 1))
      .move(1,1)
      .<+<(grid.draw)
      .move(-1,-1)

  }
}
