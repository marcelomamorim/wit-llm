/*
 * @test
 * @summary Test for Collections.addAll method to verify exception throwing behavior.
 * @run main CollectionsWitupTest
 */

import java.util.*;

public class CollectionsWitupTest {

    public static void main(String[] args) {
        testAddAllThrowsException();
    }

    public static void testAddAllThrowsException() {
        Collection<Map.Entry<Integer, String>> coll = new ArrayList<>();
        try {
            boolean result = Collections.addAll(coll);
            // Since the method always throws, this line should not be reached.
            System.out.println("Unexpected success: " + result);
        } catch (UnsupportedOperationException e) {
            System.out.println("Caught expected UnsupportedOperationException");
        } catch (Exception e) {
            System.out.println("Caught unexpected exception: " + e);
        }
    }
}
