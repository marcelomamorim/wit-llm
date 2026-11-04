/*
 * @test
 * @summary Tests for jdk.internal.module.ModuleHashesBuilder
 * @run main ModuleHashesBuilderWitupTest
 */

import java.lang.module.Configuration;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

public class ModuleHashesBuilderWitupTest {

    public static void main(String[] args) {
        testOrderedWithNullAction();
    }

    public static void testOrderedWithNullAction() {
        Configuration config = null; // placeholder, as actual configuration is complex
        Set<String> modules = new HashSet<>();
        jdk.internal.module.ModuleHashesBuilder builder = new jdk.internal.module.ModuleHashesBuilder(config, modules);
        try {
            builder.ordered(null);
            System.out.println("Test failed: NullPointerException was not thrown");
        } catch (NullPointerException e) {
            System.out.println("Test passed: NullPointerException was thrown as expected");
        } catch (Throwable t) {
            System.out.println("Test failed: Unexpected exception: " + t);
        }
    }
}
