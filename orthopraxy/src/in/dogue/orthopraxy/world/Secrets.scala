package in.dogue.orthopraxy.world

import in.dogue.orthopraxy.data.Code

object Secrets {
  val BottomCodeString = "♂█♀◦░█☼█♂♀◦▒"
  val BottomCode = BottomCodeString.map{Code.unicodeToCode}
  val ArrowCode = Vector(
    Code.↑,
    Code.↑,
    Code.↑,
    Code.↓,
    Code.→,
    Code.`←`,
    Code.↑
  )
}
