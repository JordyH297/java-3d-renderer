package api;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

/**
 * A shader interface for calculating pixel colors in a ray-based virtual 3D world.
 *
 * @param <R> The type of runtime context used for storing per-thread variables.
 *            This allows the shader to be used efficiently in multi-threaded rendering.
 */
public interface Shader<R> {

  /**
   * Calculates the color for a pixel on the screen based on its UV coordinates.
   * This default method creates a new Vector3 to store the result.
   *
   * @param screenUV the UV coordinates of the pixel on the screen [0..1]
   * @param runtime  the runtime context for storing per-thread variables
   * @return a Vector3 [0..1] representing the color of the pixel as RGB (no alpha)
   */
  default Vector3 calculateColor(Vector2 screenUV, R runtime) {
    Vector3 color = new Vector3();
    calculateColor(screenUV, color, runtime);
    return color;
  }

  /**
   * Calculates the color for a pixel on the screen based on its UV coordinates.
   * The result is stored in the provided Vector3 object.
   *
   * @param screenUV the UV coordinates of the pixel on the screen [0..1]
   * @param result   the Vector3 to store the calculated color as RGB (no alpha)
   * @param runtime  the runtime context for storing per-thread variables
   */
  void calculateColor(Vector2 screenUV, Vector3 result, R runtime);

  /**
   * Sets the dimensions of the viewport before the rendering process begins.
   * This method should be called whenever the viewport size changes.
   *
   * @param width  the width of the viewport in pixels
   * @param height the height of the viewport in pixels
   */
  void setViewportSize(int width, int height);

  /**
   * Called before every frame is rendered.
   * Can be used for maintaining time or other frame-based calculations (e.g., animations).
   */
  void onFrameStart();

  /**
   * Creates a new runtime context.
   * Each thread should have its own runtime context to ensure thread safety.
   *
   * @return a new instance of the runtime context
   */
  R createRuntime();
}
