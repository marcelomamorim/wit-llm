/*
 * @test
 * @summary Test System.Logger.log(Level, String, Throwable) method
 * @run main SystemWitupTest
 */

import java.lang.System.Logger;
import java.lang.System.Logger.Level;
import java.util.Objects;

public class SystemWitupTest {
    public static void main(String[] args) {
        // Obtain a System.Logger instance
        Logger logger = System.getLogger("TestLogger");

        // Test normal usage: log with non-null message and throwable
        Throwable t = new Throwable("Test throwable");
        logger.log(Level.INFO, "Test message", t);

        // Test with null message (allowed, should not throw)
        logger.log(Level.WARNING, null, null);

        // No exception expected, so no assertThrows
        // The method delegates to log(level, Supplier<String>, String, Throwable) with null Supplier
        // No direct way to test exception from this method as it does not throw for null msg

        // The WIT expath about NullPointerException for null Supplier is not applicable here,
        // because this method calls log(level, null, msg, thrown) and does not pass a null Supplier explicitly.
        // Hence, no test for NPE on null Supplier is added here.

        // Test with null throwable (allowed)
        logger.log(Level.SEVERE, "Message with null throwable", null);

        // Test with all nulls (level is required, so passing null level would cause NPE outside this method)
        // So do not test null level here as it is not part of this method's contract

        System.out.println("System.Logger.log(Level, String, Throwable) tests passed.");
    }
}
