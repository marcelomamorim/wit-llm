/*
 * @test
 * @summary Test Collections.nextElement() method throws NoSuchElementException
 * @run main CollectionsWitupTest
 */

import java.util.Collections;
import java.util.NoSuchElementException;

public class CollectionsWitupTest {
    public static void main(String[] args) {
        try {
            Collections.nextElement();
            throw new AssertionError("Expected NoSuchElementException not thrown");
        } catch (NoSuchElementException e) {
            // Expected exception
        }
    }
}
