/*
 * @test
 * @summary Verifies that the ListIterator returned by Collections.unmodifiableList rejects set(E) with UnsupportedOperationException and leaves the backing list unchanged
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
        expectUnsupported(it, "x");

        if (!backing.equals(Arrays.asList("a", "b"))) {
            throw new AssertionError("Backing list changed after rejected set: " + backing);
        }

        ListIterator<String> itAtIndex = unmodifiable.listIterator(1);
        expectUnsupported(itAtIndex, "y");

        if (!unmodifiable.equals(Arrays.asList("a", "b"))) {
            throw new AssertionError("Unmodifiable view changed: " + unmodifiable);
        }
    }

    private static void expectUnsupported(ListIterator<String> it, String value) {
        try {
            it.set(value);
        } catch (UnsupportedOperationException expected) {
            return;
        }
        throw new AssertionError("Expected UnsupportedOperationException from set(" + value + ")");
    }
}
