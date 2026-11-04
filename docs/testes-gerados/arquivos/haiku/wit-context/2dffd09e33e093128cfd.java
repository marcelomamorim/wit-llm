/*
 * @test
 * @summary Smoke test for public CleanerImpl construction and Runnable contract
 * @modules java.base/jdk.internal.ref
 * @run main CleanerImplWitupTest
 */

import jdk.internal.ref.CleanerImpl;

public class CleanerImplWitupTest {
    public static void main(String[] args) {
        CleanerImpl impl = new CleanerImpl();
        if (impl == null) {
            throw new RuntimeException("CleanerImpl construction returned null");
        }
        Runnable r = impl;
        if (r == null) {
            throw new RuntimeException("CleanerImpl is not a Runnable");
        }
    }
}
