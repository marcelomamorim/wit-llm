/*
 * @test
 * @summary Witup generated tests for jdk.internal.module.Checks.requireModuleName
 * @modules java.base/jdk.internal.module
 * @run main ChecksWitupTest
 */

import jdk.internal.module.Checks;

public class ChecksWitupTest {

    public static void main(String[] args) {
        checkReturnsSameName("java.base");
        checkReturnsSameName("a.b.c");
        checkReturnsSameName("_x.$y");
        checkReturnsSameName("jdk.internal2");

        expectIAE(null);
        expectIAE("a..b");
        expectIAE(".a");
        expectIAE("a.");
        expectIAE("1a");
        expectIAE("a.1b");
        expectIAE("a-b");
        expectIAE("a b");
        expectIAE("a.b-c");
    }

    static void checkReturnsSameName(String name) {
        String result = Checks.requireModuleName(name);
        if (!name.equals(result)) {
            throw new RuntimeException("unexpected result for " + name + ": " + result);
        }
    }

    static void expectIAE(String name) {
        try {
            Checks.requireModuleName(name);
        } catch (IllegalArgumentException e) {
            return;
        }
        throw new RuntimeException("IllegalArgumentException not thrown for: " + name);
    }
}
