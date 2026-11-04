/*
 * @test
 * @summary Test CheckSignatureAdapter.visitTypeArgument behavior
 * @run main CheckSignatureAdapterWitupTest
 */

import jdk.internal.org.objectweb.asm.signature.SignatureVisitor;
import jdk.internal.org.objectweb.asm.util.CheckSignatureAdapter;

public class CheckSignatureAdapterWitupTest {

    public static void main(String[] args) {
        testValidWildcards();
        testInvalidWildcard();
        testInvalidState();
    }

    private static void testValidWildcards() {
        // Create a CheckSignatureAdapter with state = CLASS_TYPE (0) and no delegate
        CheckSignatureAdapter csa = new CheckSignatureAdapter(CheckSignatureAdapter.CLASS_SIGNATURE, null);

        // Valid wildcards: '+', '-', '='
        char[] validWildcards = {'+', '-', '='};
        for (char wc : validWildcards) {
            SignatureVisitor sv = csa.visitTypeArgument(wc);
            if (sv == null) {
                throw new AssertionError("visitTypeArgument returned null for wildcard: " + wc);
            }
            // The returned visitor should be a CheckSignatureAdapter with type TYPE_SIGNATURE
            if (!(sv instanceof CheckSignatureAdapter)) {
                throw new AssertionError("Returned visitor is not CheckSignatureAdapter for wildcard: " + wc);
            }
        }
    }

    private static void testInvalidWildcard() {
        CheckSignatureAdapter csa = new CheckSignatureAdapter(CheckSignatureAdapter.CLASS_SIGNATURE, null);
        char invalidWildcard = 'x';
        try {
            csa.visitTypeArgument(invalidWildcard);
            throw new AssertionError("Expected IllegalArgumentException for invalid wildcard");
        } catch (IllegalArgumentException e) {
            // Expected
        }
    }

    private static void testInvalidState() {
        // Create a subclass to set state to a value different from CLASS_TYPE (0)
        CheckSignatureAdapter csa = new CheckSignatureAdapter(CheckSignatureAdapter.CLASS_SIGNATURE, null) {
            {
                // forcibly set state to an invalid value
                try {
                    java.lang.reflect.Field f = CheckSignatureAdapter.class.getDeclaredField("state");
                    f.setAccessible(true);
                    f.setInt(this, 999);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        };

        try {
            csa.visitTypeArgument('+');
            throw new AssertionError("Expected IllegalStateException for invalid state");
        } catch (IllegalStateException e) {
            // Expected
        }
    }
}
