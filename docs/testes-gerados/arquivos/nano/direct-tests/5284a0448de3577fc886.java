/*
 * @test
 * @summary Test pollFirst method of Collections class for UnsupportedOperationException
 * @run main CollectionsWitupTest
 */

import java.util.Collections;

public class CollectionsWitupTest {
    public static void main(String[] args) {
        testPollFirstThrows();
    }

    public static void testPollFirstThrows() {
        try {
            Collections.pollFirst();
            System.out.println("Test failed: Expected UnsupportedOperationException was not thrown.");
        } catch (UnsupportedOperationException e) {
            System.out.println("Test passed: UnsupportedOperationException was correctly thrown.");
        } catch (Throwable t) {
            System.out.println("Test failed: Unexpected exception type: " + t);
        }
    }
}
