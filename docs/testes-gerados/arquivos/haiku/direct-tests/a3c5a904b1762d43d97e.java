/*
 * @test
 * @summary Tests for jdk.internal.module.Checks.requireServiceTypeName
 * @modules java.base/jdk.internal.module
 * @run main ChecksWitupTest
 */

import jdk.internal.module.Checks;

public class ChecksWitupTest {

    public static void main(String[] args) throws Exception {
        testValidNameReturnsSameInstance();
        testNullNameThrowsIAE();
        testEmptyNameThrowsIAE();
        testConsecutiveDotsThrowIAE();
    }

    static void testValidNameReturnsSameInstance() {
        String name = "com.example.Service";
        String result = Checks.requireServiceTypeName(name);
        if (result != name) {
            throw new RuntimeException("Expected same instance for valid name");
        }
        if (!"com.example.Service".equals(result)) {
            throw new RuntimeException("Unexpected result: " + result);
        }
    }

    static void testNullNameThrowsIAE() {
        try {
            Checks.requireServiceTypeName(null);
        } catch (IllegalArgumentException expected) {
            return;
        }
        throw new RuntimeException("Expected IllegalArgumentException for null");
    }

    static void testEmptyNameThrowsIAE() {
        try {
            Checks.requireServiceTypeName("");
        } catch (IllegalArgumentException expected) {
            return;
        }
        throw new RuntimeException("Expected IllegalArgumentException for empty name");
    }

    static void testConsecutiveDotsThrowIAE() {
        try {
            Checks.requireServiceTypeName("com..example.Service");
        } catch (IllegalArgumentException expected) {
            return;
        }
        throw new RuntimeException("Expected IllegalArgumentException for consecutive dots");
    }
}
