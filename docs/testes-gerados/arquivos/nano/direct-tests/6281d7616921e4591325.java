/* @test    @summary Test constructor of FieldVisitor with valid parameters    @run main FieldVisitorWitupTest */

import jdk.internal.org.objectweb.asm.FieldVisitor;

public class FieldVisitorWitupTest {

    public static void main(String[] args) {
        testConstructorWithValidParameters();
        testConstructorWithInvalidApi();
    }

    static void testConstructorWithValidParameters() {
        int validApi = 5; // ASM5, a valid value
        FieldVisitor fv = null;
        try {
            FieldVisitor visitor = new FieldVisitor(validApi, fv);
            assert visitor != null : "Constructor returned null";
            assert visitor.api == validApi : "API version mismatch";
            assert visitor.fv == fv : "Delegate FieldVisitor mismatch";
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception in valid parameters test", e);
        }
    }

    static void testConstructorWithInvalidApi() {
        int invalidApi = 10; // Invalid value, should throw IllegalArgumentException
        try {
            new FieldVisitor(invalidApi, null);
            throw new AssertionError("Expected IllegalArgumentException was not thrown");
        } catch (IllegalArgumentException e) {
            // Expected exception
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type", e);
        }
    }
}
