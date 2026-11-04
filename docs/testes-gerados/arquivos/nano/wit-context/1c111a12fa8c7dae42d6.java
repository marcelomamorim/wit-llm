/*
 * @test
 * @summary Tests for jdk.internal.module.ModuleBootstrap.find(String)
 * @run main ModuleBootstrapWitupTest
 */

import java.util.Optional;
import jdk.internal.module.ModuleBootstrap;

public class ModuleBootstrapWitupTest {

    public static void main(String[] args) {
        testFindWithNullName();
        testFindWithValidName();
        testFindWithInvalidName();
    }

    private static void testFindWithNullName() {
        try {
            ModuleBootstrap.find(null);
            System.out.println("testFindWithNullName: FAILED (expected NullPointerException)");
        } catch (NullPointerException e) {
            System.out.println("testFindWithNullName: PASSED");
        } catch (Throwable t) {
            System.out.println("testFindWithNullName: FAILED (unexpected exception: " + t + ")");
        }
    }

    private static void testFindWithValidName() {
        // Assuming "java.base" is a valid module name in the context
        Optional<?> result = ModuleBootstrap.find("java.base");
        if (result != null && result.isPresent()) {
            System.out.println("testFindWithValidName: PASSED");
        } else {
            System.out.println("testFindWithValidName: FAILED (expected present Optional)");
        }
    }

    private static void testFindWithInvalidName() {
        // Using a likely invalid module name
        Optional<?> result = ModuleBootstrap.find("nonexistent.module.name");
        if (result != null && !result.isPresent()) {
            System.out.println("testFindWithInvalidName: PASSED");
        } else {
            System.out.println("testFindWithInvalidName: FAILED (expected empty Optional)");
        }
    }
}
