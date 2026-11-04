/*
 * @test
 * @summary Checks VM.getSavedProperty returns the saved startup values and null for unknown keys
 * @modules java.base/jdk.internal.misc
 * @run main VMWitupTest
 */

import jdk.internal.misc.VM;

public class VMWitupTest {

    public static void main(String[] args) {
        String version = VM.getSavedProperty("java.version");
        if (version == null) {
            throw new RuntimeException("java.version should be saved at startup");
        }
        if (!version.equals(System.getProperty("java.version"))) {
            throw new RuntimeException("saved java.version differs from startup value");
        }

        String again = VM.getSavedProperty("java.version");
        if (!version.equals(again)) {
            throw new RuntimeException("repeated lookup returned a different value");
        }

        String home = VM.getSavedProperty("java.home");
        if (home == null || home.isEmpty()) {
            throw new RuntimeException("java.home should be saved at startup");
        }

        String missing = VM.getSavedProperty("witup.no.such.property.12345");
        if (missing != null) {
            throw new RuntimeException("unknown key should return null but got: " + missing);
        }
    }
}
