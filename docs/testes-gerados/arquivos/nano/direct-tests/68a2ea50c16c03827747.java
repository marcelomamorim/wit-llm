/*
 * @test
 * @summary Test Runtime.addShutdownHook with a simple thread.
 * @run main RuntimeWitupTest
 */

import java.lang.Runtime;

public class RuntimeWitupTest {
    public static void main(String[] args) {
        Runtime runtime = Runtime.getRuntime();
        Thread hook = new Thread(() -> {
            System.out.println("Shutdown hook executed");
        });
        try {
            runtime.addShutdownHook(hook);
            System.out.println("Shutdown hook added successfully");
        } catch (SecurityException se) {
            System.out.println("SecurityException: Cannot add shutdown hook");
        }
    }
}
