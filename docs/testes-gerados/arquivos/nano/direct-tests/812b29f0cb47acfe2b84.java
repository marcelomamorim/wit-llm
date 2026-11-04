/* @test    @summary Test for Collections.remove() method to verify it throws IllegalStateException.    @run main CollectionsWitupTest */

import java.util.Collections;

public class CollectionsWitupTest {
    public static void main(String[] args) {
        try {
            Collections.remove();
            System.out.println("Test failed: no exception thrown.");
        } catch (IllegalStateException e) {
            System.out.println("Test passed: IllegalStateException thrown as expected.");
        } catch (Throwable t) {
            System.out.println("Test failed: unexpected exception: " + t);
        }
    }
}
