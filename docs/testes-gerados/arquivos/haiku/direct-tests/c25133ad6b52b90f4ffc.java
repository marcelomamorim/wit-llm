/*
 * @test
 * @summary Verify the default visitProvide behavior of jdk.internal.org.objectweb.asm.util.Printer and its overridable dispatch
 * @modules java.base/jdk.internal.org.objectweb.asm.util
 * @run main PrinterWitupTest
 */

import jdk.internal.org.objectweb.asm.util.Printer;

public class PrinterWitupTest {

    static class Base extends Printer {
        Base() {
            super(327680);
        }

        public void print(java.io.PrintWriter pw) {
        }
    }

    static class Override extends Printer {
        String lastService;
        String[] lastProviders;

        Override() {
            super(327680);
        }

        public void print(java.io.PrintWriter pw) {
        }

        @Override
        public void visitProvide(String service, String... providers) {
            lastService = service;
            lastProviders = providers;
        }
    }

    public static void main(String[] args) {
        Base base = new Base();

        try {
            base.visitProvide("svc", "impl");
            throw new AssertionError("expected RuntimeException was not thrown");
        } catch (RuntimeException e) {
            if (!"Must be overriden".equals(e.getMessage())) {
                throw new AssertionError("unexpected message: " + e.getMessage());
            }
        }

        try {
            base.visitProvide("svc");
            throw new AssertionError("expected RuntimeException was not thrown");
        } catch (RuntimeException e) {
            if (!"Must be overriden".equals(e.getMessage())) {
                throw new AssertionError("unexpected message: " + e.getMessage());
            }
        }

        Override o = new Override();
        o.visitProvide("svc", "a", "b");
        if (!"svc".equals(o.lastService)) {
            throw new AssertionError("service mismatch: " + o.lastService);
        }
        if (o.lastProviders == null || o.lastProviders.length != 2
                || !"a".equals(o.lastProviders[0]) || !"b".equals(o.lastProviders[1])) {
            throw new AssertionError("providers mismatch");
        }

        o.visitProvide("x");
        if (o.lastProviders == null || o.lastProviders.length != 0) {
            throw new AssertionError("expected empty providers array");
        }
    }
}
