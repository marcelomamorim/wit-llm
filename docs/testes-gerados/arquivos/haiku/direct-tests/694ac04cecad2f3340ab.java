/*
 * @test
 * @summary Smoke test: the public CleanerImpl constructor can be invoked from the test
 * @modules java.base/jdk.internal.ref
 * @run main CleanerImplWitupTest
 */

public class CleanerImplWitupTest {
    public static void main(String[] args) {
        jdk.internal.ref.CleanerImpl impl = new jdk.internal.ref.CleanerImpl();
        if (impl == null) {
            throw new RuntimeException("CleanerImpl constructor returned null");
        }
    }
}
