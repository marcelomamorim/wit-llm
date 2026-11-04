/*
 * @test
 * @summary Tests the public BasicPermission(String name, String actions) constructor: name validation and observable name/implication behavior
 * @run main BasicPermissionWitupTest
 */

import java.security.BasicPermission;

public class BasicPermissionWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testNullNameThrowsNPE() {
        try {
            new BasicPermission(null, "read");
            throw new RuntimeException("expected NullPointerException for null name");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testEmptyNameThrowsIAE() {
        try {
            new BasicPermission("", "read");
            throw new RuntimeException("expected IllegalArgumentException for empty name");
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }

    static void testNameIsPreserved() {
        BasicPermission p = new BasicPermission("exit", "ignored");
        check("exit".equals(p.getName()), "getName() should return the constructor name");
    }

    static void testActionsArgumentDoesNotChangeName() {
        BasicPermission p1 = new BasicPermission("print.queueJob", null);
        BasicPermission p2 = new BasicPermission("print.queueJob", "anything");
        check("print.queueJob".equals(p1.getName()), "name must be kept with null actions");
        check("print.queueJob".equals(p2.getName()), "name must be kept with non-null actions");
    }

    static void testWildcardStarImpliesAnyName() {
        BasicPermission all = new BasicPermission("*", "");
        check(all.implies(new BasicPermission("java.lang.Object", "")),
              "'*' should imply any BasicPermission name");
    }

    static void testPrefixWildcardImpliesSubname() {
        BasicPermission wild = new BasicPermission("java.*", "");
        check(wild.implies(new BasicPermission("java.lang.Foo", "")),
              "'java.*' should imply 'java.lang.Foo'");
        check(!wild.implies(new BasicPermission("javax.foo", "")),
              "'java.*' should not imply 'javax.foo'");
    }

    static void testNonWildcardImpliesOnlyItself() {
        BasicPermission p = new BasicPermission("exit", "");
        check(p.implies(new BasicPermission("exit", "")),
              "'exit' should imply 'exit'");
        check(!p.implies(new BasicPermission("exitVMX", "")),
              "'exit' should not imply 'exitVMX'");
    }

    public static void main(String[] args) {
        testNullNameThrowsNPE();
        testEmptyNameThrowsIAE();
        testNameIsPreserved();
        testActionsArgumentDoesNotChangeName();
        testWildcardStarImpliesAnyName();
        testPrefixWildcardImpliesSubname();
        testNonWildcardImpliesOnlyItself();
    }
}
