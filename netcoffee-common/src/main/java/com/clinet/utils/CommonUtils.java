package com.clinet.utils;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.UUID;

import javax.swing.JProgressBar;

public class CommonUtils {
	
	public static void main(String[] args) {
		System.out.println(getRandom8Chars());
	}

	public static String getRandom8Chars() {
		String result = "";
		try {
			UUID uID = UUID.randomUUID();
			result = uID.toString().substring(0, 8);// first 8 chars
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return result;
	}

	public static void centerForm(Component frm) {
		Dimension childSize = frm.getSize();
		Dimension parentSize = Toolkit.getDefaultToolkit().getScreenSize();
		centerForm(frm, parentSize, childSize);
	}

	public static void centerForm(Component parent, Component child) {
		Dimension childSize = child.getSize();
		Dimension parentSize = parent.getSize();
		centerForm(child, parentSize, childSize);
	}

	public static void centerForm(Component cp, Dimension parent,
			Dimension child) {
		cp.setLocation((parent.width - child.width) / 2, (parent.height - child.height) / 2);
	}

	public static String getLocalIPAddress() {
		String retVal = "127.0.0.1";
		try {
			if (System.getProperty("os.name").toLowerCase().startsWith("windows")) {
				InetAddress thisIp = InetAddress.getLocalHost();
				retVal = thisIp.getHostAddress();
			} else {
				InetAddress thisIp = getFirstNonLoopbackAddress();
				if (thisIp != null) {
					retVal = thisIp.getHostAddress();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return retVal;
	}

	private static InetAddress getFirstNonLoopbackAddress()
			throws SocketException {
		Enumeration<NetworkInterface> ifaces = NetworkInterface.getNetworkInterfaces();
		while (ifaces.hasMoreElements()) {
			NetworkInterface iface = ifaces.nextElement();
			if (iface.getDisplayName().equals("eth0")) {
				Enumeration<InetAddress> addresses = iface.getInetAddresses();
				while (addresses.hasMoreElements()) {
					InetAddress addr = addresses.nextElement();
					if (addr instanceof Inet4Address && !addr.isLoopbackAddress()) {
						return addr;
					}
				}
			}
		}
		return null;
	}
	
	public JProgressBar getProgressBar(){
		JProgressBar bar = new JProgressBar();
		
		return bar;
	}
}
