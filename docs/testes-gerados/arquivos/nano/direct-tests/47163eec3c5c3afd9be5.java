/* @test    @summary Test compareTo method of java.lang.Runtime class    @run main RuntimeWitupTest */

import java.lang.Runtime;

public class RuntimeWitupTest {

    public static void main(String[] args) {
        testCompareToEqualVersions();
        testCompareToDifferentVersions();
    }

    static void testCompareToEqualVersions() {
        Runtime.Version version1 = new Runtime.Version(1, 0, 0);
        Runtime.Version version2 = new Runtime.Version(1, 0, 0);
        Runtime runtime = Runtime.getRuntime();
        int result = runtime.compareTo(version1);
        int result2 = runtime.compareTo(version2);
        if (result != 0 || result2 != 0) {
            throw new AssertionError("Expected compareTo to return 0 for equal versions, but got: " + result + " and " + result2);
        }
    }

    static void testCompareToDifferentVersions() {
        Runtime.Version version1 = new Runtime.Version(1, 0, 0);
        Runtime.Version version2 = new Runtime.Version(2, 0, 0);
        Runtime runtime = Runtime.getRuntime();
        int result = runtime.compareTo(version1);
        int result2 = runtime.compareTo(version2);
        if (result >= 0) {
            throw new AssertionError("Expected compareTo to return negative for version1 < current, but got: " + result);
        }
        if (result2 <= 0) {
            throw new AssertionError("Expected compareTo to return positive for version2 > current, but got: " + result2);
        }
    }
}
