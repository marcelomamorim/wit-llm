/* @test    @summary Test for Checks.requireQualifiedClassName method    @run main ChecksWitupTest */

import jdk.internal.module.Checks;

public class ChecksWitupTest {

    public static void main(String[] args) {
        testValidQualifiedName();
        testInvalidQualifiedNameNoDot();
        testNullName();
        testEmptyName();
        testNameWithMultipleDots();
    }

    static void testValidQualifiedName() {
        String name = "com.example.MyClass";
        String result = Checks.requireQualifiedClassName("class name", name);
        if (!result.equals(name)) {
            throw new AssertionError("Expected: " + name + ", but got: " + result);
        }
    }

    static void testInvalidQualifiedNameNoDot() {
        String name = "MyClass";
        try {
            Checks.requireQualifiedClassName("class name", name);
            throw new AssertionError("Expected IllegalArgumentException for name: " + name);
        } catch (IllegalArgumentException e) {
            String expectedMessage = name + ": is not a qualified name of a Java class in a named package";
            if (!e.getMessage().equals(expectedMessage)) {
                throw new AssertionError("Unexpected message: " + e.getMessage());
            }
        }
    }

    static void testNullName() {
        String name = null;
        try {
            Checks.requireQualifiedClassName("class name", name);
            throw new AssertionError("Expected NullPointerException for null name");
        } catch (NullPointerException e) {
            // expected
        } catch (IllegalArgumentException e) {
            throw new AssertionError("Expected NullPointerException, but got: " + e);
        }
    }

    static void testEmptyName() {
        String name = "";
        try {
            Checks.requireQualifiedClassName("class name", name);
            throw new AssertionError("Expected IllegalArgumentException for empty name");
        } catch (IllegalArgumentException e) {
            String expectedMessage = name + ": is not a qualified name of a Java class in a named package";
            if (!e.getMessage().equals(expectedMessage)) {
                throw new AssertionError("Unexpected message: " + e.getMessage());
            }
        }
    }

    static void testNameWithMultipleDots() {
        String name = "com.example.sub.MyClass";
        String result = Checks.requireQualifiedClassName("class name", name);
        if (!result.equals(name)) {
            throw new AssertionError("Expected: " + name + ", but got: " + result);
        }
    }
}
