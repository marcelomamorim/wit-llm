/*
 * @test
 * @summary Test IllegalAccessLogger.Builder.complete() method behavior
 * @run main IllegalAccessLoggerWitupTest
 */

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.Map;
import jdk.internal.module.IllegalAccessLogger;

public class IllegalAccessLoggerWitupTest {
    public static void main(String[] args) throws Exception {
        testCompleteSetsCompleteFlagAndCreatesLogger();
        testCompleteThrowsIfCalledTwice();
    }

    private static void testCompleteSetsCompleteFlagAndCreatesLogger() {
        IllegalAccessLogger.Builder builder = new IllegalAccessLogger.Builder(
                IllegalAccessLogger.Mode.WARN,
                System.err
        );

        // Add some dummy data
        Module m = IllegalAccessLoggerWitupTest.class.getModule();
        Set<String> concealed = new HashSet<>();
        concealed.add("concealed.pkg");
        Set<String> exported = new HashSet<>();
        exported.add("exported.pkg");

        builder.logAccessToConcealedPackages(m, concealed);
        builder.logAccessToExportedPackages(m, exported);

        builder.complete();

        // After complete, calling complete again should throw IllegalStateException
        boolean threw = false;
        try {
            builder.complete();
        } catch (IllegalStateException e) {
            threw = true;
        }
        if (!threw) {
            throw new AssertionError("Expected IllegalStateException on second complete() call");
        }
    }

    private static void testCompleteThrowsIfCalledTwice() {
        IllegalAccessLogger.Builder builder = new IllegalAccessLogger.Builder(
                IllegalAccessLogger.Mode.ONESHOT,
                System.err
        );
        builder.complete();
        boolean threw = false;
        try {
            builder.complete();
        } catch (IllegalStateException e) {
            threw = true;
        }
        if (!threw) {
            throw new AssertionError("Expected IllegalStateException on second complete() call");
        }
    }
}
