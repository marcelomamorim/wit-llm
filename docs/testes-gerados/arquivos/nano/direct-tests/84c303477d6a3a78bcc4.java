/*
 * @test
 * @summary Test CheckSignatureAdapter.visitTypeArgument for valid and invalid usage.
 * @run main CheckSignatureAdapterWitupTest
 */

import jdk.internal.org.objectweb.asm.signature.SignatureVisitor;
import jdk.internal.org.objectweb.asm.util.CheckSignatureAdapter;

public class CheckSignatureAdapterWitupTest {

    public static void main(String[] args) {
        testValidVisitTypeArgument();
        testInvalidStateThrows();
        testInvalidWildcardThrows();
    }

    private static void testValidVisitTypeArgument() {
        SignatureVisitor innerVisitor = new SignatureVisitor() {
            @Override
            public SignatureVisitor visitTypeArgument(final char wildcard) {
                System.out.println("Visited type argument with wildcard: " + wildcard);
                return this;
            }
        };
        CheckSignatureAdapter adapter = new CheckSignatureAdapter(CheckSignatureAdapter.TYPE_SIGNATURE, innerVisitor);
        try {
            SignatureVisitor result = adapter.visitTypeArgument('+');
            if (result != null) {
                System.out.println("testValidVisitTypeArgument passed.");
            } else {
                System.out.println("testValidVisitTypeArgument failed: result is null.");
            }
        } catch (Exception e) {
            System.out.println("testValidVisitTypeArgument failed with exception: " + e);
        }
    }

    private static void testInvalidStateThrows() {
        SignatureVisitor innerVisitor = new SignatureVisitor() {
            @Override
            public SignatureVisitor visitTypeArgument(final char wildcard) {
                return this;
            }
        };
        CheckSignatureAdapter adapter = new CheckSignatureAdapter(CheckSignatureAdapter.TYPE_SIGNATURE, innerVisitor);
        // Manually set state to an invalid value to simulate incorrect usage
        try {
            java.lang.reflect.Field stateField = CheckSignatureAdapter.class.getDeclaredField("state");
            stateField.setAccessible(true);
            stateField.setInt(adapter, 99); // invalid state
            adapter.visitTypeArgument('+');
            System.out.println("testInvalidStateThrows failed: no exception thrown.");
        } catch (IllegalStateException e) {
            System.out.println("testInvalidStateThrows passed.");
        } catch (Exception e) {
            System.out.println("testInvalidStateThrows failed with unexpected exception: " + e);
        }
    }

    private static void testInvalidWildcardThrows() {
        SignatureVisitor innerVisitor = new SignatureVisitor() {
            @Override
            public SignatureVisitor visitTypeArgument(final char wildcard) {
                return this;
            }
        };
        CheckSignatureAdapter adapter = new CheckSignatureAdapter(CheckSignatureAdapter.TYPE_SIGNATURE, innerVisitor);
        try {
            adapter.visitTypeArgument('x'); // invalid wildcard
            System.out.println("testInvalidWildcardThrows failed: no exception thrown.");
        } catch (IllegalArgumentException e) {
            System.out.println("testInvalidWildcardThrows passed.");
        } catch (Exception e) {
            System.out.println("testInvalidWildcardThrows failed with unexpected exception: " + e);
        }
    }
}
