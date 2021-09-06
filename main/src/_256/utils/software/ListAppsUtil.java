package _256.utils.software;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.sun.jna.platform.win32.Advapi32Util;
import com.sun.jna.platform.win32.Advapi32Util.InfoKey;
import com.sun.jna.platform.win32.WinNT;
import com.sun.jna.platform.win32.WinReg;
import com.sun.jna.platform.win32.WinReg.HKEYByReference;

public class ListAppsUtil {

	public static Map<String, SoftwareUtil> getInstalledApps(boolean includeUpdates) {
		SoftwareListUtil list = new SoftwareListUtil();
		getUninstallKeyPrograms(list, WinReg.HKEY_LOCAL_MACHINE, "Software\\Microsoft\\Windows\\CurrentVersion\\Uninstall", includeUpdates);
		String[] subKeys = Advapi32Util.registryGetKeys(WinReg.HKEY_USERS);
		for (String subKey : subKeys) {
			try {
				getUninstallKeyPrograms(list, WinReg.HKEY_USERS, subKey + "\\Software\\Microsoft\\Windows\\CurrentVersion\\Uninstall", includeUpdates);
			} catch (Exception e) {
			}
		}
		return list;
	}

	static SoftwareListUtil getUninstallKeyPrograms(SoftwareListUtil list, WinReg.HKEY hkey, String rootKey, boolean includeUpdates) {
		String[] subKeys = Advapi32Util.registryGetKeys(hkey, rootKey);
		for (String subKeyName : subKeys) {
			Map<String, Object> vals = Advapi32Util.registryGetValues(hkey, rootKey + "\\" + subKeyName);
			if (vals.get("SystemComponent") == null || !vals.get("SystemComponent").equals(1)) {
				String name = (String) vals.get("DisplayName");
				;
				String version = (String) vals.get("DisplayVersion");
				String installLocation = (String) vals.get("InstallLocation");
				String icon = (String) vals.get("DisplayIcon");
				String publisher = (String) vals.get("Publisher");
				HKEYByReference subKey = Advapi32Util.registryGetKey(hkey, rootKey + "\\" + subKeyName, WinNT.KEY_READ);
				InfoKey infoKey = Advapi32Util.registryQueryInfoKey(subKey.getValue(), 0);
				LocalDateTime installDate = infoKey.lpftLastWriteTime.toDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
				if (vals.get("WindowsInstaller") == null || !vals.get("WindowsInstaller").equals(1)) {
					String releaseType = (String) vals.get("ReleaseType");
					SoftwareUtil softwareUtils = new SoftwareUtil(rootKey + "\\" + subKeyName, name, publisher, installLocation, installDate, version, icon);
					Pattern windowsUpdateRegex = Pattern.compile("KB[0-9]{6}$");
					Matcher m = windowsUpdateRegex.matcher(subKeyName);
					if (m.find() || (vals.get("ParentKeyName")) != null && !vals.get("ParentKeyName").equals("") || (releaseType != null && (releaseType.equals("Security Update") || releaseType.equals("Update Rollup") || releaseType.equals("Hotfix")))) {
						if (name != null && !name.equals("") && includeUpdates) {
							list.put(softwareUtils.getDisplayName(), softwareUtils);
						}
					} else {
						String uninstallValue = (String) vals.get("UninstallString");

						if (uninstallValue != null && !uninstallValue.equals("") && name != null && !name.equals("")) {
							list.put(softwareUtils.getDisplayName(), softwareUtils);
						}
					}
				} else {
					String msiKeyName = getInstallerKeyNameFromGuid(subKeyName);
					Map<String, Object> msiVals = Advapi32Util.registryGetValues(hkey, "Software\\Classes\\Installer\\Products\\" + msiKeyName);
					String msiName = (String) msiVals.get("ProductName");
					if (msiName != null && !msiName.equals("")) {
						name = msiName;
					}
					String msiIcon = (String) msiVals.get("ProductIcon");
					if (msiIcon != null && !msiIcon.equals("")) {
						icon = msiIcon;
					}
					if (!name.equals("")) {
						SoftwareUtil softwareUtils = new SoftwareUtil(rootKey + "\\" + subKeyName, name, publisher, installLocation, installDate, version, icon);
						list.put(softwareUtils.getDisplayName(), softwareUtils);
					}
				}
			}
		}
		return list;
	}

	static String getInstallerKeyNameFromGuid(String subKeyName) {
		String key = subKeyName.replace("{", "").replace("}", "");
		String[] parts = key.split("-");
		String msiName = "";
		for (int i = 0; i <= 2; i++) {
			msiName += new StringBuilder().append(parts[i]).reverse();
		}
		for (int i = 3; i <= 4; i++) {
			for (int j = 0; j < parts[i].length(); j += 2) {
				msiName += parts[i].charAt(j + 1);
				msiName += parts[i].charAt(j);
			}
		}
		return msiName;
	}
}
