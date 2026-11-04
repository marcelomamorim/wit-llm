/*
 * @test
 * @summary Test java.nio.file.attribute.AclEntry.Builder.setFlags method
 * @run main AclEntryWitupTest
 */

import java.nio.file.attribute.AclEntry;
import java.nio.file.attribute.AclEntryFlag;

public class AclEntryWitupTest {
    public static void main(String[] args) {
        testSetFlagsWithValidFlags();
        testSetFlagsWithNullElement();
    }

    private static void testSetFlagsWithValidFlags() {
        AclEntry.Builder builder = createBuilderStub();
        AclEntryFlag[] flags = new AclEntryFlag[] { AclEntryFlag.FILE_INHERIT, AclEntryFlag.DIRECTORY_INHERIT };
        AclEntry.Builder returned = builder.setFlags(flags);
        if (returned != builder) {
            throw new AssertionError("setFlags should return the same Builder instance");
        }
    }

    private static void testSetFlagsWithNullElement() {
        AclEntry.Builder builder = createBuilderStub();
        AclEntryFlag[] flags = new AclEntryFlag[] { AclEntryFlag.FILE_INHERIT, null };
        try {
            builder.setFlags(flags);
            throw new AssertionError("Expected NullPointerException for null flag element");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    // Helper method to create a Builder instance with minimal valid state
    private static AclEntry.Builder createBuilderStub() {
        // Use reflection to create Builder instance since no public constructor or factory is documented
        // But reflection is disallowed by instructions, so create a minimal subclass workaround
        // Instead, create a dummy AclEntry and get Builder from newBuilder() if available
        // Since no public constructor or factory is documented, create a dummy Builder via newBuilder()
        // The source excerpt does not show newBuilder(), so we create a dummy Builder subclass
        return new DummyBuilder();
    }

    // Dummy subclass of Builder to allow testing setFlags
    private static class DummyBuilder extends AclEntry.Builder {
        DummyBuilder() {
            super(null, null, java.util.EnumSet.noneOf(AclEntryPermission.class), java.util.EnumSet.noneOf(AclEntryFlag.class));
        }

        @Override
        public AclEntry.Builder setFlags(AclEntryFlag... flags) {
            return super.setFlags(flags);
        }
    }
}
