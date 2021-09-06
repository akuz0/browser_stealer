package _256.utils.software;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class SoftwareUtil {

	public SoftwareUtil(String regKey, String displayName, String publisher, String installLocation, LocalDateTime installDate, String version, String icon) {
		addRegKey(regKey);
		setDisplayName(displayName);
		setPublisher(publisher);
		setInstallLocation(installLocation);
		setInstallDate(installDate);
		setVersion(version);
		setIcon(icon);
	}

	private Set<String> regKeys = new HashSet<>();
	private String displayName;
	private String publisher;
	private String installLocation;
	private String version;
	private String icon;
	private LocalDateTime installDate;

	public void addRegKey(String key) {
		if (key == null) {
			return;
		}
		regKeys.add(key);
	}

	public void addRegKeys(Collection<String> keys) {
		regKeys.addAll(keys);
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = nullifEmpty(displayName);
	}

	public String getInstallLocation() {
		return installLocation;
	}

	public void setInstallLocation(String installLocation) {
		this.installLocation = nullifEmpty(installLocation);
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = nullifEmpty(version);
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = nullifEmpty(icon);
	}

	private String nullifEmpty(String string) {
		return string == null || string.equals("") ? null : string;
	}

	public Set<String> getRegKeys() {
		return regKeys;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = nullifEmpty(publisher);
	}

	public void setInstallDate(LocalDateTime installDate) {
		this.installDate = installDate;
	}
}
