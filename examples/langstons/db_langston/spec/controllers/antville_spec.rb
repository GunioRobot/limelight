require File.expand_path(File.dirname(__FILE__) + "/../spec_helper")
require File.expand_path(File.dirname(__FILE__) + "/../../controllers/antville")
describe Antville do
  before(:each) do
    def MockAnt
      include antville
    end
    
    @antville = MockAnt.new
  end
  it "should description" do
    
  end
end