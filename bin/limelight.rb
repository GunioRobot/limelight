require File.expand_path(File.dirname(__FILE__) + "/../ruby_src/lib/init")
require 'limelight/producer'

page_name = ARGV[0]
Limelight::Producer.open(page_name)