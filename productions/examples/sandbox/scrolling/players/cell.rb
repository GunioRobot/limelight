#- Copyright � 2008-2009 8th Light, Inc. All Rights Reserved.
#- Limelight and all included source files are distributed under terms of the GNU LGPL.

on_mouse_clicked do
  puts "mouse clicked"
  p = parent
  parent.remove(self)
end
  