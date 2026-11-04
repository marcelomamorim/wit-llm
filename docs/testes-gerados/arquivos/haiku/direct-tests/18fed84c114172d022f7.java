/*
 * @test
 * @summary Verifies that the anonymous Set returned by Util.ungrowableSet rejects add with UnsupportedOperationException while preserving the backing set contents
 * @modules java.base/sun.nio.ch
 * @run main UtilWitupTest
 */

import java.util.HashSet;
import java.util.Set;
import sun.nio.ch.Util;

public class UtilWitupTest {

    public static void main(String[] args) {
        Set<String> backing = new HashSet<>();
        backing.add("a");

        Set<String> ungrowable = Util.ungrowableSet(backing);

        if (!ungrowable.contains("a")) {
            throw new RuntimeException("existing element not visible through wrapper");
        }
        if (ungrowable.size() != 1) {
            throw new RuntimeException("unexpected size: " + ungrowable.size());
        }

        boolean thrown = false;
        try {
            ungrowable.add("b");
        } catch (UnsupportedOperationException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("add did not throw UnsupportedOperationException");
        }

        if (backing.contains("b") || backing.size() != 1) {
            throw new RuntimeException("backing set was modified by rejected add");
        }
    }
}
