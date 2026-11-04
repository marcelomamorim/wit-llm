/*
 * @test
 * @summary Basic observable behavior of the ModulePatcher constructor
 * @modules java.base/jdk.internal.module
 * @run main ModulePatcherWitupTest
 */

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jdk.internal.module.ModulePatcher;

public class ModulePatcherWitupTest {

    public static void main(String[] args) {
        testEmptyInputAccepted();
        testNonEmptyInputAccepted();
        testNullInputThrowsNPE();
    }

    static void testEmptyInputAccepted() {
        new ModulePatcher(Collections.<String, List<String>>emptyMap());
    }

    static void testNonEmptyInputAccepted() {
        Map<String, List<String>> input = new HashMap<>();
        input.put("java.base", List.of("patches"));
        new ModulePatcher(input);
    }

    static void testNullInputThrowsNPE() {
        try {
            new ModulePatcher(null);
            throw new RuntimeException("expected NullPointerException");
        } catch (NullPointerException expected) {
            // input.isEmpty() dereferences the argument
        }
    }
}
