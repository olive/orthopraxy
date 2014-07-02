package in.dogue.orthopraxy

import com.deweyvm.gleany.saving.{ControlName, ControlNameCollection}

class OrthopraxyControl(descriptor: String) extends ControlName {
  override def name: String = descriptor
}

object OrthopraxyControls extends ControlNameCollection[OrthopraxyControl] {
  def fromString(string: String): Option[OrthopraxyControl] = None
  def makeJoypadDefault: Map[String,String] = Map()
  def makeKeyboardDefault: Map[String,java.lang.Float] = Map()
  def values: Seq[OrthopraxyControl] = Seq()
}
