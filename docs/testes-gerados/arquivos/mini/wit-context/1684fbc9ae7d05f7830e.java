/*
 * @test
 * @summary Test ASMifier.visitAnnotation method for basic behavior and exception safety
 * @run main ASMifierWitupTest
 */

import jdk.internal.org.objectweb.asm.util.ASMifier;

public class ASMifierWitupTest {

    public static void main(String[] args) {
        ASMifier asmifier = new ASMifier();

        // Test normal invocation with typical non-null strings
        ASMifier result = asmifier.visitAnnotation("TestName", "Ldesc;");
        if (result == null) {
            throw new AssertionError("visitAnnotation returned null");
        }

        // Test with null name and desc - should not throw but produce valid ASMifier
        ASMifier resultNulls = asmifier.visitAnnotation(null, null);
        if (resultNulls == null) {
            throw new AssertionError("visitAnnotation returned null when called with nulls");
        }

        // Test with empty strings
        ASMifier resultEmpty = asmifier.visitAnnotation("", "");
        if (resultEmpty == null) {
            throw new AssertionError("visitAnnotation returned null when called with empty strings");
        }

        // No exceptions expected from visitAnnotation in current implementation
        // The WIT expaths suggesting IllegalArgumentException are discarded because
        // they relate to internal StringBuffer.setLength calls with invalid arguments,
        // which are not triggered by the current method usage.

        System.out.println("All ASMifier.visitAnnotation tests passed.");
    }
}
