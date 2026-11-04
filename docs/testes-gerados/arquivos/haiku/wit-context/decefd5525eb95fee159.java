/*
 * @test
 * @summary Witup generated test for ASMifier.visitAttribute with a null attribute
 * @modules java.base/jdk.internal.org.objectweb.asm.util
 * @run main ASMifierWitupTest
 */

import jdk.internal.org.objectweb.asm.util.ASMifier;

public class ASMifierWitupTest {
    public static void main(String[] args) {
        ASMifier asmifier = new ASMifier();
        try {
            asmifier.visitAttribute(null);
            throw new AssertionError("expected NullPointerException for null attribute");
        } catch (NullPointerException expected) {
        }
    }
}
