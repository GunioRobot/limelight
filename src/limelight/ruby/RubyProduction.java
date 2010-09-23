package limelight.ruby;

import limelight.Context;
import limelight.LimelightException;
import limelight.model.Production;
import limelight.ui.api.ProductionProxy;
import org.jruby.Ruby;
import org.jruby.RubyInstanceConfig;
import org.jruby.javasupport.JavaClass;
import org.jruby.javasupport.JavaEmbedUtils;
import org.jruby.javasupport.JavaSupport;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class RubyProduction extends Production
{
  private static Map<Integer, Ruby> rubies = new HashMap<Integer, Ruby>();
  private static int productionIds = 0;

  public static String newProductionSrc = "" +
    "require 'limelight/limelight_init'\n" +
    "require 'limelight/production'\n" +
    "proxy = Limelight::Production.new(Java::java.lang.Thread.currentThread.production)\n" +
    "Java::java.lang.Thread.currentThread.proxy = proxy\n";

  private Ruby rubyRuntime;
  private int id;

  public RubyProduction(String path)
  {
    super(path);
    id = ++productionIds;
  }

  public void open()
  {
    spawnRubyRuntime();
    getProxy().open();
  }

  @Override
  public void close()
  {
    rubies.remove(id);
    if(rubyRuntime != null)
    {
      new TearDownRubyThread(rubyRuntime, rubies.values()).start();
    }
  }

//  public Object callMethod(String name, Object... args)
//  {
//    return null;
//  }
//
//  public void publish_on_drb(int port)
//  {
//  }

  private void spawnRubyRuntime()
  {
    SpawnRubyThread spawnThread = new SpawnRubyThread(newProductionSrc, id);
    spawnThread.production = this;
    spawnThread.start();

    try
    {
      spawnThread.join();
      if(spawnThread.error != null)
        throw spawnThread.error;
      else if(spawnThread.proxy != null)
      {
        rubyRuntime = spawnThread.ruby;
        rubies.put(id, rubyRuntime);
        setProxy(spawnThread.proxy);
      }
      else
      {
        spawnThread.ruby.tearDown();
        spawnThread = null;
        gc();
      }
    }
    catch(Exception e)
    {
      throw new LimelightException("Failed to start JRuby Runtime", e);
    }
    finally
    {
      if(spawnThread != null)
        spawnThread.clear();
    }
  }

  private void gc()
  {
    new Thread()
    {
      public void run()
      {
        System.gc();
      }
    }.start();
  }

  private static class SpawnRubyThread extends Thread
  {
    public Production production;
    public ProductionProxy proxy;
    private String src;
    private Ruby ruby;
    private Exception error;

    public SpawnRubyThread(String src, int id)
    {
      super("SpawnRuby_" + id);
      this.src = src;
    }

    public void run()
    {
      try
      {
        List<String> loadPaths = new ArrayList<String>();
        loadPaths.add(new File(Context.instance().limelightHome + "/lib").getAbsolutePath());
        RubyInstanceConfig config = new RubyInstanceConfig();
        config.setObjectSpaceEnabled(true);
        ruby = JavaEmbedUtils.initialize(loadPaths, config);
        InputStream input = new ByteArrayInputStream(src.getBytes());
        ruby.runFromMain(input, Context.instance().limelightHome + "/" + getName());
      }
      catch(Exception e)
      {
        error = e;
      }
    }

    public Ruby getRuby()
    {
      return ruby;
    }

    public void clear() //Don't keep any references around so the Ruby instance can be GC'ed.
    {
      ruby = null;
      production = null;
      proxy = null;
      error = null;
    }
  }

  public static class TearDownRubyThread extends Thread
  {
    private Ruby ruby;
    private Collection<Ruby> otherRubies;

    public TearDownRubyThread(Ruby ruby, Collection<Ruby> otherRubies)
    {
      this.ruby = ruby;
      this.otherRubies = otherRubies;
    }

    public void run()
    {
      Thread.yield(); // Allow the call stack to finish with any references belonging to the dying Ruby instance
      ruby.tearDown();
      ruby = null;
      clearProxyReferences(); // To make the Ruby instance GC-able. http://jira.codehaus.org/browse/JRUBY-4165
      System.gc();
    }

    private void clearProxyReferences()
    {
      try
      {
        for(Ruby otherRuby : otherRubies)
        {
          JavaSupport javaSupport = otherRuby.getJavaSupport();
          Field field = javaSupport.getClass().getDeclaredField("javaClassCache");
          field.setAccessible(true);
          ConcurrentHashMap<Class, JavaClass> classCache = (ConcurrentHashMap<Class, JavaClass>) field.get(javaSupport);
          for(Class aClass : classCache.keySet())
          {
            if(aClass.toString().startsWith("class $Proxy"))
              classCache.remove(aClass);
          }
        }
      }
      catch(Exception e)
      {
        e.printStackTrace();
      }
    }
  }
}
