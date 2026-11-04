/*
 * @test
 * @summary Test java.util.Collections.CheckedCollection constructor null checks
 * @run main CollectionsWitupTest
 */

import java.util.Collections;
import java.util.Collection;
import java.util.ArrayList;
import java.util.Objects;

public class CollectionsWitupTest {
    public static void main(String[] args) {
        testNullCollection();
        testNullType();
        testValidConstruction();
    }

    static void testNullCollection() {
        try {
            new Collections.CheckedCollection(null, String.class);
            throw new Error("Expected NullPointerException for null collection");
        } catch (NullPointerException e) {
            if (!"c".equals(e.getMessage())) {
                throw new Error("NullPointerException message mismatch for null collection");
            }
        }
    }

    static void testNullType() {
        try {
            new Collections.CheckedCollection(new ArrayList<String>(), null);
            throw new Error("Expected NullPointerException for null type");
        } catch (NullPointerException e) {
            if (!"type".equals(e.getMessage())) {
                throw new Error("NullPointerException message mismatch for null type");
            }
        }
    }

    static void testValidConstruction() {
        Collection<String> list = new ArrayList<>();
        Collections.CheckedCollection<String> cc = new Collections.CheckedCollection<>(list, String.class);
        if (cc == null) {
            throw new Error("CheckedCollection instance should not be null");
        }
    }
}
