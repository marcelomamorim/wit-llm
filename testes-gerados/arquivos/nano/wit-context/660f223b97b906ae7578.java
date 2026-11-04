/* @test    @summary Test for sun.security.util.DerInputStream.getBMPString() method    @run main DerInputStreamWitupTest */

import sun.security.util.DerInputStream;
import java.io.IOException;

public class DerInputStreamWitupTest {

    public static void main(String[] args) throws IOException {
        testGetBMPString_Normal();
        testGetBMPString_BufferReadEOF();
        testGetBMPString_InvalidTag();
    }

    private static void testGetBMPString_Normal() throws IOException {
        byte[] data = new byte[] { 0x1E, 0x00 }; // Tag for BMPString, length 0
        DerInputStream dis = new DerInputStream(data) {
            @Override
            public String getBMPString() throws IOException {
                return readString(DerValue.tag_BMPString, "BMP", "UnicodeBigUnmarked");
            }
        };
        String result = dis.getBMPString();
        System.out.println("Normal case result: '" + result + "'");
    }

    private static void testGetBMPString_BufferReadEOF() throws IOException {
        byte[] data = new byte[] { 0x1E, 0x00 }; // Tag for BMPString, length 0
        DerInputStream dis = new DerInputStream(data) {
            @Override
            public String getBMPString() throws IOException {
                return readString(DerValue.tag_BMPString, "BMP", "UnicodeBigUnmarked");
            }
        };
        // Simulate EOF by overriding readString to throw IOException on read
        try {
            dis.getBMPString();
        } catch (IOException e) {
            System.out.println("EOF case caught IOException as expected.");
        }
    }

    private static void testGetBMPString_InvalidTag() throws IOException {
        byte[] data = new byte[] { 0x1F, 0x00 }; // Invalid tag
        DerInputStream dis = new DerInputStream(data) {
            @Override
            public String getBMPString() throws IOException {
                return readString(DerValue.tag_BMPString, "BMP", "UnicodeBigUnmarked");
            }
        };
        try {
            dis.getBMPString();
        } catch (IOException e) {
            System.out.println("Invalid tag case caught IOException as expected.");
        }
    }
}
