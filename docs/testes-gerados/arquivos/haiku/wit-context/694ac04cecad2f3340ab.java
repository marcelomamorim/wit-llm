/*
 * @test
 * @summary Smoke test for the public CleanerImpl constructor; the clear() override is not publicly reachable
 * @modules java.base/jdk.internal.ref
 * @run main CleanerImplWitupTest
 */

public class CleanerImplWitupTest {
    public static void main(String[] args) {
        CleanerImpl a = new CleanerImpl();
        CleanerImpl b = new CleanerImpl();
        if (a == null || b == null) {
            throw new RuntimeException("CleanerImpl construction returned null");
        }
        if (a == b) {
            throw new RuntimeException("expected distinct CleanerImpl instances");
        }
    }
}
