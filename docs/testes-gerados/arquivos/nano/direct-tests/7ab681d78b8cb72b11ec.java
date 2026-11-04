/*
 * @test
 * @summary Test SystemModuleFinders.find() method for existing and non-existing modules
 * @run main SystemModuleFindersWitupTest
 */

import java.lang.module.ModuleReference;
import java.util.Optional;

public class SystemModuleFindersWitupTest {

    public static void main(String[] args) {
        testFindExistingModule();
        testFindNonExistingModule();
    }

    private static void testFindExistingModule() {
        String existingModuleName = "java.base";
        Optional<ModuleReference> result = jdk.internal.module.SystemModuleFinders.find(existingModuleName);
        if (result.isPresent()) {
            System.out.println("PASS: Found existing module: " + existingModuleName);
        } else {
            System.out.println("FAIL: Did not find existing module: " + existingModuleName);
        }
    }

    private static void testFindNonExistingModule() {
        String nonExistingModuleName = "non.existent.module";
        Optional<ModuleReference> result = jdk.internal.module.SystemModuleFinders.find(nonExistingModuleName);
        if (!result.isPresent()) {
            System.out.println("PASS: Correctly did not find non-existing module: " + nonExistingModuleName);
        } else {
            System.out.println("FAIL: Unexpectedly found non-existing module: " + nonExistingModuleName);
        }
    }
}
