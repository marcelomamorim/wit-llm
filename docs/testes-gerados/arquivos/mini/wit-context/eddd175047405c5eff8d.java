/*
 * @test
 * @summary Test IllegalAccessLogger.Builder.complete() for correct behavior and NPE on null maps
 * @run main IllegalAccessLoggerWitupTest
 */

import java.io.PrintStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class IllegalAccessLoggerWitupTest {

    public static void main(String[] args) {
        testCompleteNormal();
        testCompleteNullModuleToConcealedPackages();
        testCompleteNullModuleToExportedPackages();
    }

    private static void testCompleteNormal() {
        IllegalAccessLogger.Builder builder = new IllegalAccessLogger.Builder(
                IllegalAccessLogger.Mode.WARN, System.err);
        // Add some dummy data to avoid null maps
        Map<Module, Set<String>> concealed = new HashMap<>();
        Map<Module, Set<String>> exported = new HashMap<>();
        builder.logAccessToConcealedPackages(IllegalAccessLoggerWitupTest.class.getModule(), Collections.singleton("dummy"));
        builder.logAccessToExportedPackages(IllegalAccessLoggerWitupTest.class.getModule(), Collections.singleton("dummy"));

        // Should complete without exception
        builder.complete();
    }

    private static void testCompleteNullModuleToConcealedPackages() {
        IllegalAccessLogger.Builder builder = new IllegalAccessLogger.Builder(
                IllegalAccessLogger.Mode.WARN, System.err) {
            // Override to simulate null moduleToConcealedPackages
            private Map<Module, Set<String>> moduleToConcealedPackages = null;
            private Map<Module, Set<String>> moduleToExportedPackages = new HashMap<>();

            @Override
            public void complete() {
                // Use the overridden null field to trigger NPE
                Map<Module, Set<String>> map1 = Collections.unmodifiableMap(moduleToConcealedPackages);
                Map<Module, Set<String>> map2 = Collections.unmodifiableMap(moduleToExportedPackages);
            }
        };

        try {
            builder.complete();
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException npe) {
            // Expected
        }
    }

    private static void testCompleteNullModuleToExportedPackages() {
        IllegalAccessLogger.Builder builder = new IllegalAccessLogger.Builder(
                IllegalAccessLogger.Mode.WARN, System.err) {
            // Override to simulate null moduleToExportedPackages
            private Map<Module, Set<String>> moduleToConcealedPackages = new HashMap<>();
            private Map<Module, Set<String>> moduleToExportedPackages = null;

            @Override
            public void complete() {
                Map<Module, Set<String>> map1 = Collections.unmodifiableMap(moduleToConcealedPackages);
                Map<Module, Set<String>> map2 = Collections.unmodifiableMap(moduleToExportedPackages);
            }
        };

        try {
            builder.complete();
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException npe) {
            // Expected
        }
    }

}
