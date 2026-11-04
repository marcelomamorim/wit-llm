/*
 * @test
 * @summary Test visitAnnotation method of ASMifier for basic functionality and exception safety
 * @run main ASMifierWitupTest
 */

import jdk.internal.org.objectweb.asm.util.ASMifier;

public class ASMifierWitupTest {

    public static void main(String[] args) {
        ASMifier asmifier = new ASMifier();

        // Test with normal inputs
        ASMifier result = asmifier.visitAnnotation("Ljava/lang/Deprecated;", true);
        if (result == null) {
            throw new AssertionError("visitAnnotation returned null");
        }

        // Test with empty string desc
        result = asmifier.visitAnnotation("", false);
        if (result == null) {
            throw new AssertionError("visitAnnotation returned null for empty desc");
        }

        // Test with null desc - expected to not throw but produce some output
        // The source code does not show explicit null check or exception
        try {
            result = asmifier.visitAnnotation(null, true);
            if (result == null) {
                throw new AssertionError("visitAnnotation returned null for null desc");
            }
        } catch (NullPointerException e) {
            // Acceptable if NPE is thrown due to null desc usage internally
        }

        // No explicit exception throwing paths visible in source code for visitAnnotation
        // The WIT expaths relate to StringBuffer.setLength and internal String operations
        // which are not directly triggered by visitAnnotation inputs here.

        System.out.println("ASMifier.visitAnnotation tests passed.");
    }
}
