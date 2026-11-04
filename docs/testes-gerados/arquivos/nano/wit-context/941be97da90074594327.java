/* @test    @summary Test for CheckModuleAdapter.visitEnd() method to verify IllegalStateException is thrown when 'end' is true.    @run main CheckModuleAdapterWitupTest */

import jdk.internal.org.objectweb.asm.util.CheckModuleAdapter;

public class CheckModuleAdapterWitupTest {

    public static void main(String[] args) {
        testVisitEndThrowsExceptionWhenEndIsTrue();
    }

    private static void testVisitEndThrowsExceptionWhenEndIsTrue() {
        // Setup: create a dummy subclass to manipulate 'end' state
        CheckModuleAdapter adapter = new CheckModuleAdapter(null, false) {
            @Override
            public void visitEnd() {
                // Force 'end' to true to simulate the state after first call
                this.end = true;
                super.visitEnd();
            }
        };
        // Call visitEnd() once to set 'end' to true
        adapter.visitEnd();
        // Expect: second call should throw IllegalStateException
        boolean exceptionThrown = false;
        try {
            adapter.visitEnd();
        } catch (IllegalStateException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected IllegalStateException was not thrown.");
        }
    }
}
