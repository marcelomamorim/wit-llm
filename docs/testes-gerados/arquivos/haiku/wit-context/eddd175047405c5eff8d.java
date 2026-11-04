/*
 * @test
 * @summary Verifies that Builder.complete() finalizes the builder so that later configuration calls throw IllegalStateException
 * @modules java.base/jdk.internal.module
 * @run main IllegalAccessLoggerWitupTest
 */

import java.io.PrintStream;
import java.util.Set;
import jdk.internal.module.IllegalAccessLogger;

public class IllegalAccessLoggerWitupTest {

    public static void main(String[] args) {
        PrintStream ps = System.err;

        for (IllegalAccessLogger.Mode mode : IllegalAccessLogger.Mode.values()) {
            IllegalAccessLogger.Builder b = new IllegalAccessLogger.Builder(mode, ps);
            b.complete();

            expectISE(() -> b.logAccessToConcealedPackages(Object.class.getModule(), Set.of("p")));
            expectISE(() -> b.logAccessToExportedPackages(Object.class.getModule(), Set.of("q")));
        }

        IllegalAccessLogger.Builder fresh = new IllegalAccessLogger.Builder(IllegalAccessLogger.Mode.WARN, ps);
        fresh.complete();
    }

    private static void expectISE(Runnable r) {
        try {
            r.run();
        } catch (IllegalStateException expected) {
            return;
        }
        throw new RuntimeException("Expected IllegalStateException was not thrown");
    }
}
