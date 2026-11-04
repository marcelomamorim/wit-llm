/*
 * @test
 * @summary Test ConcurrentHashMap add(V e) throws UnsupportedOperationException
 * @run main ConcurrentHashMapWitupTest
 */

import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentHashMapWitupTest {
    public static void main(String[] args) {
        ConcurrentHashMap<Object, Object> map = new ConcurrentHashMap<>();
        boolean thrown = false;
        try {
            map.add(new Object());
        } catch (UnsupportedOperationException ex) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("Expected UnsupportedOperationException not thrown");
        }
    }
}
