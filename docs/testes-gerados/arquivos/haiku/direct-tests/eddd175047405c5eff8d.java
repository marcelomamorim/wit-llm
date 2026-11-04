/*
 * @test
 * @summary Verify that IllegalAccessLogger.Builder.complete() locks the builder against further configuration
 * @modules java.base/jdk.internal.module
 * @run main IllegalAccessLoggerWitupTest
 */

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Collections;
import java.util.Set;
import jdk.internal.module.IllegalAccessLogger;

public class IllegalAccessLoggerWitupTest {

    public static void main(String[] args) {
        Module m = Object.class.getModule();
        Set<String> pkgs = Collections.singleton("java.lang");

        IllegalAccessLogger.Builder b = new IllegalAccessLogger.Builder(
                IllegalAccessLogger.Mode.WARN, new PrintStream(new ByteArrayOutputStream()));
        b.logAccessToConcealedPackages(m, pkgs);
        b.logAccessToExportedPackages(m, pkgs);
        b.complete();

        expectIllegalState(() -> b.logAccessToConcealedPackages(m, pkgs));
        expectIllegalState(() -> b.logAccessToExportedPackages(m, pkgs));

        b.complete();

        IllegalAccessLogger.Builder b2 = new IllegalAccessLogger.Builder(
                IllegalAccessLogger.Mode.DEBUG, new PrintStream(new ByteArrayOutputStream()));
        b2.complete();
        expectIllegalState(() -> b2.logAccessToExportedPackages(m, pkgs));
    }

    static void expectIllegalState(Runnable r) {
        try {
            r.run();
        } catch (IllegalStateException e) {
            return;
        }
        throw new AssertionError("expected IllegalStateException");
    }
}
