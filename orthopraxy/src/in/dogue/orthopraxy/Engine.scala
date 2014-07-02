package in.dogue.orthopraxy

import in.dogue.orthopraxy.graphics.{TileRenderer, Renderer, Tileset}
import com.deweyvm.gleany.AssetLoader
import in.dogue.orthopraxy.mode.title.TitleMode
import in.dogue.orthopraxy.mode.Mode

class Engine {
  val ts = Tileset(16, 16, 16, 16, AssetLoader.loadTexture("Md_curses_16x16"))
  val r:Renderer = new Renderer(ts)
  var tr:TileRenderer = TileRenderer.create
  var mode:Mode = TitleMode.create(16, 16)
  def update() {
    mode = mode.update
  }
  def draw() {
    tr = tr <+< mode.draw
    tr = r.render(tr) <-- ()
  }
}
