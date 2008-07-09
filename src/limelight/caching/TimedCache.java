package limelight.caching;

public class TimedCache<KEY, VALUE> extends Cache<KEY, VALUE>
{
  private double timeoutSeconds;

  public TimedCache(double timeoutInSeconds)
  {
    timeoutSeconds = timeoutInSeconds;
  }

  protected CacheEntry<VALUE> createEntry(VALUE value)
  {
    return new TimedCacheEntry<VALUE>(value, timeoutSeconds);
  }

  public double getTimeoutSeconds()
  {
    return timeoutSeconds;
  }
}
