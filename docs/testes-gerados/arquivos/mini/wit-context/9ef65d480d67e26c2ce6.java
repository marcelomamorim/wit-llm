/*
 * @test
 * @summary Test CheckSignatureAdapter.visitInterface() for correct state validation and delegation
 * @run main CheckSignatureAdapterWitupTest
 */

import jdk.internal.org.objectweb.asm.util.CheckSignatureAdapter;
import jdk.internal.org.objectweb.asm.signature.SignatureVisitor;

public class CheckSignatureAdapterWitupTest {

    public static void main(String[] args) {
        testVisitInterfaceWithValidState();
        testVisitInterfaceWithInvalidState();
    }

    private static void testVisitInterfaceWithValidState() {
        // Create a CheckSignatureAdapter with state = SUPER (8) to allow visitInterface call
        // We use the protected constructor to set state to SUPER (8) by subclassing
        CheckSignatureAdapter adapter = new CheckSignatureAdapter(TYPE_SIGNATURE, null) {
            {
                // forcibly set state to SUPER (8) to simulate valid state
                try {
                    java.lang.reflect.Field stateField = CheckSignatureAdapter.class.getDeclaredField("state");
                    stateField.setAccessible(true);
                    stateField.setInt(this, 8); // SUPER = 8
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        };

        SignatureVisitor sv = adapter.visitInterface();
        if (sv == null) {
            throw new AssertionError("visitInterface() should not return null when sv is null but state is SUPER");
        }
        if (!(sv instanceof CheckSignatureAdapter)) {
            throw new AssertionError("visitInterface() should return a CheckSignatureAdapter instance");
        }
    }

    private static void testVisitInterfaceWithInvalidState() {
        // Create a CheckSignatureAdapter with default state (0) which is not SUPER (8)
        CheckSignatureAdapter adapter = new CheckSignatureAdapter(CheckSignatureAdapter.TYPE_SIGNATURE, null);

        boolean thrown = false;
        try {
            adapter.visitInterface();
        } catch (IllegalStateException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("visitInterface() should throw IllegalStateException when state != SUPER");
        }
    }

    private static final int TYPE_SIGNATURE = 2;
}
