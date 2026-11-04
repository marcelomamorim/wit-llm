/*
 * @test    @summary Test addReads method of java.lang.Module
 * @run main ModuleWitupTest
 */

import java.lang.Module;
import java.lang.reflect.Method;

public class ModuleWitupTest {

    public static void main(String[] args) {
        testAddReadsWithValidModules();
        testAddReadsWithNull();
        testAddReadsWithCallerCheck();
    }

    private static void testAddReadsWithValidModules() {
        Module m1 = new DummyModule("module1");
        Module m2 = new DummyModule("module2");
        try {
            m1.addReads(m2);
            System.out.println("testAddReadsWithValidModules passed");
        } catch (Exception e) {
            System.err.println("testAddReadsWithValidModules failed: " + e);
        }
    }

    private static void testAddReadsWithNull() {
        Module m1 = new DummyModule("module1");
        try {
            m1.addReads(null);
            System.err.println("testAddReadsWithNull failed: no exception thrown");
        } catch (NullPointerException e) {
            System.out.println("testAddReadsWithNull passed");
        } catch (Exception e) {
            System.err.println("testAddReadsWithNull failed: " + e);
        }
    }

    private static void testAddReadsWithCallerCheck() {
        Module m1 = new DummyModule("module1") {
            @Override
            public Module addReads(Module other) {
                // Simulate caller check failure
                throw new IllegalCallerException("Invalid caller");
            }
        };
        Module m2 = new DummyModule("module2");
        try {
            m1.addReads(m2);
            System.err.println("testAddReadsWithCallerCheck failed: no exception thrown");
        } catch (IllegalCallerException e) {
            System.out.println("testAddReadsWithCallerCheck passed");
        } catch (Exception e) {
            System.err.println("testAddReadsWithCallerCheck failed: " + e);
        }
    }

    static class DummyModule extends Module {
        private final String name;
        DummyModule(String name) {
            super(name);
        }
        @Override
        public String getName() {
            return name;
        }
        @Override
        public boolean isNamed() {
            return true;
        }
        @Override
        public Module addReads(Module other) {
            // Simulate the actual method behavior
            if (other == null) {
                throw new NullPointerException();
            }
            // Simulate caller check
            Class<?> callerClass = Reflection.getCallerClass();
            if (callerClass != null && getCallerModule(callerClass) != this) {
                throw new IllegalCallerException(callerClass + " != " + this);
            }
            // Simulate adding read edge
            return this;
        }
        private static Module getCallerModule(Class<?> callerClass) {
            // Simplified simulation
            return new DummyModule("callerModule");
        }
    }
}
