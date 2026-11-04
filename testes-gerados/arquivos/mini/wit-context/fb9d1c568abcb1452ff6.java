/*
 * @test
 * @summary Test java.util.Collections.CheckedCollection constructor null checks
 * @run main CollectionsWitupTest
 */

import java.util.Collection;
import java.util.Collections;
import java.util.Objects;

public class CollectionsWitupTest {
    public static void main(String[] args) {
        testNullCollection();
        testNullType();
        testValidConstruction();
    }

    private static void testNullCollection() {
        try {
            new Collections.CheckedCollection(null, String.class);
            throw new Error("Expected NullPointerException for null collection");
        } catch (NullPointerException e) {
            // expected
        }
    }

    private static void testNullType() {
        try {
            Collection<String> c = java.util.Collections.emptyList();
            new Collections.CheckedCollection(c, null);
            throw new Error("Expected NullPointerException for null type");
        } catch (NullPointerException e) {
            // expected
        }
    }

    private static void testValidConstruction() {
        Collection<String> c = java.util.Collections.emptyList();
        Collections.CheckedCollection<String> cc = new Collections.CheckedCollection<>(c, String.class);
        if (cc == null) {
            throw new Error("CheckedCollection constructor returned null");
        }
    }
}
