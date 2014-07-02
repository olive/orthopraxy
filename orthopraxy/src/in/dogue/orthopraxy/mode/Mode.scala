package in.dogue.orthopraxy.mode

import in.dogue.orthopraxy.graphics.TileRenderer

trait Mode {
  def update:Mode
  def draw(r:TileRenderer):TileRenderer
}
