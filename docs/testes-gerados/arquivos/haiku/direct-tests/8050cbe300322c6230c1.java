/*
 * @test
 * @summary Verify CheckSignatureAdapter.visitTypeArgument state checking and delegation
 * @modules java.base/jdk.internal.org.objectweb.asm.util
 * @run main CheckSignatureAdapterWitupTest
 */

import jdk.internal.org.objectweb.asm.util.CheckSignatureAdapter;

public class CheckSignatureAdapterWitupTest {

    public static void main(String[] args) {
        testTypeArgumentOnFreshAdapterThrows();
        testTypeArgumentAfterClassTypeDoesNotThrow();
        testTypeArgumentDelegatesToNextVisitor();
    }

    static void testTypeArgumentOnFreshAdapterThrows() {
        CheckSignatureAdapter a = new CheckSignatureAdapter(CheckSignatureAdapter.TYPE_SIGNATURE, null);
        boolean thrown = false;
        try {
            a.visitTypeArgument();
        } catch (IllegalStateException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("visitTypeArgument on fresh adapter must throw IllegalStateException");
        }
    }

    static void testTypeArgumentAfterClassTypeDoesNotThrow() {
        CheckSignatureAdapter a = new CheckSignatureAdapter(CheckSignatureAdapter.TYPE_SIGNATURE, null);
        a.visitClassType("java/lang/String");
        a.visitTypeArgument();
        a.visitTypeArgument();
    }

    static void testTypeArgumentDelegatesToNextVisitor() {
        CheckSignatureAdapter delegate = new CheckSignatureAdapter(CheckSignatureAdapter.TYPE_SIGNATURE, null);
        CheckSignatureAdapter a = new CheckSignatureAdapter(CheckSignatureAdapter.TYPE_SIGNATURE, delegate);
        a.visitClassType("java/lang/Object");
        a.visitTypeArgument();
        delegate.visitTypeArgument();
    }
}
