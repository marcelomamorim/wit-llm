/*
 * @test
 * @summary Test getMethodType() method of InfoFromMemberName
 * @run main InfoFromMemberNameWitupTest
 */

import java.lang.invoke.InfoFromMemberName;
import java.lang.invoke.MethodType;
import java.lang.invoke.MethodHandles.Lookup;
import java.lang.reflect.Member;

public class InfoFromMemberNameWitupTest {

    // Minimal stub class to simulate MemberName with getMethodOrFieldType
    static class StubMemberName extends Member {
        private final MethodType type;

        StubMemberName(MethodType type) {
            super();
            this.type = type;
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
            return type;
        }

        // Dummy implementations for abstract methods
        @Override
        public boolean isSynthetic() { return false; }
    }

    // Minimal stub Lookup class to satisfy constructor
    static class StubLookup extends Lookup {
        StubLookup() {
            super(InfoFromMemberNameWitupTest.class);
        }
    }

    public static void main(String[] args) throws Throwable {
        // Create a MethodType instance
        MethodType mt = MethodType.methodType(String.class, int.class, double.class);

        // Create stub member with this MethodType
        StubMemberName stubMember = new StubMemberName(mt);

        // Create InfoFromMemberName instance
        InfoFromMemberName info = new InfoFromMemberName(new StubLookup(), stubMember, (byte)1);

        // Call getMethodType and verify it returns the same MethodType
        MethodType returnedType = info.getMethodType();

        if (!returnedType.equals(mt)) {
            throw new AssertionError("getMethodType() did not return expected MethodType");
        }

        System.out.println("Test passed: getMethodType() returned expected MethodType: " + returnedType);
    }
}
