/* @test    @summary Test visitCode method for abstract method check    @run main CheckMethodAdapterWitupTest */

import jdk.internal.org.objectweb.asm.util.CheckMethodAdapter;
import jdk.internal.org.objectweb.asm.Opcodes;

public class CheckMethodAdapterWitupTest {

    public static void main(String[] args) {
        testVisitCodeWithAbstractAccess();
        testVisitCodeWithNonAbstractAccess();
    }

    private static void testVisitCodeWithAbstractAccess() {
        CheckMethodAdapter adapter = new CheckMethodAdapter(0, null, null, null, null, null);
        int access = Opcodes.ACC_ABSTRACT;
        try {
            adapter.visitCode();
            System.out.println("Test failed: Expected RuntimeException for abstract access.");
        } catch (RuntimeException e) {
            if (e.getMessage().equals("Abstract methods cannot have code")) {
                System.out.println("Test passed: RuntimeException thrown as expected for abstract access.");
            } else {
                System.out.println("Test failed: Unexpected exception message: " + e.getMessage());
            }
        } catch (Exception e) {
            System.out.println("Test failed: Unexpected exception type: " + e);
        }
    }

    private static void testVisitCodeWithNonAbstractAccess() {
        CheckMethodAdapter adapter = new CheckMethodAdapter(0, null, null, null, null, null);
        int access = 0; // non-abstract
        try {
            adapter.visitCode();
            System.out.println("Test passed: No exception for non-abstract access.");
        } catch (Exception e) {
            System.out.println("Test failed: Unexpected exception: " + e);
        }
    }
}
