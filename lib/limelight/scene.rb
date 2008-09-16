#- Copyright 2008 8th Light, Inc. All Rights Reserved.
#- Limelight and all included source files are distributed under terms of the GNU LGPL.

require 'limelight/java_util'
require 'limelight/prop'
require 'limelight/button_group_cache'

module Limelight

  # A Scene is a root Prop.  Scenes may be loaded onto a Stage.  In addition to being a Prop object, Scenes have a
  # few extra attributes and behaviors.
  #
  class Scene < Prop
    
    include UI::Api::Scene
  
    attr_reader :button_groups, :styles, :casting_director
    attr_accessor :stage, :loader, :visible, :path, :production
    getters :stage, :loader, :styles
    setters :stage
    event :scene_opened
    
    def initialize(options={})
      super(options)
      @scene = self
      @button_groups = ButtonGroupCache.new
      illuminate
    end
  
    def add_options(options) #:nodoc:
      @options = options
      illuminate
    end

    # Creates the menu bar for the Scene 
    #
    def menu_bar
      return MenuBar.build(self) do
        menu("File") do
          item("Open", :open_chosen_production)
          item("Refresh", :reload)
        end
      end
    end

    # Opens a FileChooser for a new Production.  Loads the chosen Production.
    #
    def open_chosen_production
      options = { :title => "Open New Limelight Production", :description => "Limelight Production", :directory => @directory }
      chosen_file = stage.choose_file(options) { |file| Util.is_limelight_scene?(file) || Util.is_limelight_production?(file) }
      if chosen_file
        @directory = File.dirname(chosen_file)
        open_production(chosen_file)
      end
    end

    # Creates a new Producer to open the specified Production.
    #
    def open_production(production_path)
      producer = Producer.new(production_path, @production.theater)
      producer.open
    end

    # Opens the specified Scene on the Stage currently occupied by this Scene.s
    #
    def load(scene_name)
      @production.producer.open_scene(scene_name, @stage)
    end
    
    private ###############################################
    
    def illuminate
      @styles = @options.has_key?(:styles) ? @options.delete(:styles) : (@styles || {})
      @casting_director = @options.delete(:casting_director) if @options.has_key?(:casting_director)
      super
    end
    
    def reload
      load(@path)
    end
  
  end
end