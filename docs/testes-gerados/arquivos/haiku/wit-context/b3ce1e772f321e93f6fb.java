/*
 * @test
 * @summary Smoke test for public constructor of jdk.internal.ref.CleanerImpl; clear() is not reachable from public API
 * @modules java.base/jdk.internal.ref
 * @run main CleanerImplWitupTest
 */

import jdk.internal.ref.CleanerImpl;

public class CleanerImplWitupTest {

    public static void main(String[] args) {
        CleanerImpl first = new CleanerImpl();
        if (first == null) {
            throw new RuntimeException("CleanerImpl constructor returned null");
        }
        CleanerImpl second = new CleanerImpl();
        if (first == second) {
            throw new RuntimeException("CleanerImpl constructor returned a shared instance");
        }
    }
}
