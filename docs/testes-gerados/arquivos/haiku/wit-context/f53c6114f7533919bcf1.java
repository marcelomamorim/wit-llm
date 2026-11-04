/*
 * @test
 * @summary Basic observable check of the public CleanerImpl constructor and its Runnable contract
 * @modules java.base/jdk.internal.ref
 * @run main CleanerImplWitupTest
 */

import jdk.internal.ref.CleanerImpl;

public class CleanerImplWitupTest {
    public static void main(String[] args) {
        CleanerImpl impl = new CleanerImpl();
        if (impl == null) {
            throw new RuntimeException("constructor returned null");
        }
        if (!(impl instanceof Runnable)) {
            throw new RuntimeException("CleanerImpl must implement Runnable");
        }
    }
}
