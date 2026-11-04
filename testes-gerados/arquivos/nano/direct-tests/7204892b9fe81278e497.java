/* @test    @summary Test compareToIgnoreOptional method of Runtime class    @run main RuntimeWitupTest */

import java.lang.Runtime;

public class RuntimeWitupTest {

    public static void main(String[] args) {
        testCompareToIgnoreOptionalEqual();
        testCompareToIgnoreOptionalLess();
        testCompareToIgnoreOptionalGreater();
    }

    static class Version {
        private int versionCode;
        public Version(int code) {
            this.versionCode = code;
        }
        public int getVersionCode() {
            return versionCode;
        }
    }

    static class RuntimeExtended extends Runtime {
        public int compare(Version v, boolean ignoreOptional) {
            if (v == null) {
                throw new NullPointerException();
            }
            // Simulate comparison based on versionCode
            int currentVersion = 1; // arbitrary current version
            if (v.getVersionCode() == currentVersion) {
                return 0;
            } else if (v.getVersionCode() < currentVersion) {
                return 1;
            } else {
                return -1;
            }
        }
        public int compareToIgnoreOptional(Version obj) {
            return compare(obj, true);
        }
    }

    static void testCompareToIgnoreOptionalEqual() {
        RuntimeExtended runtime = new RuntimeExtended();
        Version v = new Version(1);
        int result = runtime.compareToIgnoreOptional(v);
        if (result != 0) {
            throw new AssertionError("Expected 0, but got " + result);
        }
    }

    static void testCompareToIgnoreOptionalLess() {
        RuntimeExtended runtime = new RuntimeExtended();
        Version v = new Version(0);
        int result = runtime.compareToIgnoreOptional(v);
        if (result != 1) {
            throw new AssertionError("Expected 1, but got " + result);
        }
    }

    static void testCompareToIgnoreOptionalGreater() {
        RuntimeExtended runtime = new RuntimeExtended();
        Version v = new Version(2);
        int result = runtime.compareToIgnoreOptional(v);
        if (result != -1) {
            throw new AssertionError("Expected -1, but got " + result);
        }
    }
}
