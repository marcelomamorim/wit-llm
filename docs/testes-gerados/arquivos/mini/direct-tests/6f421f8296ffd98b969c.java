/*
 * @test
 * @summary Test CheckSignatureAdapter.visitInnerClassType behavior
 * @run main CheckSignatureAdapterWitupTest
 */

import jdk.internal.org.objectweb.asm.util.CheckSignatureAdapter;
import jdk.internal.org.objectweb.asm.signature.SignatureVisitor;

public class CheckSignatureAdapterWitupTest {

    public static void main(String[] args) {
        testVisitInnerClassTypeValid();
        testVisitInnerClassTypeIllegalState();
        testVisitInnerClassTypeNullName();
        testVisitInnerClassTypeEmptyName();
    }

    private static void testVisitInnerClassTypeValid() {
        // Create a CheckSignatureAdapter with state = CLASS_TYPE (0) and a dummy SignatureVisitor
        final int CLASS_TYPE = 0;
        final String testName = "InnerClass";

        DummySignatureVisitor dummy = new DummySignatureVisitor();
        CheckSignatureAdapter csa = new CheckSignatureAdapter(CLASS_TYPE, dummy);

        csa.visitInnerClassType(testName);

        if (!testName.equals(dummy.lastVisitedName)) {
            throw new AssertionError("visitInnerClassType did not delegate correctly");
        }
    }

    private static void testVisitInnerClassTypeIllegalState() {
        // Create a CheckSignatureAdapter with state != CLASS_TYPE to trigger IllegalStateException
        final int NOT_CLASS_TYPE = 1; // METHOD_SIGNATURE

        CheckSignatureAdapter csa = new CheckSignatureAdapter(NOT_CLASS_TYPE, null);

        try {
            csa.visitInnerClassType("Name");
            throw new AssertionError("Expected IllegalStateException not thrown");
        } catch (IllegalStateException ise) {
            // expected
        }
    }

    private static void testVisitInnerClassTypeNullName() {
        // Create a CheckSignatureAdapter with state = CLASS_TYPE
        final int CLASS_TYPE = 0;
        CheckSignatureAdapter csa = new CheckSignatureAdapter(CLASS_TYPE, null);

        try {
            csa.visitInnerClassType(null);
            throw new AssertionError("Expected IllegalArgumentException not thrown for null name");
        } catch (IllegalArgumentException iae) {
            // expected
        }
    }

    private static void testVisitInnerClassTypeEmptyName() {
        // Create a CheckSignatureAdapter with state = CLASS_TYPE
        final int CLASS_TYPE = 0;
        CheckSignatureAdapter csa = new CheckSignatureAdapter(CLASS_TYPE, null);

        try {
            csa.visitInnerClassType("");
            throw new AssertionError("Expected IllegalArgumentException not thrown for empty name");
        } catch (IllegalArgumentException iae) {
            // expected
        }
    }

    // Dummy SignatureVisitor to verify delegation
    private static class DummySignatureVisitor extends SignatureVisitor {
        String lastVisitedName = null;

        DummySignatureVisitor() {
            super(0);
        }

        @Override
        public void visitInnerClassType(String name) {
            lastVisitedName = name;
        }
    }
}
