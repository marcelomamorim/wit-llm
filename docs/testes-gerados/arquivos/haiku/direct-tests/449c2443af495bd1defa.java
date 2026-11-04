/*
 * @test
 * @summary Checks.requireModuleName accepts legal names and rejects null or illegal identifiers
 * @modules java.base/jdk.internal.module
 * @run main ChecksWitupTest
 */

import jdk.internal.module.Checks;

public class ChecksWitupTest {

    public static void main(String[] args) {
        testLegalSingleIdentifier();
        testLegalQualifiedName();
        testNullRejected();
        testDigitStartingSegmentRejected();
        testHyphenInSegmentRejected();
    }

    static void testLegalSingleIdentifier() {
        String name = "foo";
        String result = Checks.requireModuleName(name);
        if (!"foo".equals(result)) {
            throw new RuntimeException("Expected 'foo' but got: " + result);
        }
    }

    static void testLegalQualifiedName() {
        String name = "com.example.app";
        String result = Checks.requireModuleName(name);
        if (!name.equals(result)) {
            throw new RuntimeException("Expected same name but got: " + result);
        }
    }

    static void testNullRejected() {
        try {
            Checks.requireModuleName(null);
        } catch (IllegalArgumentException e) {
            if (e.getMessage() == null || !e.getMessage().contains("Null module name")) {
                throw new RuntimeException("Unexpected message: " + e.getMessage());
            }
            return;
        }
        throw new RuntimeException("Expected IllegalArgumentException for null");
    }

    static void testDigitStartingSegmentRejected() {
        try {
            Checks.requireModuleName("a.1b");
        } catch (IllegalArgumentException e) {
            if (e.getMessage() == null || !e.getMessage().contains("is not a Java identifier")) {
                throw new RuntimeException("Unexpected message: " + e.getMessage());
            }
            return;
        }
        throw new RuntimeException("Expected IllegalArgumentException for 'a.1b'");
    }

    static void testHyphenInSegmentRejected() {
        try {
            Checks.requireModuleName("a-b");
        } catch (IllegalArgumentException e) {
            if (e.getMessage() == null || !e.getMessage().contains("is not a Java identifier")) {
                throw new RuntimeException("Unexpected message: " + e.getMessage());
            }
            return;
        }
        throw new RuntimeException("Expected IllegalArgumentException for 'a-b'");
    }
}
