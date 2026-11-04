/*
 * @test
 * @summary Checks that an exhausted iterator over the entry set of an immutable Map.of map throws NoSuchElementException
 * @run main ImmutableCollectionsWitupTest
 */

import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;

public class ImmutableCollectionsWitupTest {
    public static void main(String[] args) {
        Map<String, Integer> m = Map.of("a", 1, "b", 2, "c", 3);
        Iterator<Map.Entry<String, Integer>> it = m.entrySet().iterator();
        Set<String> seen = new HashSet<>();
        int count = 0;
        while (it.hasNext()) {
            Map.Entry<String, Integer> e = it.next();
            seen.add(e.getKey());
            if (!Objects.equals(m.get(e.getKey()), e.getValue())) {
                throw new RuntimeException("entry value mismatch for " + e.getKey());
            }
            count++;
        }
        if (count != 3) {
            throw new RuntimeException("expected 3 entries but got " + count);
        }
        if (!seen.equals(Set.of("a", "b", "c"))) {
            throw new RuntimeException("unexpected keys: " + seen);
        }
        if (it.hasNext()) {
            throw new RuntimeException("hasNext should be false after exhaustion");
        }
        try {
            it.next();
            throw new RuntimeException("next() on exhausted iterator did not throw");
        } catch (NoSuchElementException expected) {
        }

        Iterator<Map.Entry<String, Integer>> it2 = m.entrySet().iterator();
        for (int i = 0; i < 3; i++) {
            it2.next();
        }
        try {
            it2.next();
            throw new RuntimeException("fourth next() on fresh iterator did not throw");
        } catch (NoSuchElementException expected) {
        }
    }
}
