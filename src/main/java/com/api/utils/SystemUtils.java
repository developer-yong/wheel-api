package com.api.utils;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class SystemUtils {

    public static String getHostName() {
        String hostName = System.getenv("COMPUTERNAME");
        if (hostName == null) {
            try {
                return (InetAddress.getLocalHost()).getHostName();
            } catch (UnknownHostException uhe) {
                // host = "hostname: hostname"
                String host = uhe.getMessage();
                if (host != null) {
                    int colon = host.indexOf(':');
                    if (colon > 0) {
                        return host.substring(0, colon);
                    }
                }
                return "UnknownHost";
            }
        }
        return hostName;
    }
}
