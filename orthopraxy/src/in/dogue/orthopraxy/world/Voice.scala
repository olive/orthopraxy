package in.dogue.orthopraxy.world

import scala.concurrent.Lock
import in.dogue.orthopraxy.audio.SoundManager
import com.deweyvm.gleany.audio.Sfx


object Voice {
  val lock = new Lock()
}

class Voice {

  val noChange = SoundManager.gologa
  val sounds = Map(
      0 -> SoundManager.skinta0,
      1 -> SoundManager.skinta1,
      2 -> SoundManager.skinta2,
      3 -> SoundManager.skinta3,
      4 -> SoundManager.skinta4,
      5 -> SoundManager.skinta5,
      6 -> SoundManager.skinta6,
      7 -> SoundManager.skinta7,
      8 -> SoundManager.skinta8
  )

  val all = sounds.values ++ Seq(noChange)

  def play(s:Sfx ) = {
    all.foreach{ m =>
      if (m.isPlaying) {
        m.stop()
      }
    }
    s.play()
  }

  def playTitle() = {
    play(SoundManager.orthopraxy)
  }

  def sound(diff:BoardWatcher) {
    if (diff.didSwap) {
      diff.highestChange match {
        case Some(c) => play(sounds(c))
        case None => play(noChange)
      }
    }
  }
}
