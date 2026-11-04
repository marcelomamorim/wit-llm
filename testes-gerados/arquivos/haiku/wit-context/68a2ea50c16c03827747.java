/*
 * @test
 * @summary Basic observable behavior of Runtime.addShutdownHook
 * @run main RuntimeWitupTest
 */

public class RuntimeWitupTest {
    public static void main(String[] args) {
        Runtime rt = Runtime.getRuntime();
        if (rt != Runtime.getRuntime()) {
            throw new RuntimeException("Runtime.getRuntime() must return the same instance");
        }
        Thread t1 = new Thread(() -> { });
        Thread t2 = new Thread(() -> { });
        rt.addShutdownHook(t1);
        rt.addShutdownHook(t2);
    }
}
