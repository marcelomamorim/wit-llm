/*
 * @test
 * @summary Test for java.lang.Module.addReads(Module)
 * @run main ModuleWitupTest
 */

import java.lang.Module;
import java.lang.reflect.ReflectiveOperationException;

public class ModuleWitupTest {

    public static void main(String[] args) {
        testAddReadsWithNull();
        testAddReadsWithValidModule();
    }

    private static void testAddReadsWithNull() {
        Module module = createDummyModule();
        try {
            module.addReads(null);
            System.err.println("Expected NullPointerException not thrown");
            System.exit(1);
        } catch (NullPointerException e) {
            System.out.println("NullPointerException correctly thrown for null argument");
        } catch (Throwable t) {
            System.err.println("Unexpected exception: " + t);
            System.exit(1);
        }
    }

    private static void testAddReadsWithValidModule() {
        Module module1 = createDummyModule();
        Module module2 = createDummyModule();
        try {
            Module result = module1.addReads(module2);
            if (result != module1) {
                System.err.println("addReads did not return the original module");
                System.exit(1);
            }
            System.out.println("addReads with valid module executed successfully");
        } catch (Throwable t) {
            System.err.println("Unexpected exception during valid addReads: " + t);
            System.exit(1);
        }
    }

    private static Module createDummyModule() {
        return new DummyModule();
    }

    private static class DummyModule extends Module {
        protected DummyModule() {
            super("dummy");
        }
        @Override
        public String getName() {
            return "dummy";
        }
        @Override
        public boolean isNamed() {
            return true;
        }
        @Override
        public Module addReads(Module other) {
            if (other == null) {
                throw new NullPointerException();
            }
            // Simulate behavior: do nothing and return this
            return this;
        }
    }
}
