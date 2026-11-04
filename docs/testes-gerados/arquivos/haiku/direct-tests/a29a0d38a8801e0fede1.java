/*
 * @test
 * @summary Verifies that ListIterator.add on an unmodifiable list wrapper throws UnsupportedOperationException and leaves the backing list unchanged
 * @run main CollectionsWitupTest
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;

public class CollectionsWitupTest {

    public static void main(String[] args) {
        List<String> backing = new ArrayList<>(Arrays.asList("a", "b"));
        List<String> unmodifiable = Collections.unmodifiableList(backing);
        ListIterator<String> it = unmodifiable.listIterator();

        boolean thrown = false;
        try {
            it.add("x");
        } catch (UnsupportedOperationException expected) {
            thrown = true;
        }

        if (!thrown) {
            throw new RuntimeException("Expected UnsupportedOperationException from ListIterator.add");
        }
        if (unmodifiable.size() != 2 || !"a".equals(unmodifiable.get(0)) || !"b".equals(unmodifiable.get(1))) {
            throw new RuntimeException("Unmodifiable list was modified by failed add");
        }
    }
}
