/*
 * @test
 * @summary Test for SystemModuleFinders.find(String)
 * @run main SystemModuleFindersWitupTest
 */

import java.util.Optional;
import jdk.internal.module.SystemModuleFinders;

public class SystemModuleFindersWitupTest {

    public static void main(String[] args) {
        testFindWithNullName();
        testFindWithExistingName();
        testFindWithNonExistingName();
    }

    private static void testFindWithNullName() {
        try {
            SystemModuleFinders.find(null);
            System.out.println("FAIL: Expected NullPointerException for null name");
        } catch (NullPointerException e) {
            System.out.println("PASS: NullPointerException thrown as expected for null name");
        } catch (Throwable t) {
            System.out.println("FAIL: Unexpected exception for null name: " + t);
        }
    }

    private static void testFindWithExistingName() {
        String existingName = "java.base";
        Optional<?> result = SystemModuleFinders.find(existingName);
        if (result != null && result.isPresent()) {
            System.out.println("PASS: Found module for existing name: " + existingName);
        } else {
            System.out.println("FAIL: Did not find module for existing name: " + existingName);
        }
    }

    private static void testFindWithNonExistingName() {
        String nonExistingName = "non.existent.module";
        Optional<?> result = SystemModuleFinders.find(nonExistingName);
        if (result != null && !result.isPresent()) {
            System.out.println("PASS: No module found for non-existing name: " + nonExistingName);
        } else {
            System.out.println("FAIL: Unexpectedly found module for non-existing name: " + nonExistingName);
        }
    }
}
