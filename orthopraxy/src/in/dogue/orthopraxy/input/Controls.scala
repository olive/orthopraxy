package in.dogue.orthopraxy.input

import com.badlogic.gdx.Input
import com.deweyvm.gleany.input.triggers.{KeyboardTrigger, TriggerAggregate}
import scala.collection.mutable.ArrayBuffer
import com.deweyvm.gleany.input.{AxisControl, Control}

object Controls {
  val All = ArrayBuffer[Control[Boolean]]()
  val Left = makeKb(Input.Keys.LEFT)
  val Right = makeKb(Input.Keys.RIGHT)
  val Up = makeKb(Input.Keys.UP)
  val Down = makeKb(Input.Keys.DOWN)
  val Swap = makeKb(Input.Keys.SPACE)
  val Pattern = makeKb(Input.Keys.NUM_7)

  val AxisX = new AxisControl(Left, Right)
  val AxisY = new AxisControl(Up, Down)

  def makeKb(key:Int) = {
    val result = new TriggerAggregate(Seq(new KeyboardTrigger(key)))
    All += result
    result
  }


  def update() {
    All foreach (_.update())
  }
}
