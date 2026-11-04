/*
 * @test
 * @summary Verifies that the single-argument FieldVisitor constructor stores the api version and leaves the delegate visitor null
 * @modules java.base/jdk.internal.org.objectweb.asm
 * @run main FieldVisitorWitupTest
 */

import jdk.internal.org.objectweb.asm.FieldVisitor;

public class FieldVisitorWitupTest {

    static class Probe extends FieldVisitor {
        Probe(int api) {
            super(api);
        }

        int observedApi() {
            return api;
        }

        boolean delegateIsNull() {
            return fv == null;
        }
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    public static void main(String[] args) {
        Probe p1 = new Probe(5);
        check(p1.observedApi() == 5, "api should be stored as 5");
        check(p1.delegateIsNull(), "delegate fv should be null for one-arg constructor");

        Probe p2 = new Probe(0);
        check(p2.observedApi() == 0, "api should be stored as 0");
        check(p2.delegateIsNull(), "delegate fv should be null for api 0");

        Probe p3 = new Probe(-1);
        check(p3.observedApi() == -1, "api should be stored as -1");
        check(p3.delegateIsNull(), "delegate fv should be null for api -1");

        Probe p4 = new Probe(Integer.MAX_VALUE);
        check(p4.observedApi() == Integer.MAX_VALUE, "api should be stored as MAX_VALUE");
        check(p4.delegateIsNull(), "delegate fv should be null for MAX_VALUE");
    }
}
