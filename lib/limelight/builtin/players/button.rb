#- Copyright 2008 8th Light, Inc. All Rights Reserved.
#- Limelight and all included source files are distributed under terms of the GNU LGPL.

module Limelight
  module Builtin
    module Players

      # A Builtin Player that adds the look and behavior of a native button.  It may be applied in the PropBuilder DSL
      # like so:
      #
      #   my_button :players => "button"
      #
      # Props including this Player should implement the button_pressed hook.
      #
      #   def button_pressed(e)
      #     # do something
      #   end
      #
      # Props including this Player may implement any of the key and focus event hooks:
      #
      #   key_pressed, key_typed, key_released, focus_gained, focus_lost
      #
      module Button
        class << self

          def extended(prop) #:nodoc:
            button = Limelight::UI::Model::Inputs::ButtonPanel.new
            prop.panel.add(button)
            prop.button = button.button
          end
        end

        attr_accessor :button #:nodoc:

      end

    end
  end
end