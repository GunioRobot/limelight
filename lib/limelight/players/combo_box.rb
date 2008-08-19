#- Copyright 2008 8th Light, Inc.
#- Limelight and all included source files are distributed under terms of the GNU LGPL.

module Limelight
  module Players
    
    module ComboBox
      class << self
        def extended(prop)
          combo_box = Limelight::UI::Model::Inputs::ComboBoxPanel.new
          prop.panel.add(combo_box)
          prop.combo_box = combo_box.combo_box
        end
      end
      
      attr_accessor :combo_box
      
      def choices=(value)
        value = eval(value) if value.is_a? String
        raise "Invalid choices type: #{value.class}.  Choices have to be an array." if !value.is_a?(Array)
        combo_box.remove_all_items
        value.each { |choice| combo_box.add_item(choice) }
      end
      
      def value=(text)
        self.text = text
      end
      
      def value
        return self.text
      end
      
    end
  
  end
end