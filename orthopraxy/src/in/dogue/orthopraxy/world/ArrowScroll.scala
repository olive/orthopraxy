package in.dogue.orthopraxy.world

import in.dogue.orthopraxy.graphics.{TileRenderer, Tile}
import in.dogue.orthopraxy.data.Code
import com.deweyvm.gleany.graphics.Color
import scala.util.Random

object ArrowScroll {
  def create(max:Int):ArrowScroll = {
    ArrowScroll(max, (0 until max).map{ _ => makeTile(defaultCode)}.toVector, false)
  }
  def makeTile(c:Code) = Tile(c, Color.White, Color.Black)

  def defaultCode = Code.`←`
}

case class ArrowScroll(max:Int, nums:Vector[Tile], locked:Boolean) {

  def updateLocked(diff:BoardWatcher) = {
    val codes = Vector(Code.▲, Code.►, Code.▼, Code.◄, Code.♦)
    val tiles = (0 until max) map { i => Tile(codes(Random.nextInt(codes.length)), Color.Black, Color.Purple)}
    copy(nums=tiles.toVector)
  }

  def updateUnlocked(diff:BoardWatcher):ArrowScroll = {
    val code = if (diff.cursorBlock.c == Code.☻) {
      if (diff.didMove) {
        Some(ArrowScroll.defaultCode)
      } else if (diff.didSwap) {
        Some(Code.→)
      } else {
        None
      }
    } else {
      if (diff.didMove) {
        Some(Code.↑)
      } else if (diff.didSwap) {
        Some(Code.↓)
      } else {
        None
      }
    }

    val newLocked = if (!locked && nums.map{_.code}.containsSlice(Secrets.ArrowCode)) {
      true
    } else {
      locked
    }

    val added = code match {
      case None => this
      case Some(c) =>
        val newList = if (nums.length < max || code.isEmpty) {
          nums
        } else {
          nums.drop(1)
        }
        val t = ArrowScroll.makeTile(c)
        copy(nums=newList :+ t)
    }
    added.copy(locked=newLocked)



  }
  def update(diff:BoardWatcher):ArrowScroll = {
    if (locked) {
      updateLocked(diff)
    } else {
      updateUnlocked(diff)
    }

  }

  def draw(i:Int, j:Int)(r:TileRenderer):TileRenderer = {
    r <++ nums.zipWithIndex.map{case (t, k) => (i, j + k, t)}
  }

}
