/*
 * @test
 * @summary Test CopyOnWriteArrayList.add(E) throws UnsupportedOperationException
 * @run main CopyOnWriteArrayListWitupTest
 */

import java.util.concurrent.CopyOnWriteArrayList;

public class CopyOnWriteArrayListWitupTest {
    public static void main(String[] args) {
        CopyOnWriteArrayList<Object> list = new CopyOnWriteArrayList<>();
        try {
            list.add(new Object());
            throw new RuntimeException("Expected UnsupportedOperationException not thrown");
        } catch (UnsupportedOperationException expected) {
            // Expected exception
        }
    }
}
