package in.dogue.orthopraxy

import com.deweyvm.gleany.{GleanyInitializer, GleanyConfig, GleanyGame}
import com.deweyvm.gleany.files.PathResolver
import com.deweyvm.gleany.saving.{Settings, SettingDefaults}
import com.deweyvm.gleany.data.Point2i

object Main {
  def main(args: Array[String]) {

    val iconPath = "sprites/icon.gif"
    val settings = new Settings(OrthopraxyControls, new SettingDefaults() {
      val SfxVolume: Float = 0.2f
      val MusicVolume: Float = 0.2f
      val WindowSize: Point2i = Point2i(512,512)
      val DisplayMode: Int = 0
    })
    val config = new GleanyConfig(settings, "orthopraxy", Some(iconPath))
    val pathResolver = new PathResolver(
      "fonts",
      "sprites",
      "sfx",
      "music",
      "shaders",
      "maps"
    )
    val initializer = new GleanyInitializer(pathResolver, settings)
    GleanyGame.runGame(config, new Game(initializer))

  }
}
