/*
 * @test
 * @summary Test checkClassSignature with valid and invalid signatures
 * @run main CheckClassSignatureTest
 */

import java.io.*;

public class CheckClassSignatureTest {

    public static void main(String[] args) {
        testValidSignature();
        testEmptySignature();
        testInvalidSignature();
    }

    static void testValidSignature() {
        String validSignature = "Lcom/example/MyClass;";
        try {
            jdk.internal.org.objectweb.asm.util.CheckClassAdapter.checkClassSignature(validSignature);
        } catch (Exception e) {
            throw new AssertionError("Valid signature threw exception: " + e);
        }
    }

    static void testEmptySignature() {
        String emptySignature = "";
        try {
            jdk.internal.org.objectweb.asm.util.CheckClassAdapter.checkClassSignature(emptySignature);
            throw new AssertionError("Empty signature did not throw exception");
        } catch (IllegalArgumentException e) {
            // Expected exception
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for empty signature: " + e);
        }
    }

    static void testInvalidSignature() {
        String invalidSignature = "Lcom/example/MyClass";
        try {
            jdk.internal.org.objectweb.asm.util.CheckClassAdapter.checkClassSignature(invalidSignature);
            throw new AssertionError("Invalid signature did not throw exception");
        } catch (IllegalArgumentException e) {
            // Expected exception
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for invalid signature: " + e);
        }
    }
}
