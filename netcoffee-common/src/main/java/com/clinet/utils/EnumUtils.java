package com.clinet.utils;

public final class EnumUtils {

	public static enum Status{
		ON(1, "online"),
		OFF(0, "offline");
		
		private Status(int code, String name) {
			this.code = code;
			this.name = name;
		}
		
		private String name;
		private int code;
		
		public int getCode() {
			return code;
		}
		
		public String getName() {
			return name;
		}
	}
	
	public static enum Command{
		MESSAGE(0, "Message"),
		COMMAND(1, "Command");
		
		private Command(int code, String name) {
			this.code = code;
			this.name = name;
		}
		
		private String name;
		private int code;
		
		public int getCode() {
			return code;
		}
		
		public String getName() {
			return name;
		}
		
		@Override
		public String toString() {
			return String.valueOf(code);
		}
	}
}
