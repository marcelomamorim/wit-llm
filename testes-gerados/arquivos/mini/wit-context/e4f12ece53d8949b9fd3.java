/*
 * @test
 * @summary Test basic behavior of ASMifier.visitMaxs(int, int) method
 * @run main ASMifierWitupTest
 */

import jdk.internal.org.objectweb.asm.util.ASMifier;

public class ASMifierWitupTest {

    public static void main(String[] args) {
        ASMifier asmifier = new ASMifier();

        // Test with typical values
        asmifier.visitMaxs(5, 10);
        String output = asmifier.getText().toString();
        if (!output.contains(".visitMaxs(5, 10)")) {
            throw new AssertionError("visitMaxs output does not contain expected string");
        }

        // Test with zero values
        asmifier.visitMaxs(0, 0);
        output = asmifier.getText().toString();
        if (!output.contains(".visitMaxs(0, 0)")) {
            throw new AssertionError("visitMaxs output does not contain expected string for zero values");
        }

        // Test with negative values (should not throw, just produce output)
        asmifier.visitMaxs(-1, -1);
        output = asmifier.getText().toString();
        if (!output.contains(".visitMaxs(-1, -1)")) {
            throw new AssertionError("visitMaxs output does not contain expected string for negative values");
        }

        System.out.println("ASMifier.visitMaxs tests passed.");
    }
}
