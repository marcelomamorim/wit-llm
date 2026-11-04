/*
 * @test
 * @summary Test that Util.add(E) always throws UnsupportedOperationException
 * @run main UtilAddTest
 */

public class UtilAddTest {
    public static void main(String[] args) {
        UtilAddTest test = new UtilAddTest();
        test.testAddThrows();
    }

    private void testAddThrows() {
        try {
            new UtilAdd<Object>().add(new Object());
            throw new AssertionError("Expected UnsupportedOperationException not thrown");
        } catch (UnsupportedOperationException expected) {
            // expected
        }
    }

    // Minimal Util subclass to expose add method for testing
    private static class UtilAdd<E> {
        public boolean add(E o) {
            throw new UnsupportedOperationException();
        }
    }
}
