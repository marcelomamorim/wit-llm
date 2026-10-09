/* @test
   @summary Tests LockSupport.getBlocker for a non-null Thread.
   @run main LockSupportWitupTest_Ped635df1cf5f
 */
import java.util.concurrent.locks.LockSupport;
import java.lang.Thread;
import jdk.internal.misc.Unsafe;

public class LockSupportWitupTest_Ped635df1cf5f {
    public static void main(String[] args) throws Exception {
        Thread t = new Thread();
        Object result = LockSupport.getBlocker(t);
        if (result != null) {
            System.out.println("getBlocker returned non-null object as expected.");
        } else {
            System.out.println("getBlocker returned null.");
        }
        // Test that passing null throws NullPointerException
        boolean exceptionThrown = false;
        try {
            LockSupport.getBlocker(null);
        } catch (NullPointerException e) {
            exceptionThrown = true;
        }
        if (exceptionThrown) {
            System.out.println("NullPointerException thrown as expected when passing null.");
        } else {
            throw new AssertionError("Expected NullPointerException when passing null.");
        }
    }
}
