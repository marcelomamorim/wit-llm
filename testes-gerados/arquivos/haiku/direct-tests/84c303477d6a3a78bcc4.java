/*
 * @test
 * @summary Checks that CheckSignatureAdapter.visitTypeArgument rejects calls made outside the CLASS_TYPE state
 * @modules java.base/jdk.internal.org.objectweb.asm.util java.base/jdk.internal.org.objectweb.asm.signature
 * @run main CheckSignatureAdapterWitupTest
 */

import jdk.internal.org.objectweb.asm.util.CheckSignatureAdapter;

public class CheckSignatureAdapterWitupTest {

    public static void main(String[] args) {
        expectIllegalState('+');
        expectIllegalState('-');
        expectIllegalState('=');
        expectIllegalState('*');
        expectIllegalState('x');
        expectIllegalStateOnTypeSignatureAdapter('+');
        expectIllegalStateOnTypeSignatureAdapter('*');
    }

    private static void expectIllegalState(char wildcard) {
        CheckSignatureAdapter adapter =
            new CheckSignatureAdapter(CheckSignatureAdapter.TYPE_SIGNATURE, null);
        try {
            adapter.visitTypeArgument(wildcard);
        } catch (IllegalStateException e) {
            return;
        }
        throw new RuntimeException("Expected IllegalStateException for wildcard " + wildcard);
    }

    private static void expectIllegalStateOnTypeSignatureAdapter(char wildcard) {
        CheckSignatureAdapter adapter =
            new CheckSignatureAdapter(CheckSignatureAdapter.CLASS_SIGNATURE, null);
        try {
            adapter.visitTypeArgument(wildcard);
        } catch (IllegalStateException e) {
            return;
        }
        throw new RuntimeException("Expected IllegalStateException for CLASS_SIGNATURE adapter, wildcard " + wildcard);
    }
}
