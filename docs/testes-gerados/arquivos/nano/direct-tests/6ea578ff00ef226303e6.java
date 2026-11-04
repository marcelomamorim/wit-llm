/* @test    @summary Test getDescriptor method of StackWalker class    @run main StackWalkerWitupTest */

import java.lang.StackWalker;

public class StackWalkerWitupTest {

    public static void main(String[] args) {
        testGetDescriptorReturnsUnsupported();
    }

    public static void testGetDescriptorReturnsUnsupported() {
        StackWalker stackWalker = StackWalker.getInstance();
        String result = null;
        try {
            result = stackWalker.getDescriptor();
        } catch (UnsupportedOperationException e) {
            result = "unsupported";
        }
        if (result == null || result.equals("unsupported")) {
            System.out.println("Test passed: getDescriptor() throws or returns unsupported as expected.");
        } else {
            System.out.println("Test failed: getDescriptor() returned unexpected value: " + result);
        }
    }
}
