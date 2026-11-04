/*
 * @test
 * @summary Test for visitFieldEnd method in ASMifier class
 * @run main ASMifierWitupTest
 */

import jdk.internal.org.objectweb.asm.util.ASMifier;

public class ASMifierWitupTest {

    public static void main(String[] args) {
        testVisitFieldEnd();
    }

    public static void testVisitFieldEnd() {
        ASMifier asmifier = new ASMifier();
        try {
            // Invoke the method under test
            asmifier.visitFieldEnd();
            // No exception expected, test passes if no exception thrown
        } catch (Exception e) {
            throw new AssertionError("visitFieldEnd() threw an unexpected exception: " + e, e);
        }
    }
}
