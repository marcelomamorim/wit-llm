/*
 * @test
 * @summary WITUP-derived check: ConcurrentHashMap values().contains(null) must throw NullPointerException, and valid lookups must still work
 * @run main ConcurrentHashMapWitupTest
 */

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentHashMapWitupTest {

    public static void main(String[] args) {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        map.put("a", "1");
        Collection<String> values = map.values();

        if (!values.contains("1")) {
            throw new RuntimeException("expected contains(\"1\") to be true");
        }
        if (values.contains("2")) {
            throw new RuntimeException("expected contains(\"2\") to be false");
        }

        boolean npe = false;
        try {
            values.contains(null);
        } catch (NullPointerException e) {
            npe = true;
        }
        if (!npe) {
            throw new RuntimeException("expected NullPointerException from values().contains(null)");
        }
    }
}
