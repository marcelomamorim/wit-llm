/*
 * @test
 * @summary Test CheckSignatureAdapter.visitTypeVariable for correct exception and normal behavior
 * @run main CheckSignatureAdapterWitupTest
 */

import jdk.internal.org.objectweb.asm.util.CheckSignatureAdapter;
import jdk.internal.org.objectweb.asm.signature.SignatureVisitor;

public class CheckSignatureAdapterWitupTest {

    private static final int TYPE_SIGNATURE = CheckSignatureAdapter.TYPE_SIGNATURE;
    private static final int EMPTY = 0;
    private static final int SIMPLE_TYPE = 1;

    // A minimal SignatureVisitor stub that records if visitTypeVariable was called
    static class StubSignatureVisitor extends SignatureVisitor {
        boolean visited = false;
        String visitedName = null;

        StubSignatureVisitor() {
            super(Opcodes.ASM7);
        }

        @Override
        public void visitTypeVariable(String name) {
            visited = true;
            visitedName = name;
        }
    }

    public static void main(String[] args) {
        testVisitTypeVariableThrowsIllegalStateException();
        testVisitTypeVariableThrowsIllegalArgumentException();
        testVisitTypeVariableNormalBehavior();
    }

    private static void testVisitTypeVariableThrowsIllegalStateException() {
        // Create adapter with type = TYPE_SIGNATURE
        StubSignatureVisitor sv = new StubSignatureVisitor();
        CheckSignatureAdapter adapter = new CheckSignatureAdapter(TYPE_SIGNATURE, sv);

        // We need to put adapter in a state different from EMPTY (0) to trigger IllegalStateException
        // The field 'state' is private, so we cannot set it directly.
        // Instead, call visitBaseType to change state to SIMPLE_TYPE (1) which is != EMPTY
        adapter.visitBaseType('I'); // changes state to SIMPLE_TYPE internally

        try {
            adapter.visitTypeVariable("T");
            throw new AssertionError("Expected IllegalStateException not thrown");
        } catch (IllegalStateException expected) {
            // expected
        }
    }

    private static void testVisitTypeVariableThrowsIllegalArgumentException() {
        StubSignatureVisitor sv = new StubSignatureVisitor();
        CheckSignatureAdapter adapter = new CheckSignatureAdapter(TYPE_SIGNATURE, sv);

        // state is EMPTY and type is TYPE_SIGNATURE, so call with null or empty string triggers IllegalArgumentException
        try {
            adapter.visitTypeVariable(null);
            throw new AssertionError("Expected IllegalArgumentException for null name not thrown");
        } catch (IllegalArgumentException expected) {
            // expected
        }

        try {
            adapter.visitTypeVariable("");
            throw new AssertionError("Expected IllegalArgumentException for empty name not thrown");
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }

    private static void testVisitTypeVariableNormalBehavior() {
        StubSignatureVisitor sv = new StubSignatureVisitor();
        CheckSignatureAdapter adapter = new CheckSignatureAdapter(TYPE_SIGNATURE, sv);

        // Call with valid name when state == EMPTY and type == TYPE_SIGNATURE
        adapter.visitTypeVariable("T");

        // The internal state should be changed to SIMPLE_TYPE (1) - cannot check directly, so check side effect
        // The stub SignatureVisitor should have been called with "T"
        if (!sv.visited) {
            throw new AssertionError("Expected visitTypeVariable to be called on delegate SignatureVisitor");
        }
        if (!"T".equals(sv.visitedName)) {
            throw new AssertionError("Expected visitTypeVariable to be called with name 'T'");
        }
    }

    // Opcodes constant for ASM API version
    private static class Opcodes {
        static final int ASM7 = 7;
    }
}
