/*
 * @test
 * @summary Witup-derived tests for the java.util.jar.Attributes.Name(String) constructor
 * @run main AttributesWitupTest
 */

import java.util.jar.Attributes;

public class AttributesWitupTest {

    public static void main(String[] args) {
        testNullNameThrowsNullPointerException();
        testCaseInsensitiveEqualityAndHash();
        testToStringPreservesOriginalName();
    }

    static void testNullNameThrowsNullPointerException() {
        boolean thrown = false;
        try {
            new Attributes.Name((String) null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected NullPointerException for null name");
        }
    }

    static void testCaseInsensitiveEqualityAndHash() {
        Attributes.Name a = new Attributes.Name("Manifest-Version");
        Attributes.Name b = new Attributes.Name("MANIFEST-VERSION");
        if (!a.equals(b)) {
            throw new RuntimeException("Names differing only in case should be equal");
        }
        if (a.hashCode() != b.hashCode()) {
            throw new RuntimeException("Equal names should have equal hash codes");
        }
    }

    static void testToStringPreservesOriginalName() {
        String original = "Created-By";
        Attributes.Name n = new Attributes.Name(original);
        if (!original.equals(n.toString())) {
            throw new RuntimeException("toString() should return the original name");
        }
    }
}
