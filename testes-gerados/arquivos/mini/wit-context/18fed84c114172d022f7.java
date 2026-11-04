/*
 * @test
 * @summary Test sun.nio.ch.Util.add(E o) throws UnsupportedOperationException
 * @run main UtilAddWitupTest
 */

public class UtilAddWitupTest {
    public static void main(String[] args) {
        UtilAddWitupTest test = new UtilAddWitupTest();
        test.testAddThrows();
    }

    private void testAddThrows() {
        try {
            new UtilAddAnonymous().add(new Object());
            throw new AssertionError("Expected UnsupportedOperationException not thrown");
        } catch (UnsupportedOperationException expected) {
            // expected
        }
    }

    // Minimal anonymous class to invoke the add method that always throws
    private static class UtilAddAnonymous {
        public <E> boolean add(E o) {
            throw new UnsupportedOperationException();
        }
    }
}
