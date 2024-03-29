package utils;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

import com.android.volley.toolbox.ImageLoader;

public class LruBitmapCache extends LruCache<String, Bitmap> implements ImageLoader.ImageCache {

  public static int getDefaultLruCacheSize() {
    final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);

    return maxMemory / 8;
  }

  public LruBitmapCache() {
    this(getDefaultLruCacheSize());
  }

  public LruBitmapCache(int sizeInKiloBytes) {
    super(sizeInKiloBytes);
  }

  @Override
  protected int sizeOf(String key, Bitmap value) {
    return value.getRowBytes() * value.getHeight() / 1024;
  }

  @Override
  public Bitmap getBitmap(String s) {
    return null;
  }

  @Override
  public void putBitmap(String s, Bitmap bitmap) {

  }
}