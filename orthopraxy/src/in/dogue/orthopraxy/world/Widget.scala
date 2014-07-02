package in.dogue.orthopraxy.world

import in.dogue.orthopraxy.graphics.TileRenderer

trait Widget {
   def update:Widget
   def draw(i:Int, j:Int)(tr:TileRenderer):TileRenderer
 }
