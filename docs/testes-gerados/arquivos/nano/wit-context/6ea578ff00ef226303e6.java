/* @test    @summary Test for StackWalker.getDescriptor() method to observe behavior and exception throwing    @run main StackWalkerWitupTest */

import java.lang.StackWalker;

public class StackWalkerWitupTest {

    public static void main(String[] args) {
        testGetDescriptorThrowsException();
    }

    public static void testGetDescriptorThrowsException() {
        StackWalker stackWalker = StackWalker.getInstance();
        try {
            String descriptor = stackWalker.getDescriptor();
            System.out.println("Unexpected success: getDescriptor() returned: " + descriptor);
        } catch (UnsupportedOperationException e) {
            System.out.println("Caught expected UnsupportedOperationException.");
        } catch (Throwable t) {
            System.out.println("Caught unexpected exception: " + t);
        }
    }
}
