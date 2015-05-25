/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logic;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

/**
 *
 * @author Daniel
 */
public class GetIpLan {
    
    public static String getIp(){
        String ip;
        int i = 0;
        try {
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            while (i < 1) {
                NetworkInterface iface = interfaces.nextElement();
                // filters out 127.0.0.1 and inactive interfaces
                if (iface.isLoopback() || !iface.isUp())
                    continue;

                Enumeration<InetAddress> addresses = iface.getInetAddresses();
                while(i < 1) {
                    InetAddress addr = addresses.nextElement();
                    ip = addr.getHostAddress();
                    i++;
                    return ip;
                }
            }
        }
        catch (Exception e) {
        }
        return "SIN IP";
    }
}    
