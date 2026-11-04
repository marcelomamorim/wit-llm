/*
 * @test
 * @summary Test Collections.computeIfAbsent throws UnsupportedOperationException
 * @run main CollectionsWitupTest
 */

import java.util.Collections;
import java.util.function.Function;

public class CollectionsWitupTest {
    public static void main(String[] args) {
        Collections c = new Collections() { };
        try {
            c.computeIfAbsent("key", new Function<String, String>() {
                public String apply(String s) {
                    return "value";
                }
            });
            throw new Error("Expected UnsupportedOperationException");
        } catch (UnsupportedOperationException expected) {
            // Test passed
        }
    }
}
