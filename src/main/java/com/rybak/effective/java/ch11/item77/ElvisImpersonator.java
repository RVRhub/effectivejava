package com.rybak.effective.java.ch11.item77;


import java.io.*;

import com.rybak.effective.java.ch11.item77.enumSingleton.Elvis;
public class ElvisImpersonator {
    // Byte stream could not have come from real Elvis instance!
    private static final byte[] serializedForm = new byte[] {
            (byte)0xac, (byte)0xed, 0x00, 0x05, 0x73, 0x72, 0x00, 0x2a, 0x63, 0x6f, 0x6d, 0x2e, 0x72,
            0x79, 0x62, 0x61, 0x6b, 0x2e, 0x65, 0x66, 0x66, 0x65, 0x63, 0x74, 0x69, 0x76, 0x65, 0x2e,
            0x6a, 0x61, 0x76, 0x61, 0x2e, 0x63, 0x68, 0x31, 0x31, 0x2e, 0x69, 0x74, 0x65, 0x6d, 0x37,
            0x37, 0x2e, 0x45, 0x6c, 0x76, 0x69, 0x73, (byte)0xef, 0x62, (byte)0xb0, (byte)0x86, 0x29,
            0x13, (byte)0xe7, (byte)0xa8, 0x02, 0x00, 0x01, 0x5b, 0x00, 0x0d, 0x66, 0x61, 0x76, 0x6f,
            0x72, 0x69, 0x74, 0x65, 0x53, 0x6f, 0x6e, 0x67, 0x73, 0x74, 0x00, 0x13, 0x5b, 0x4c, 0x6a,
            0x61, 0x76, 0x61, 0x2f, 0x6c, 0x61, 0x6e, 0x67, 0x2f, 0x53, 0x74, 0x72, 0x69, 0x6e, 0x67,
            0x3b, 0x78, 0x70, 0x75, 0x72, 0x00, 0x13, 0x5b, 0x4c, 0x6a, 0x61, 0x76, 0x61, 0x2e, 0x6c,
            0x61, 0x6e, 0x67, 0x2e, 0x53, 0x74, 0x72, 0x69, 0x6e, 0x67, 0x3b, (byte)0xad, (byte)0xd2,
            0x56, (byte)0xe7, (byte)0xe9, 0x1d, 0x7b, 0x47, 0x02, 0x00, 0x00, 0x78, 0x70, 0x00, 0x00,
            0x00, 0x02, 0x74, 0x00, 0x09, 0x48, 0x6f, 0x75, 0x6e, 0x64, 0x20, 0x44, 0x6f, 0x67, 0x74,
            0x00, 0x10, 0x48, 0x65, 0x61, 0x72, 0x74, 0x62, 0x72, 0x65, 0x61, 0x6b, 0x20, 0x48, 0x6f, 0x74, 0x65, 0x6c

    };

    private static final byte[] serializedFormEnum = new byte[]{
            (byte)0xac, (byte)0xed, 0x00, 0x05, 0x7e, 0x72, 0x00, 0x38, 0x63, 0x6f, 0x6d, 0x2e, 0x72, 0x79, 0x62, 0x61,
            0x6b, 0x2e, 0x65, 0x66, 0x66, 0x65, 0x63, 0x74, 0x69, 0x76, 0x65, 0x2e, 0x6a, 0x61, 0x76, 0x61, 0x2e, 0x63,
            0x68, 0x31, 0x31, 0x2e, 0x69, 0x74, 0x65, 0x6d, 0x37, 0x37, 0x2e, 0x65, 0x6e, 0x75, 0x6d, 0x53, 0x69, 0x6e,
            0x67, 0x6c, 0x65, 0x74, 0x6f, 0x6e, 0x2e, 0x45, 0x6c, 0x76, 0x69, 0x73, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
            0x00, 0x00, 0x12, 0x00, 0x00, 0x78, 0x72, 0x00, 0x0e, 0x6a, 0x61, 0x76, 0x61, 0x2e, 0x6c, 0x61, 0x6e, 0x67,
            0x2e, 0x45, 0x6e, 0x75, 0x6d, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x12, 0x00, 0x00, 0x78, 0x70,
            0x74, 0x00, 0x08, 0x49, 0x4e, 0x53, 0x54, 0x41, 0x4e, 0x43, 0x45
    };

    public static void main(String[] args) throws Exception{
        // Initializes ElvisStealer.impersonator and returns
        // the real Elvis (which is Elvis.INSTANCE)
        Elvis elvis = (Elvis) deserialize(serializedFormEnum);
        //Elvis impersonator = ElvisStealer.impersonator;

        elvis.printFavorites();
      //  impersonator.printFavorites();

//        Elvis periodSer = Elvis.INSTANCE;
//        ByteArrayOutputStream bos = new ByteArrayOutputStream();
//        ObjectOutputStream out = new ObjectOutputStream(bos);
//        out.writeObject(periodSer);
//
//        System.out.println(getHex(bos.toByteArray()));
    }

    // Returns the object with the specified serialized form
    private static Object deserialize(byte[] sf) {
        try {
            InputStream is = new ByteArrayInputStream(sf);
            ObjectInputStream ois = new ObjectInputStream(is);
            return ois.readObject();
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

    static final String HEXES = "0123456789abcdef";

    public static String getHex( byte [] raw ) {
        if ( raw == null )
        {
            return null;
        }

        final StringBuilder hex = new StringBuilder( 2 * raw.length );
        for ( final byte b : raw )
        {
            hex.append("0x");
            hex.append(HEXES.charAt((b & 0xF0) >> 4))
                    .append(HEXES.charAt((b & 0x0F)));

            hex.append(", ");
        }
        return hex.toString();
    }
}