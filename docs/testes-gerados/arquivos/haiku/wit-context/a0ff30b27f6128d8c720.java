/*
 * @test
 * @summary Witup-derived checks for Runtime.removeShutdownHook
 * @run main RuntimeWitupTest
 */

public class RuntimeWitupTest {
    public static void main(String[] args) {
        Runtime rt = Runtime.getRuntime();

        Thread unregistered = new Thread(() -> {});
        if (rt.removeShutdownHook(unregistered)) {
            throw new RuntimeException("removeShutdownHook returned true for unregistered hook");
        }

        Thread hook = new Thread(() -> {});
        rt.addShutdownHook(hook);
        if (!rt.removeShutdownHook(hook)) {
            throw new RuntimeException("removeShutdownHook returned false for registered hook");
        }
        if (rt.removeShutdownHook(hook)) {
            throw new RuntimeException("removeShutdownHook returned true on second removal");
        }

        try {
            rt.removeShutdownHook(null);
            throw new RuntimeException("expected NullPointerException for null hook");
        } catch (NullPointerException expected) {
        }
    }
}
