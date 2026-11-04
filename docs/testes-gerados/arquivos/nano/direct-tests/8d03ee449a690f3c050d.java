/*
 * @test    @summary Test addAll method of ConcurrentHashMap for unsupported operation exception
 * @run main ConcurrentHashMapWitupTest
 */

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentHashMapWitupTest {

    public static void main(String[] args) {
        testAddAllThrowsException();
    }

    public static void testAddAllThrowsException() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        Collection<String> collection = java.util.Arrays.asList("a", "b", "c");
        boolean threwException = false;
        try {
            map.addAll(collection);
        } catch (UnsupportedOperationException e) {
            threwException = true;
        }
        if (!threwException) {
            throw new AssertionError("addAll did not throw UnsupportedOperationException as expected");
        }
    }
}
