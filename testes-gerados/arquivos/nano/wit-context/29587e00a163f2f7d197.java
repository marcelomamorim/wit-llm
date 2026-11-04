/*
 * @test
 * @summary Test for ConcurrentHashMap.add method to verify exception throwing behavior.
 * @run main ConcurrentHashMapWitupTest
 */

import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentHashMapWitupTest {

    public static void main(String[] args) {
        testAddThrowsException();
    }

    public static void testAddThrowsException() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        try {
            boolean result = map.add("key");
            // Since add() always throws UnsupportedOperationException, this line should not be reached.
            System.err.println("Error: add() did not throw exception as expected.");
        } catch (UnsupportedOperationException e) {
            System.out.println("Passed: add() threw UnsupportedOperationException as expected.");
        } catch (Exception e) {
            System.err.println("Failed: add() threw unexpected exception: " + e);
        }
    }
}
