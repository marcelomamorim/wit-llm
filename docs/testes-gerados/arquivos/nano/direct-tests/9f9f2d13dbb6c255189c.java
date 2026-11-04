/* @test    @summary Test visitModule method of RemappingClassAdapter for deprecation exception    @run main RemappingClassAdapterWitupTest */

import jdk.internal.org.objectweb.asm.commons.RemappingClassAdapter;

public class RemappingClassAdapterWitupTest {

    public static void main(String[] args) {
        testVisitModuleThrowsRuntimeException();
    }

    public static void testVisitModuleThrowsRuntimeException() {
        try {
            RemappingClassAdapter adapter = new RemappingClassAdapter(null, null);
            adapter.visitModule("testModule", 0, "1.0");
            System.out.println("Test failed: no exception thrown");
        } catch (RuntimeException e) {
            if (e.getMessage().contains("RemappingClassAdapter is deprecated")) {
                System.out.println("Test passed: RuntimeException caught as expected");
            } else {
                System.out.println("Test failed: Unexpected exception message: " + e.getMessage());
            }
        } catch (Exception e) {
            System.out.println("Test failed: Unexpected exception type: " + e);
        }
    }
}
