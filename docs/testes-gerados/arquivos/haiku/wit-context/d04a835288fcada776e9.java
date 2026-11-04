/*
 * @test
 * @summary Witup-generated checks for the public ModulePatcher constructor input handling
 * @modules java.base/jdk.internal.module
 * @run main ModulePatcherWitupTest
 */

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jdk.internal.module.ModulePatcher;

public class ModulePatcherWitupTest {

    public static void main(String[] args) {
        testNullInputThrowsNullPointerException();
        testEmptyInputIsAccepted();
        testNonEmptyInputIsAccepted();
        System.out.println("PASS");
    }

    static void testNullInputThrowsNullPointerException() {
        try {
            new ModulePatcher(null);
            throw new RuntimeException("expected NullPointerException for null input");
        } catch (NullPointerException expected) {
        }
    }

    static void testEmptyInputIsAccepted() {
        Map<String, List<String>> input = Collections.emptyMap();
        ModulePatcher patcher = new ModulePatcher(input);
        if (patcher == null) {
            throw new RuntimeException("constructor returned null");
        }
    }

    static void testNonEmptyInputIsAccepted() {
        Map<String, List<String>> input = new HashMap<>();
        List<String> paths = new ArrayList<>();
        paths.add("patch-dir");
        input.put("java.base", paths);
        ModulePatcher patcher = new ModulePatcher(input);
        if (patcher == null) {
            throw new RuntimeException("constructor returned null");
        }
    }
}
