/*
 * @test
 * @summary Test java.lang.System.addOpens(Module m, String pn, Module other) for NPE on null args
 * @run main SystemWitupTest
 */

import java.lang.Module;
import java.util.Objects;

public class SystemWitupTest {
    public static void main(String[] args) {
        testAddOpensNullOther();
        testAddOpensNullPnNonNullOther();
    }

    private static void testAddOpensNullOther() {
        Module m = DummyModule.create("m");
        String pn = "package";
        Module other = null;
        try {
            System.class.getDeclaredMethod("addOpens", Module.class, String.class, Module.class)
                  .invoke(new SystemWrapper(), m, pn, other);
            throw new AssertionError("Expected NullPointerException for null other");
        } catch (ReflectiveOperationException e) {
            Throwable cause = e.getCause();
            if (!(cause instanceof NullPointerException)) {
                throw new AssertionError("Expected NullPointerException for null other, got " + cause, cause);
            }
        }
    }

    private static void testAddOpensNullPnNonNullOther() {
        Module m = DummyModule.create("m");
        String pn = null;
        Module other = DummyModule.create("other");
        try {
            System.class.getDeclaredMethod("addOpens", Module.class, String.class, Module.class)
                  .invoke(new SystemWrapper(), m, pn, other);
            throw new AssertionError("Expected NullPointerException for null pn with non-null other");
        } catch (ReflectiveOperationException e) {
            Throwable cause = e.getCause();
            if (!(cause instanceof NullPointerException)) {
                throw new AssertionError("Expected NullPointerException for null pn with non-null other, got " + cause, cause);
            }
        }
    }

    // Wrapper to call instance method addOpens on System
    static class SystemWrapper extends System {
        public void addOpens(Module m, String pn, Module other) {
            super.addOpens(m, pn, other);
        }
    }

    // Minimal dummy Module implementation to allow calls
    static final class DummyModule extends Module {
        private final String name;
        private DummyModule(String name) {
            super(null, name, null, null, null, null, null, null, null);
            this.name = name;
        }
        static Module create(String name) {
            return new DummyModule(name);
        }
        @Override
        public String toString() {
            return "DummyModule[" + name + "]";
        }
    }
}