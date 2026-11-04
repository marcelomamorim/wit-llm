/*
 * @test
 * @summary Test visitExport method of ASMifier for normal and edge cases
 * @run main ASMifierWitupTest
 */

import jdk.internal.org.objectweb.asm.util.ASMifier;

public class ASMifierWitupTest {

    public static void main(String[] args) {
        ASMifier asmifier = new ASMifier();

        // Test with normal package, access and modules
        asmifier.visitExport("java/lang", 1, "moduleA", "moduleB");

        // Test with null modules
        asmifier.visitExport("java/lang", 0);

        // Test with empty modules array
        asmifier.visitExport("java/lang", 0, new String[0]);

        // Test with empty package string
        asmifier.visitExport("", 0, "moduleA");

        // Test with access flags zero
        asmifier.visitExport("java/lang", 0, "moduleA");

        // Test with access flags negative (should be accepted as int)
        asmifier.visitExport("java/lang", -1, "moduleA");

        // Test with single module
        asmifier.visitExport("java/lang", 1, "moduleA");

        // Test with multiple modules
        asmifier.visitExport("java/lang", 1, "moduleA", "moduleB", "moduleC");

        // No exceptions expected, just ensure no runtime errors
        System.out.println("ASMifier.visitExport tests passed.");
    }
}
