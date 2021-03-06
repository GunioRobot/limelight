#- Copyright © 2008-2011 8th Light, Inc. All Rights Reserved.
#- Limelight and all included source files are distributed under terms of the MIT License.

require File.expand_path(File.dirname(__FILE__) + "/../../../spec_helper")
require 'limelight/scene'
require 'limelight/prop'
require 'limelight/builtin/players'

describe Limelight::Builtin::Players::RadioButton do

  before(:each) do
    @scene = Limelight::Scene.new(:casting_director => mock("caster"))
    @prop = Limelight::Prop.new
    @scene << @prop
    Limelight::Player.cast(Limelight::Builtin::Players::RadioButton, @prop)
  end
  
  it "should have a RadioButton" do
    @prop.peer.children[0].class.should == Java::limelight.ui.model.inputs.RadioButtonPanel
  end
         
  it "should handled checked state" do
    @prop.checked?.should == false
    @prop.checked.should == false
    @prop.selected?.should == false
    @prop.selected.should == false
    
    @prop.selected = true
    
    @prop.checked?.should == true
    @prop.checked.should == true
    @prop.selected?.should == true
    @prop.selected.should == true
  end
  
  it "should belong to a button group" do
    @prop.group = "group 1"
    
    prop2 = Limelight::Prop.new
    @scene << prop2
    prop2.include_player(Limelight::Builtin::Players::RadioButton)
    prop2.group = "group 1"
    
    prop3 = Limelight::Prop.new
    @scene << prop3
    prop3.include_player(Limelight::Builtin::Players::RadioButton)
    prop3.group = "group 2"
    
    group1 = @scene.button_groups.get("group 1")
    group1.buttons.include?(@prop.radio_button).should == true
    group1.buttons.include?(prop2.radio_button).should == true
    group1.buttons.include?(prop3.radio_button).should == false
    
    group2 = @scene.button_groups.get("group 2")
    group2.buttons.include?(@prop.radio_button).should == false
    group2.buttons.include?(prop2.radio_button).should == false
    group2.buttons.include?(prop3.radio_button).should == true
    
    @prop.button_group.should == group1
    prop2.button_group.should == group1
    prop3.button_group.should == group2
  end

end