/*
 * @test
 * @summary Verifies that the empty enumeration returned by Collections.emptyEnumeration() reports no elements and throws NoSuchElementException from nextElement()
 * @run main CollectionsWitupTest
 */

import java.util.Collections;
import java.util.Enumeration;
import java.util.NoSuchElementException;

public class CollectionsWitupTest {

    public static void main(String[] args) {
        Enumeration<String> e = Collections.emptyEnumeration();

        if (e.hasMoreElements()) {
            throw new RuntimeException("hasMoreElements() should be false for empty enumeration");
        }

        checkThrowsNoSuchElement(e);
        checkThrowsNoSuchElement(e);

        if (e.hasMoreElements()) {
            throw new RuntimeException("hasMoreElements() should remain false after nextElement()");
        }
    }

    private static void checkThrowsNoSuchElement(Enumeration<String> e) {
        boolean thrown = false;
        try {
            e.nextElement();
        } catch (NoSuchElementException ex) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("nextElement() should throw NoSuchElementException");
        }
    }
}
