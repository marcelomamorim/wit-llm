/*
 * @test
 * @summary Verify Runtime.removeShutdownHook returns true only for a hook that is currently registered
 * @run main RuntimeWitupTest
 */

public class RuntimeWitupTest {
    public static void main(String[] args) {
        Runtime rt = Runtime.getRuntime();

        Thread hook = new Thread(() -> { });
        rt.addShutdownHook(hook);

        if (!rt.removeShutdownHook(hook)) {
            throw new RuntimeException("removeShutdownHook should return true for a registered hook");
        }

        if (rt.removeShutdownHook(hook)) {
            throw new RuntimeException("removeShutdownHook should return false when the hook was already removed");
        }

        Thread neverRegistered = new Thread(() -> { });
        if (rt.removeShutdownHook(neverRegistered)) {
            throw new RuntimeException("removeShutdownHook should return false for a hook that was never registered");
        }

        Thread reRegistered = new Thread(() -> { });
        rt.addShutdownHook(reRegistered);
        if (!rt.removeShutdownHook(reRegistered)) {
            throw new RuntimeException("removeShutdownHook should return true after a new registration");
        }
    }
}
