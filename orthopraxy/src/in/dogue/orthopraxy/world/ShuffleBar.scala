package in.dogue.orthopraxy.world

import in.dogue.orthopraxy.graphics.{TileRenderer, Tile}
import in.dogue.orthopraxy.data.Code
import scala.util.Random
import com.deweyvm.gleany.graphics.Color
import in.dogue.orthopraxy.audio.SoundManager


object ShuffleBar {
  def create(width:Int):ShuffleBar = ShuffleBar(width, 0, Vector(), 0, false, false)

  val blockCodes = Vector(
    Code.░,
    Code.▒,
    Code.█

  )

  val genderCodes = Vector(
    Code.♀,
    Code.♂,
    Code.◦,
    Code.☼
  )

  val lockCodes = Vector(
    Code.g,
    Code.o,
    Code.l,
    Code.o,
    Code.g,
    Code.a
  )
}


case class ShuffleBar(width:Int, index:Int, text:Vector[Tile], t:Int, rollover:Boolean, locked:Boolean) {
  import ShuffleBar._
  def updateUnlocked(diff:BoardWatcher) = {
    val codes = if (diff.cursorBlock.c == Code.☺) {
      genderCodes
    } else {
      blockCodes
    }
    val tiles = if (t % 60 == 0) {
      SoundManager.flip.play()
      val ts = (index until width) map { i =>
        Tile(codes(Random.nextInt(codes.length)), Color.Black, Color.Red)
      }
      (text.take(index) ++ ts).take(width)
    } else {
      text
    }

    val lockedTiles = tiles.take(index)
    val newLocked = if (!locked && lockedTiles.map{_.code}.containsSlice(Secrets.BottomCode)) {
      true
    } else {
      locked
    }
    val offset = if (diff.didSwap && diff.highestChange.exists(_ >= 2)) {
      SoundManager.chunk.play()
      1
    } else {
      0
    }

    val (newIndex, didRollover) = if ((index + offset) >= tiles.length) {
      SoundManager.fwip.play()
      (0, true)
    } else {
      (index + offset, false)
    }


    copy(text=tiles, index = newIndex, t=t+1, rollover=didRollover, locked=newLocked)
  }

  def update(diff:BoardWatcher):ShuffleBar = {
    if (locked) {
      val tiles = (0 until width) map { i => Tile(lockCodes(Random.nextInt(lockCodes.length)), Color.Black, Color.Cyan)}
      copy(text=tiles.toVector)
    } else {
      updateUnlocked(diff)
    }


  }

  def draw(i:Int, j:Int)(r:TileRenderer):TileRenderer = {
    r <++ text.zipWithIndex.map{case (t, k) => (i + k, j, t)}
  }
}
