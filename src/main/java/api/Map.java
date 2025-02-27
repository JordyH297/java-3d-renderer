package api;

import com.badlogic.gdx.math.Vector2;

import java.awt.image.BufferedImage;

/**
 * Represents a texture map that stores normalized pixel data in float arrays.
 * Supports 1, 2, or 3 components per pixel.
 */
public class Map {
  private static final float NORMALIZATION_FACTOR = 1 / 255f;
  private final float[] data;
  private final int components;
  private final int width, height;
  private final float[] outputBuffer;

  /**
   * Constructs a Map from a BufferedImage and the number of components per pixel.
   *
   * @param image      the BufferedImage to read from
   * @param components the number of components per pixel (1, 2, or 3)
   */
  public Map(BufferedImage image, int components) {
    this(components, image.getWidth(), image.getHeight());
    readFrom(image);
  }

  /**
   * Constructs an empty Map with the specified dimensions and components.
   *
   * @param components the number of components per pixel (1, 2, or 3)
   * @param width      the width of the map
   * @param height     the height of the map
   */
  public Map(int components, int width, int height) {
    if (components < 1 || components > 3) {
      throw new IllegalArgumentException("Map only supports 1, 2, or 3 components");
    }
    this.components = components;
    this.width = width;
    this.height = height;
    this.data = new float[width * height * components];
    this.outputBuffer = new float[components];
  }

  /**
   * Reads normalized pixel data from a BufferedImage.
   * The data is stored in a float array with normalized values between [0..1].
   *
   * @param image the image to read pixel data from
   */
  public void readFrom(BufferedImage image) {
    if (image.getWidth() != this.width || image.getHeight() != this.height) {
      throw new IllegalArgumentException("Image dimensions do not match the map");
    }
    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        int pixelColor = image.getRGB(x, y);
        int pixelIndex = getIndex(x, y);

        for (int i = 0; i < components; i++) {
          int colorComponent = (pixelColor >> (i * 8)) & 0xff;
          data[pixelIndex + components - (i + 1)] = colorComponent * NORMALIZATION_FACTOR;
        }
      }
    }
  }

  /**
   * Gets the normalized color data at the specified UV coordinate.
   *
   * @param surfaceUV the UV coordinate as a Vector2
   * @return an array containing the normalized color data
   */
  public float[] get(Vector2 surfaceUV) {
    return get(surfaceUV, outputBuffer);
  }

  /**
   * Gets the normalized color data at the specified UV coordinate and stores it in the provided output array.
   *
   * @param surfaceUV the UV coordinate as a Vector2
   * @param out       the output array to store the color data
   * @return the output array containing the normalized color data
   */
  public float[] get(Vector2 surfaceUV, float[] out) {
    int x = (int) (width * surfaceUV.x);
    int y = (int) (height * surfaceUV.y);
    int pixelIndex = getIndex(x, y);

    System.arraycopy(data, pixelIndex, out, 0, components);
    return out;
  }

  /**
   * Gets a single normalized value from the map at the specified UV coordinate.
   * This method is only valid for single-component maps.
   *
   * @param surfaceUV the UV coordinate as a Vector2
   * @return the normalized value at the specified UV coordinate
   * @throws IllegalStateException if called on a multi-component map
   */
  public float getSingle(Vector2 surfaceUV) {
    if (components != 1) {
      throw new IllegalStateException("getSingle is only allowed on a single-component map");
    }
    int x = (int) (width * surfaceUV.x);
    int y = (int) (height * surfaceUV.y);
    return data[getIndex(x, y)];
  }

  /**
   * Inverts the color values in the map.
   */
  public void invert() {
    for (int i = 0; i < data.length; i++) {
      data[i] = 1 - data[i];
    }
  }

  /**
   * Gets the index for the data array for the given x and y coordinates.
   *
   * @param x the x-coordinate
   * @param y the y-coordinate
   * @return the calculated index
   */
  private int getIndex(int x, int y) {
    return components * (y * width + x);
  }
}
