/*
 * @test
 * @summary Verifies that removeIf on unmodifiable collection wrappers throws UnsupportedOperationException without mutating the backing collection
 * @run main CollectionsWitupTest
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

public class CollectionsWitupTest {
    public static void main(String[] args) {
        List<String> base = new ArrayList<>(Arrays.asList("a", "bb", "ccc"));
        Collection<String> unmodifiable = Collections.unmodifiableCollection(base);
        int[] calls = new int[1];
        Predicate<String> filter = s -> { calls[0]++; return s.length() > 1; };

        boolean threw = false;
        try {
            unmodifiable.removeIf(filter);
        } catch (UnsupportedOperationException e) {
            threw = true;
        }
        if (!threw) {
            throw new RuntimeException("expected UnsupportedOperationException from unmodifiableCollection.removeIf");
        }
        if (base.size() != 3) {
            throw new RuntimeException("backing collection was modified: size=" + base.size());
        }
        if (!base.equals(Arrays.asList("a", "bb", "ccc"))) {
            throw new RuntimeException("backing collection content changed: " + base);
        }

        List<String> baseList = new ArrayList<>(Arrays.asList("x", "yy"));
        List<String> unmodifiableList = Collections.unmodifiableList(baseList);
        threw = false;
        try {
            unmodifiableList.removeIf(s -> s.isEmpty());
        } catch (UnsupportedOperationException e) {
            threw = true;
        }
        if (!threw) {
            throw new RuntimeException("expected UnsupportedOperationException from unmodifiableList.removeIf");
        }
        if (!baseList.equals(Arrays.asList("x", "yy"))) {
            throw new RuntimeException("backing list was modified: " + baseList);
        }
    }
}
