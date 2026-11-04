/*
 * @test
 * @summary Test for CheckSignatureAdapter.visitTypeArgument method behavior.
 * @run main CheckSignatureAdapterWitupTest
 */

import jdk.internal.org.objectweb.asm.signature.SignatureVisitor;
import jdk.internal.org.objectweb.asm.util.CheckSignatureAdapter;

public class CheckSignatureAdapterWitupTest {

    public static void main(String[] args) {
        testVisitTypeArgumentWithValidWildcard();
        testVisitTypeArgumentWithInvalidWildcard();
        testVisitTypeArgumentWithNullSignatureVisitor();
    }

    private static void testVisitTypeArgumentWithValidWildcard() {
        try {
            SignatureVisitor mockVisitor = new SignatureVisitor() {
                @Override
                public SignatureVisitor visitTypeArgument(char wildcard) {
                    System.out.println("visitTypeArgument called with: " + wildcard);
                    return this;
                }
            };
            CheckSignatureAdapter adapter = new CheckSignatureAdapter(CheckSignatureAdapter.TYPE_SIGNATURE, mockVisitor);
            SignatureVisitor result = adapter.visitTypeArgument('+');
            if (result != null) {
                System.out.println("testVisitTypeArgumentWithValidWildcard passed.");
            } else {
                System.out.println("testVisitTypeArgumentWithValidWildcard failed: result is null.");
            }
        } catch (Exception e) {
            System.out.println("testVisitTypeArgumentWithValidWildcard failed with exception: " + e);
        }
    }

    private static void testVisitTypeArgumentWithInvalidWildcard() {
        try {
            SignatureVisitor mockVisitor = new SignatureVisitor() {
                @Override
                public SignatureVisitor visitTypeArgument(char wildcard) {
                    System.out.println("visitTypeArgument called with: " + wildcard);
                    return this;
                }
            };
            CheckSignatureAdapter adapter = new CheckSignatureAdapter(CheckSignatureAdapter.TYPE_SIGNATURE, mockVisitor);
            adapter.visitTypeArgument('x');
            System.out.println("testVisitTypeArgumentWithInvalidWildcard failed: no exception thrown.");
        } catch (IllegalArgumentException e) {
            System.out.println("testVisitTypeArgumentWithInvalidWildcard passed.");
        } catch (Exception e) {
            System.out.println("testVisitTypeArgumentWithInvalidWildcard failed with unexpected exception: " + e);
        }
    }

    private static void testVisitTypeArgumentWithNullSignatureVisitor() {
        try {
            CheckSignatureAdapter adapter = new CheckSignatureAdapter(CheckSignatureAdapter.TYPE_SIGNATURE, null);
            SignatureVisitor result = adapter.visitTypeArgument('+');
            if (result == null) {
                System.out.println("testVisitTypeArgumentWithNullSignatureVisitor passed.");
            } else {
                System.out.println("testVisitTypeArgumentWithNullSignatureVisitor failed: result is not null.");
            }
        } catch (Exception e) {
            System.out.println("testVisitTypeArgumentWithNullSignatureVisitor failed with exception: " + e);
        }
    }
}
