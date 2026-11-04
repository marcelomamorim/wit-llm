/*
 * @test
 * @summary Test that CopyOnWriteArrayList remove() method throws UnsupportedOperationException
 * @run main CopyOnWriteArrayListWitupTest
 */

public class CopyOnWriteArrayListWitupTest {
    public static void main(String[] args) {
        final java.util.concurrent.CopyOnWriteArrayList<Object> list = new java.util.concurrent.CopyOnWriteArrayList<>();
        java.util.Iterator<Object> it = list.iterator();
        try {
            it.remove();
            throw new Error("Expected UnsupportedOperationException");
        } catch (UnsupportedOperationException expected) {
            // expected
        }
    }
}
