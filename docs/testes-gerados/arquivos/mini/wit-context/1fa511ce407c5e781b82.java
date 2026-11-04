/*
 * @test
 * @summary Deterministic tests for java.lang.invoke.InfoFromMemberName.getMethodType()
 * @run main InfoFromMemberNameWitupTest
 */

import java.lang.invoke.InfoFromMemberName;
import java.lang.invoke.MethodType;
import java.lang.invoke.MethodHandles.Lookup;
import java.lang.reflect.Member;

public class InfoFromMemberNameWitupTest {

    // Minimal stub for MemberName to allow testing getMethodType
    static class MemberNameStub extends Member {
        private final MethodType methodType;

        MemberNameStub(MethodType mt) {
            super();
            this.methodType = mt;
        }

        @Override
        public Class<?> getDeclaringClass() {
            return InfoFromMemberNameWitupTest.class;
        }

        @Override
        public String getName() {
            return "stubMethod";
        }

        @Override
        public int getModifiers() {
            return 0;
        }

        // Method to simulate getMethodOrFieldType
        public MethodType getMethodOrFieldType() {
            return methodType;
        }

        // Dummy implementations to satisfy abstract class Member
        @Override
        public boolean isSynthetic() {
            return false;
        }
    }

    public static void main(String[] args) {
        testGetMethodTypeReturnsCorrectType();
    }

    private static void testGetMethodTypeReturnsCorrectType() {
        MethodType expected = MethodType.methodType(String.class, int.class, double.class);
        MemberNameStub member = new MemberNameStub(expected);
        // referenceKind is arbitrary valid byte, e.g. 1
        InfoFromMemberName info = new InfoFromMemberName(null, member, (byte)1);

        MethodType actual = info.getMethodType();

        if (!expected.equals(actual)) {
            throw new AssertionError("getMethodType() returned unexpected MethodType: " + actual);
        }
    }
}
