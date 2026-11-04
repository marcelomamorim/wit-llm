/*
 * @test
 * @summary Smoke test for the public constructor of jdk.internal.ref.CleanerImpl; get() is not publicly reachable
 * @modules java.base/jdk.internal.ref
 * @run main CleanerImplWitupTest
 */

import jdk.internal.ref.CleanerImpl;

public class CleanerImplWitupTest {
    public static void main(String[] args) {
        CleanerImpl impl = new CleanerImpl();
        if (impl == null) {
            throw new AssertionError("constructor returned null");
        }
        if (!(impl instanceof Runnable)) {
            throw new AssertionError("CleanerImpl must implement Runnable");
        }
    }
}
