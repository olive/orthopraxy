package in.dogue.orthopraxy.world

import in.dogue.orthopraxy.data.Code

case class BoardWatcher(oldGrid:Grid, newGrid:Grid, cursor:Cursor, shuff:ShuffleBar, arr:ArrowScroll) {
  val e = 0 until 9
  val didSwap:Boolean = cursor.swap.length > 0
  val didMove = cursor.moved
  val newCombos:Map[Int,Int] = countCombos(newGrid)
  val oldCombos:Map[Int,Int] = countCombos(oldGrid)
  val newSymbols = countSymbols(newGrid)
  val diffCombos = (e map { case i =>
    (i, newCombos(i) - oldCombos(i))
  }).toMap.withDefaultValue(0)
  val highestChange = {
    val pos = e map { (i:Int) => (i, diffCombos(i)) }
    if (!pos.exists{ case (_, d) => d > 0}) {
      None
    } else {
      Some(pos.maxBy({case (_, d) => d})._1)
    }
  }

  def hasCombo(i:Int) = newCombos.get(i).isDefined
  def hasSymbol(c:Code, i:Int) = newSymbols((c, i)).length >= 1

  val cursorBlock = newGrid.cursorBlock

  val highestCombo = {
    val pos = e map { i => (i, newCombos(i)) } filter {case (_, d) => d > 0}
    if (pos.length == 0) {
      (0, 0)
    } else {
      pos.maxBy(_._1)
    }
  }
  def countCombos(g:Grid):Map[Int,Int] = {
    g.blocks.flatten
      .map { case (_, _, b) => b.adj }
      .groupBy(i => i)
      .mapValues(_.length)
      .withDefaultValue(0)
  }

  def countSymbols(g:Grid): Map[(Code, Int), Seq[Block]] = {
    g.blocks.flatten
      .map { case (_, _, b) => b}
      .groupBy(x => (x.c, x.adj))
      .withDefaultValue(Seq())
  }
}
