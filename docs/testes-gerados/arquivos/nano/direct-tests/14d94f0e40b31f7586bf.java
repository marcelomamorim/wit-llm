/*
 * @test
 * @summary Tests for CheckClassAdapter.checkClassSignature method.
 * @run main CheckClassAdapterWitupTest
 */

import java.lang.IllegalArgumentException;

public class CheckClassAdapterWitupTest {

    public static void main(String[] args) {
        testValidSignature();
        testInvalidSignature();
    }

    static void testValidSignature() {
        try {
            jdk.internal.org.objectweb.asm.util.CheckClassAdapter.checkClassSignature("<T:Ljava/lang/Object;>Ljava/lang/Object;");
        } catch (Exception e) {
            throw new AssertionError("Valid signature threw exception", e);
        }
    }

    static void testInvalidSignature() {
        boolean thrown = false;
        try {
            jdk.internal.org.objectweb.asm.util.CheckClassAdapter.checkClassSignature("<T:Ljava/lang/Object;>Ljava/lang/Object;X");
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("Invalid signature did not throw exception");
        }
    }
}
