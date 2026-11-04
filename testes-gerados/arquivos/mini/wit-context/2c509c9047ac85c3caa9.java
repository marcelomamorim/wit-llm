/*
 * @test
 * @summary Test CopyOnWriteArrayList.set(E) throws UnsupportedOperationException
 * @run main CopyOnWriteArrayListWitupTest
 */

import java.util.concurrent.CopyOnWriteArrayList;

public class CopyOnWriteArrayListWitupTest {
    public static void main(String[] args) {
        CopyOnWriteArrayList<Object> list = new CopyOnWriteArrayList<>();
        boolean thrown = false;
        try {
            list.set(new Object());
        } catch (UnsupportedOperationException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("Expected UnsupportedOperationException not thrown by set(E)");
        }
    }
}
