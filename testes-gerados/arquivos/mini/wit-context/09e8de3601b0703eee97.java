/*
 * @test
 * @summary Test java.lang.Module.addReads(Module) for null argument and caller validation
 * @run main ModuleWitupTest
 */

import java.lang.Module;
import java.lang.IllegalCallerException;
import java.util.Objects;
import jdk.internal.reflect.Reflection;

public class ModuleWitupTest {

    public static void main(String[] args) throws Exception {
        testAddReadsNullArgument();
        testAddReadsCallerMismatch();
        testAddReadsSuccess();
    }

    private static void testAddReadsNullArgument() {
        Module module = ModuleWitupTest.class.getModule();
        try {
            module.addReads(null);
            throw new Error("Expected NullPointerException not thrown");
        } catch (NullPointerException npe) {
            // expected
        }
    }

    private static void testAddReadsCallerMismatch() {
        Module module = ModuleWitupTest.class.getModule();
        if (!module.isNamed()) {
            // unnamed module: skip this test because addReads does not throw IllegalCallerException
            return;
        }
        // We need to simulate a caller module different from 'this' to trigger IllegalCallerException
        // The method getCallerModule(Reflection.getCallerClass()) is internal and no public API to fake caller
        // So we invoke addReads from a helper method in a different class to produce a different caller
        try {
            Helper.callAddReadsWithDifferentCaller(module);
            throw new Error("Expected IllegalCallerException not thrown");
        } catch (IllegalCallerException ice) {
            // expected
        }
    }

    private static void testAddReadsSuccess() {
        Module module = ModuleWitupTest.class.getModule();
        Module other = ModuleWitupTest.class.getModule();
        // addReads returns this module instance
        Module result = module.addReads(other);
        if (result != module) {
            throw new Error("addReads did not return this module instance");
        }
    }

    static class Helper {
        static void callAddReadsWithDifferentCaller(Module target) {
            // This method calls addReads on target module, so caller is Helper class's module
            // If target module is different from Helper's module, IllegalCallerException expected
            target.addReads(ModuleWitupTest.class.getModule());
        }
    }
}
