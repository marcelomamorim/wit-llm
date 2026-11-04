/*
 * @test
 * @summary Verify that the enumeration returned by Collections.emptyEnumeration() throws NoSuchElementException from nextElement
 * @run main CollectionsWitupTest
 */

import java.util.Collections;
import java.util.Enumeration;
import java.util.NoSuchElementException;

public class CollectionsWitupTest {

    public static void main(String[] args) {
        Enumeration<String> e = Collections.emptyEnumeration();
        if (e.hasMoreElements()) {
            throw new RuntimeException("emptyEnumeration() must not report more elements");
        }
        for (int i = 0; i < 3; i++) {
            boolean thrown = false;
            try {
                e.nextElement();
            } catch (NoSuchElementException ex) {
                thrown = true;
            }
            if (!thrown) {
                throw new RuntimeException("nextElement() must throw NoSuchElementException on attempt " + i);
            }
        }
        if (e.hasMoreElements()) {
            throw new RuntimeException("emptyEnumeration() must remain empty after failed nextElement() calls");
        }
    }
}
