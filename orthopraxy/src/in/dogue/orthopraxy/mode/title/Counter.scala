package in.dogue.orthopraxy.mode.title

import in.dogue.orthopraxy.data.Code
import in.dogue.orthopraxy.world.Secrets

object Counter {
  val sequence = Secrets.ArrowCode

  def create(length:Int) = Counter(length, 0, 0, false, false)

}
case class Counter(length:Int, t:Int, p:Int, prevSolid:Boolean, solid:Boolean) {
  import Counter._
  final val iSpeed = 60
  final val jSpeed = 15

  def update = {
    val prevSolid = solid
    val nowSolid = i == j
    val newP = if (nowSolid && !prevSolid) {
      p + 1
    } else {
      p
    }
    copy(t = t + 1, p = newP, prevSolid = prevSolid, solid=nowSolid)
  }
  def i = (t/iSpeed + 1) % length
  def j = length - ((t/jSpeed) % length) - 1
  def symbol = sequence(p % sequence.length)
 }
