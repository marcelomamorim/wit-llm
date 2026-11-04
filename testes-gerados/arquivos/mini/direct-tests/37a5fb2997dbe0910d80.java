/*
 * @test
 * @summary Test sun.security.util.DerValue.toDerInputStream() method behavior
 * @run main DerValueWitupTest
 */

import sun.security.util.DerValue;
import sun.security.util.DerInputStream;
import sun.security.util.DerInputBuffer;
import java.io.IOException;

public class DerValueWitupTest {
    public static void main(String[] args) throws Exception {
        testToDerInputStreamWithSequenceTag();
        testToDerInputStreamWithSetTag();
        testToDerInputStreamWithInvalidTag();
    }

    private static void testToDerInputStreamWithSequenceTag() throws Exception {
        // tag_Sequence = 0x30 (universal constructed sequence)
        byte sequenceTag = DerValue.tag_Sequence;
        byte[] dummyData = {0x01, 0x02, 0x03};
        DerValue dv = new DerValue(sequenceTag, dummyData);

        DerInputStream dis = dv.toDerInputStream();
        if (dis == null) {
            throw new RuntimeException("toDerInputStream returned null for tag_Sequence");
        }
    }

    private static void testToDerInputStreamWithSetTag() throws Exception {
        // tag_Set = 0x31 (universal constructed set)
        byte setTag = DerValue.tag_Set;
        byte[] dummyData = {0x04, 0x05, 0x06};
        DerValue dv = new DerValue(setTag, dummyData);

        DerInputStream dis = dv.toDerInputStream();
        if (dis == null) {
            throw new RuntimeException("toDerInputStream returned null for tag_Set");
        }
    }

    private static void testToDerInputStreamWithInvalidTag() {
        // Use a tag that is neither tag_Sequence nor tag_Set
        byte invalidTag = DerValue.tag_Integer; // 0x02
        byte[] dummyData = {0x07, 0x08, 0x09};
        DerValue dv = new DerValue(invalidTag, dummyData);

        try {
            dv.toDerInputStream();
            throw new RuntimeException("Expected IOException not thrown for invalid tag");
        } catch (IOException e) {
            String msg = e.getMessage();
            if (!msg.contains("toDerInputStream rejects tag type")) {
                throw new RuntimeException("Unexpected IOException message: " + msg);
            }
        }
    }
}
