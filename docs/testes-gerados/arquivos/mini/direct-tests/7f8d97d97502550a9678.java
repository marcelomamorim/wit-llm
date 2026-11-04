/*
 * @test
 * @summary Test java.lang.Module.isExported(String) method
 * @run main ModuleWitupTest
 */

import java.lang.Module;
import java.util.Objects;

public class ModuleWitupTest {
    public static void main(String[] args) throws Exception {
        // Get the unnamed module of this class's class loader
        Module module = ModuleWitupTest.class.getModule();

        // Test 1: null argument should throw NullPointerException
        try {
            module.isExported(null);
            throw new Error("Expected NullPointerException for null argument");
        } catch (NullPointerException expected) {
        }

        // Test 2: empty string package name (likely not exported)
        boolean exportedEmpty = module.isExported("");
        // exportedEmpty is boolean, no exception expected

        // Test 3: typical package name "java.lang" (may be exported or not depending on module)
        boolean exportedJavaLang = module.isExported("java.lang");

        // Test 4: arbitrary package name unlikely to be exported
        boolean exportedFake = module.isExported("fake.package.name");

        // Print results to ensure method runs without exceptions
        System.out.println("isExported("") = " + exportedEmpty);
        System.out.println("isExported(\"java.lang\") = " + exportedJavaLang);
        System.out.println("isExported(\"fake.package.name\") = " + exportedFake);
    }
}
