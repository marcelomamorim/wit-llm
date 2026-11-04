/* @test    @summary Test ClassLoader.run() method for package access check    @run main ClassLoaderWitupTest */

import java.lang.ClassLoader;

public class ClassLoaderWitupTest {

    public static void main(String[] args) {
        testRunMethod();
    }

    public static void testRunMethod() {
        ClassLoader classLoader = new ClassLoader() {
            @Override
            public Void run() {
                return super.run();
            }
        };
        try {
            classLoader.run();
            // If no exception, test passes
        } catch (SecurityException se) {
            // Expected if package access is denied
            System.out.println("SecurityException caught as expected");
        } catch (Exception e) {
            // Fail the test if other exceptions occur
            throw new RuntimeException("Unexpected exception", e);
        }
    }
}
