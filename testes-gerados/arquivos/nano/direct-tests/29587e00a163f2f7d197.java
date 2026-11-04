/*
 * @test
 * @summary Test add method of ConcurrentHashMap throws UnsupportedOperationException
 * @run main ConcurrentHashMapWitupTest
 */

import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentHashMapWitupTest {

    public static void main(String[] args) {
        testAddMethodThrowsException();
    }

    public static void testAddMethodThrowsException() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        boolean exceptionThrown = false;
        try {
            map.add("test");
        } catch (UnsupportedOperationException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected UnsupportedOperationException was not thrown.");
        }
    }
}
