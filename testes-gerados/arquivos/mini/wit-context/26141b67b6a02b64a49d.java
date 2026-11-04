/*
 * @test
 * @summary Test Collections.EmptyEnumeration.nextElement() throws NoSuchElementException
 * @run main CollectionsWitupTest
 */

import java.util.Collections;
import java.util.Enumeration;
import java.util.NoSuchElementException;

public class CollectionsWitupTest {
    public static void main(String[] args) {
        Enumeration<?> emptyEnum = Collections.emptyEnumeration();
        try {
            emptyEnum.nextElement();
            throw new AssertionError("Expected NoSuchElementException not thrown");
        } catch (NoSuchElementException e) {
            // Expected exception
        }
    }
}
