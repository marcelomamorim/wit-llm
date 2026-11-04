/*
 * @test
 * @summary Test ASMifier.visitArray method behavior
 * @run main ASMifierWitupTest
 */

import jdk.internal.org.objectweb.asm.util.ASMifier;

public class ASMifierWitupTest {

    public static void main(String[] args) {
        ASMifier asmifier = new ASMifier();

        // Test visitArray with a normal string
        ASMifier nested = asmifier.visitArray("testName");
        if (nested == null) {
            throw new AssertionError("visitArray returned null");
        }

        // The returned ASMifier should be a different instance
        if (nested == asmifier) {
            throw new AssertionError("visitArray should return a new ASMifier instance");
        }

        // Test visitArray with null argument
        ASMifier nestedNull = asmifier.visitArray(null);
        if (nestedNull == null) {
            throw new AssertionError("visitArray returned null when called with null");
        }

        // The returned ASMifier should be a different instance
        if (nestedNull == asmifier) {
            throw new AssertionError("visitArray should return a new ASMifier instance even when called with null");
        }
    }
}
