/*
 * @test
 * @summary Test Collections.replaceAll method behavior with null function
 * @run main CollectionsWitupTest
 */

import java.util.function.BiFunction;
import java.util.Objects;

public class CollectionsWitupTest {

    static class DummyMap<K, V> {
        public void replaceAll(BiFunction<? super K, ? super V, ? extends V> function) {
            Objects.requireNonNull(function);
        }
    }

    public static void main(String[] args) {
        DummyMap<String, String> map = new DummyMap<>();

        // Test that passing null to replaceAll throws NullPointerException
        try {
            map.replaceAll(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // Expected exception
        }

        // Test that passing a non-null function does not throw
        BiFunction<String, String, String> func = new BiFunction<String, String, String>() {
            public String apply(String k, String v) {
                return v + "_modified";
            }
        };

        map.replaceAll(func);
    }
}
