/*
 * @test
 * @summary Collections.emptyListIterator().add must throw UnsupportedOperationException
 * @run main CollectionsWitupTest
 */

import java.util.Collections;
import java.util.ListIterator;

public class CollectionsWitupTest {
    public static void main(String[] args) {
        ListIterator<String> it = Collections.emptyListIterator();
        boolean thrown = false;
        try {
            it.add("x");
        } catch (UnsupportedOperationException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected UnsupportedOperationException from add(E)");
        }
    }
}
