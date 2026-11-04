/*
 * @test
 * @summary Checks that CheckSignatureAdapter.visitTypeArgument rejects calls made outside the CLASS_TYPE state
 * @modules java.base/jdk.internal.org.objectweb.asm.util
 * @run main CheckSignatureAdapterWitupTest
 */

import jdk.internal.org.objectweb.asm.util.CheckSignatureAdapter;

public class CheckSignatureAdapterWitupTest {

    public static void main(String[] args) {
        testFreshAdapterThrowsIllegalStateException();
    }

    private static void testFreshAdapterThrowsIllegalStateException() {
        CheckSignatureAdapter adapter = new CheckSignatureAdapter(CheckSignatureAdapter.CLASS_SIGNATURE, null);
        boolean thrown = false;
        try {
            adapter.visitTypeArgument('+');
        } catch (IllegalStateException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("Expected IllegalStateException when visitTypeArgument is called outside CLASS_TYPE state");
        }
    }
}
