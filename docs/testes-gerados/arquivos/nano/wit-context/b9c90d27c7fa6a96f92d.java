/*
 * @test
 * @summary Test for CheckSignatureAdapter.visitExceptionType() method.
 * @run main CheckSignatureAdapterWitupTest
 */

import jdk.internal.org.objectweb.asm.Opcodes;
import jdk.internal.org.objectweb.asm.signature.SignatureVisitor;
import jdk.internal.org.objectweb.asm.util.CheckSignatureAdapter;

public class CheckSignatureAdapterWitupTest {

    public static void main(String[] args) {
        testVisitExceptionType_ValidState();
        testVisitExceptionType_InvalidState();
    }

    private static void testVisitExceptionType_ValidState() {
        SignatureVisitor mockVisitor = new SignatureVisitor(Opcodes.ASM9) {
            @Override
            public SignatureVisitor visitExceptionType() {
                System.out.println("visitExceptionType called");
                return this;
            }
        };
        CheckSignatureAdapter adapter = new CheckSignatureAdapter(CheckSignatureAdapter.TYPE_SIGNATURE, mockVisitor);
        try {
            SignatureVisitor result = adapter.visitExceptionType();
            if (result != null) {
                System.out.println("testVisitExceptionType_ValidState passed");
            } else {
                System.out.println("testVisitExceptionType_ValidState failed: null result");
            }
        } catch (Exception e) {
            System.out.println("testVisitExceptionType_ValidState failed with exception: " + e);
        }
    }

    private static void testVisitExceptionType_InvalidState() {
        SignatureVisitor mockVisitor = new SignatureVisitor(Opcodes.ASM9) {
            @Override
            public SignatureVisitor visitExceptionType() {
                System.out.println("visitExceptionType called in invalid state");
                return this;
            }
        };
        CheckSignatureAdapter adapter = new CheckSignatureAdapter(CheckSignatureAdapter.TYPE_SIGNATURE, mockVisitor);
        // Simulate invalid state by directly setting internal state (not recommended in real tests, but for illustration)
        // Since internal state is private, this test assumes the method is called in an invalid state.
        // Alternatively, we can invoke the method without prior valid call to simulate invalid state.
        // Here, we just call the method directly to see if it behaves as expected.
        try {
            SignatureVisitor result = adapter.visitExceptionType();
            if (result != null) {
                System.out.println("testVisitExceptionType_InvalidState failed: no exception thrown");
            } else {
                System.out.println("testVisitExceptionType_InvalidState failed: null result");
            }
        } catch (IllegalStateException e) {
            System.out.println("testVisitExceptionType_InvalidState passed: caught expected exception");
        } catch (Exception e) {
            System.out.println("testVisitExceptionType_InvalidState failed with unexpected exception: " + e);
        }
    }
}
