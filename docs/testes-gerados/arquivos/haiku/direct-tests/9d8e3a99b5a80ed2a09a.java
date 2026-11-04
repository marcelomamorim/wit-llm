/*
 * @test
 * @summary Smoke test for the public CleanerImpl constructor
 * @modules java.base/jdk.internal.ref
 * @run main CleanerImplWitupTest
 */

import jdk.internal.ref.CleanerImpl;

public class CleanerImplWitupTest {
    public static void main(String[] args) {
        CleanerImpl impl = new CleanerImpl();
        if (impl == null) {
            throw new RuntimeException("CleanerImpl constructor returned null");
        }
        if (impl.queue == null) {
            throw new RuntimeException("queue not initialized");
        }
    }
}
