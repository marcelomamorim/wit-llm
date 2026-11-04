/* @test    @summary Test for Collections.nextElement() method to ensure it throws NoSuchElementException when called on an empty enumeration.    @run main CollectionsWitupTest */

import java.util.Collections;
import java.util.Enumeration;

public class CollectionsWitupTest {

    public static void main(String[] args) {
        testNextElementThrowsExceptionOnEmpty();
    }

    public static void testNextElementThrowsExceptionOnEmpty() {
        Enumeration<?> enumeration = Collections.emptyEnumeration();
        try {
            enumeration.nextElement();
            System.err.println("Test failed: NoSuchElementException was not thrown.");
            System.exit(1);
        } catch (NoSuchElementException e) {
            // Expected exception, test passes.
            System.out.println("Test passed: NoSuchElementException thrown as expected.");
        } catch (Exception e) {
            System.err.println("Test failed: Unexpected exception: " + e);
            System.exit(1);
        }
    }
}
