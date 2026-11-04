/*
 * @test
 * @summary Test for ModuleFinder.find method, covering null input case.
 * @run main ModuleFinderWitupTest
 */

import java.lang.module.ModuleFinder;
import java.util.Optional;

public class ModuleFinderWitupTest {

    public static void main(String[] args) {
        testFindWithNullName();
        testFindWithValidName();
    }

    public static void testFindWithNullName() {
        try {
            ModuleFinder finder = new ModuleFinder() {
                @Override
                public Optional find(String name) {
                    return null;
                }
            };
            // Invoca o método com null, esperando NullPointerException
            finder.find(null);
            System.out.println("FAIL: Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            System.out.println("PASS: NullPointerException thrown as expected");
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception: " + e);
        }
    }

    public static void testFindWithValidName() {
        ModuleFinder finder = new ModuleFinder() {
            @Override
            public Optional find(String name) {
                return Optional.empty();
            }
        };
        Optional result = finder.find("someModule");
        if (result != null && !result.isPresent()) {
            System.out.println("PASS: find with valid name returned empty Optional");
        } else {
            System.out.println("FAIL: find with valid name did not return empty Optional");
        }
    }
}
