#- Copyright © 2008-2011 8th Light, Inc. All Rights Reserved.
#- Limelight and all included source files are distributed under terms of the MIT License.

on_mouse_pressed do |e|
  @drag_reference_x = e.x
  @drag_reference_y = e.y
end

on_mouse_dragged do |e|
  dx = e.x - @drag_reference_x
  dy = e.y - @drag_reference_y

  frame = scene.stage.peer.frame
  location = frame.location

  location.x += dx
  location.y += dy
  frame.location = location
end