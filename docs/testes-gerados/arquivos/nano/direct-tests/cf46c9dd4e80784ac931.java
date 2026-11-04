/*
 * @test
 * @summary Test setValue method of ConcurrentHashMap for correct exception throwing
 * @run main ConcurrentHashMapWitupTest
 */

import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentHashMapWitupTest {

    public static void main(String[] args) {
        testSetValueThrowsException();
    }

    public static void testSetValueThrowsException() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        try {
            map.setValue("test");
            // If no exception is thrown, the test should fail
            throw new AssertionError("Expected UnsupportedOperationException was not thrown.");
        } catch (UnsupportedOperationException e) {
            // Test passes if this exception is caught
            System.out.println("setValue correctly throws UnsupportedOperationException.");
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type: " + e, e);
        }
    }
}
