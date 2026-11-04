/* @test    @summary Test that Collections.remove() throws UnsupportedOperationException    @run main CollectionsWitupTest */

import java.util.Collections;

public class CollectionsWitupTest {
    public static void main(String[] args) {
        testRemoveMethodThrows();
    }

    public static void testRemoveMethodThrows() {
        try {
            Collections.remove();
            System.out.println("FAIL: Expected UnsupportedOperationException was not thrown.");
        } catch (UnsupportedOperationException e) {
            System.out.println("PASS: Collections.remove() threw UnsupportedOperationException as expected.");
        } catch (Throwable t) {
            System.out.println("FAIL: Unexpected exception type: " + t);
        }
    }
}
