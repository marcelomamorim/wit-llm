/*
 * @test
 * @summary Verify that add(int, E) on a list returned by Collections.unmodifiableList throws UnsupportedOperationException and leaves the backing list unchanged
 * @run main CollectionsWitupTest
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CollectionsWitupTest {
    public static void main(String[] args) {
        List<String> backing = new ArrayList<>(Arrays.asList("a", "b"));
        List<String> unmod = Collections.unmodifiableList(backing);

        expectUnsupported(unmod, 0, "x");
        expectUnsupported(unmod, 1, "y");
        expectUnsupported(unmod, 2, "z");
        expectUnsupported(unmod, -1, "w");

        if (unmod.size() != 2 || !unmod.get(0).equals("a") || !unmod.get(1).equals("b")) {
            throw new RuntimeException("unmodifiable view changed after failed add");
        }
        if (!backing.equals(Arrays.asList("a", "b"))) {
            throw new RuntimeException("backing list changed after failed add");
        }
    }

    static void expectUnsupported(List<String> list, int index, String element) {
        try {
            list.add(index, element);
        } catch (UnsupportedOperationException expected) {
            return;
        }
        throw new RuntimeException("expected UnsupportedOperationException for index " + index);
    }
}
