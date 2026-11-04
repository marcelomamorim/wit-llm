/*
 * @test
 * @summary Test CheckSignatureAdapter.visitTypeVariable for correct behavior.
 * @run main CheckSignatureAdapterWitupTest
 */

import jdk.internal.org.objectweb.asm.util.CheckSignatureAdapter;

public class CheckSignatureAdapterWitupTest {

    public static void main(String[] args) {
        testVisitTypeVariableWithValidState();
        testVisitTypeVariableWithInvalidState();
    }

    private static void testVisitTypeVariableWithValidState() {
        CheckSignatureAdapter adapter = new CheckSignatureAdapter(
            CheckSignatureAdapter.TYPE_SIGNATURE,
            new DummySignatureVisitor()
        );
        try {
            adapter.visitTypeVariable("T");
            System.out.println("testVisitTypeVariableWithValidState passed");
        } catch (Exception e) {
            System.err.println("testVisitTypeVariableWithValidState failed: " + e);
        }
    }

    private static void testVisitTypeVariableWithInvalidState() {
        CheckSignatureAdapter adapter = new CheckSignatureAdapter(
            CheckSignatureAdapter.TYPE_SIGNATURE,
            new DummySignatureVisitor()
        );
        // Manually set state to trigger exception
        adapter.state = CheckSignatureAdapter.TYPE_SIGNATURE;
        try {
            adapter.visitTypeVariable("T");
            System.err.println("testVisitTypeVariableWithInvalidState failed: no exception thrown");
        } catch (IllegalStateException e) {
            System.out.println("testVisitTypeVariableWithInvalidState passed");
        } catch (Exception e) {
            System.err.println("testVisitTypeVariableWithInvalidState failed: " + e);
        }
    }

    private static class DummySignatureVisitor extends jdk.internal.org.objectweb.asm.signature.SignatureVisitor {
        public DummySignatureVisitor() {
            super(0);
        }
        @Override
        public void visitTypeVariable(String name) {
            // no-op
        }
    }
}
