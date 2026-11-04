/*
 * @test
 * @summary Test visitMultiANewArrayInsn method of ASMifier for basic functionality
 * @run main ASMifierWitupTest
 */

import jdk.internal.org.objectweb.asm.util.ASMifier;

public class ASMifierWitupTest {

    public static void main(String[] args) {
        ASMifier asmifier = new ASMifier();

        // Test with normal inputs
        asmifier.visitMultiANewArrayInsn("[I", 3);

        // The method appends a line to the internal text list, but we cannot access it directly.
        // We test that no exception is thrown and method completes normally.

        // Test with empty descriptor and zero dims
        asmifier.visitMultiANewArrayInsn("", 0);

        // Test with null descriptor - should not throw NullPointerException as per current code
        // but since appendConstant likely handles null, we test it anyway
        asmifier.visitMultiANewArrayInsn(null, 1);

        // No exceptions expected, so if we reach here, test passes
        System.out.println("PASS");
    }
}
