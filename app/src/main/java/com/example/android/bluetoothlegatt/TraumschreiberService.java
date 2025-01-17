package com.example.android.bluetoothlegatt;

import android.util.Log;

import java.util.UUID;


class TraumschreiberService {

    private final static String VENDOR_PREFIX = "74:72:61:75:6D:";
    // Names chosen according to the python tflow_edge Traumschreiber.py
    public final static UUID BIOSIGNALS_UUID = UUID.fromString("faa7b588-19e5-f590-0545-c99f193c5c3e");
    public final static UUID LEDS_UUID = UUID.fromString("fcbea85a-4d87-18a2-2141-0d8d2437c0a4");
    private final String mTraumschreiberDeviceAddress;

    public TraumschreiberService(String traumschreiberDeviceAddress) {
        this.mTraumschreiberDeviceAddress = traumschreiberDeviceAddress;
    }

    public static boolean isTraumschreiberAddress(String bluetoothDeviceAddress) {
        return bluetoothDeviceAddress.startsWith(VENDOR_PREFIX);
    }

    /***
     * decompress takes a bytearray data_bytes and converts it to integers, according to the way the Traumschreiber transmits the data via bluetooth
     * @param data_bytes array of bytes received
     * @return int[] data_ints of the datapoint values as integers
     */
    public static int[] decompress(byte[] data_bytes) {
        int bytelengthDatapoint = 2;
        int[] data_ints = new int[data_bytes.length / bytelengthDatapoint];
        Log.d("Decompressing", "decompress: " + String.format("%02X %02X ", data_bytes[0], data_bytes[1]));
        for (int i = 0; i < data_bytes.length / bytelengthDatapoint; i++) {
            int new_int = (data_bytes[i * bytelengthDatapoint + 1]) << 8 | (data_bytes[i * bytelengthDatapoint]) & 0xff;
            data_ints[i] = new_int;
        }
        return data_ints;
    }
}
