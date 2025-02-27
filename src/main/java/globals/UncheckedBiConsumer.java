package globals;

import java.util.function.BiConsumer;

@FunctionalInterface
public interface UncheckedBiConsumer<T, U, E extends Exception> {

  /**
   * Wraps a renderer.UncheckedBiConsumer and returns a BiConsumer that throws a RuntimeException
   * if an exception occurs.
   *
   * @param consumer the renderer.UncheckedBiConsumer to wrap
   * @return a BiConsumer that throws a RuntimeException on exception
   */
  static <T, U, E extends Exception> BiConsumer<T, U> unchecked(UncheckedBiConsumer<T, U, E> consumer) {
    return (t, u) -> {
      try {
        consumer.accept(t, u);
      } catch (Exception ex) {
        throw new RuntimeException("Exception occurred while executing BiConsumer", ex);
      }
    };
  }

  /**
   * Performs this operation on the given arguments.
   *
   * @param t the first input argument
   * @param u the second input argument
   * @throws E if an exception of type E occurs
   */
  void accept(T t, U u) throws E;
}
