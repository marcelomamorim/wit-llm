/* @test    @summary Test that Collections.remove() throws UnsupportedOperationException    @run main CollectionsWitupTest */

import java.util.Collections;

public class CollectionsWitupTest {
    public static void main(String[] args) {
        try {
            Collections.remove();
            System.out.println("Test failed: no exception thrown");
            System.exit(1);
        } catch (UnsupportedOperationException e) {
            System.out.println("Test passed: UnsupportedOperationException thrown as expected");
        } catch (Throwable t) {
            System.out.println("Test failed: unexpected exception: " + t);
            System.exit(1);
        }
    }
}
