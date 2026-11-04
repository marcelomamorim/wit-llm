/*
 * @test
 * @summary Verify that addAll on the entry set of an unmodifiable map throws UnsupportedOperationException
 * @run main CollectionsWitupTest
 */

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CollectionsWitupTest {

    public static void main(String[] args) {
        Map<String, Integer> backing = new HashMap<>();
        backing.put("a", 1);
        Map<String, Integer> unmodifiable = Collections.unmodifiableMap(backing);
        Collection<Map.Entry<String, Integer>> entries = unmodifiable.entrySet();

        List<Map.Entry<String, Integer>> nonEmpty = new ArrayList<>();
        nonEmpty.add(new AbstractMap.SimpleEntry<>("b", 2));
        checkThrowsUoe(entries, nonEmpty, "non-empty");

        List<Map.Entry<String, Integer>> empty = new ArrayList<>();
        checkThrowsUoe(entries, empty, "empty");

        if (backing.size() != 1 || backing.get("a") != 1 || backing.containsKey("b")) {
            throw new RuntimeException("Backing map was modified");
        }
    }

    private static void checkThrowsUoe(Collection<Map.Entry<String, Integer>> target,
                                       Collection<Map.Entry<String, Integer>> arg,
                                       String label) {
        boolean threw = false;
        try {
            target.addAll(arg);
        } catch (UnsupportedOperationException e) {
            threw = true;
        }
        if (!threw) {
            throw new RuntimeException("Expected UnsupportedOperationException for " + label + " argument");
        }
    }
}
