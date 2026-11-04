/*
 * @test
 * @summary Test for IllegalAccessLogger.complete()
 * @run main IllegalAccessLoggerWitupTest
 */

import java.io.PrintStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class IllegalAccessLoggerWitupTest {

    public static void main(String[] args) {
        testCompleteMethod();
    }

    public static void testCompleteMethod() {
        // Setup
        Map<Object, Set<String>> moduleToConcealedPackages = new HashMap<>();
        Map<Object, Set<String>> moduleToExportedPackages = new HashMap<>();
        Object module1 = new Object();
        Object module2 = new Object();
        Set<String> packages1 = new HashSet<>();
        packages1.add("com.example.concealed");
        Set<String> packages2 = new HashSet<>();
        packages2.add("com.example.exported");
        moduleToConcealedPackages.put(module1, packages1);
        moduleToExportedPackages.put(module2, packages2);

        // Instantiate logger
        jdk.internal.module.IllegalAccessLogger logger = new jdk.internal.module.IllegalAccessLogger();
        // Use reflection to invoke complete()
        try {
            java.lang.reflect.Method completeMethod = jdk.internal.module.IllegalAccessLogger.class.getDeclaredMethod("complete");
            completeMethod.setAccessible(true);
            completeMethod.invoke(logger);
        } catch (Exception e) {
            throw new RuntimeException("Failed to invoke complete()", e);
        }

        // Verify that the logger is marked as complete
        // Since 'complete' sets 'complete = true', we can check via reflection
        try {
            java.lang.reflect.Field completeField = jdk.internal.module.IllegalAccessLogger.class.getDeclaredField("complete");
            completeField.setAccessible(true);
            boolean isComplete = completeField.getBoolean(logger);
            if (!isComplete) {
                throw new AssertionError("Logger should be marked as complete after calling complete()");
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to verify complete state", e);
        }
    }
}
