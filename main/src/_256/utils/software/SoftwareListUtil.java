package _256.utils.software;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class SoftwareListUtil implements Map<String, SoftwareUtil> {

	Map<String, SoftwareUtil> list = new HashMap<>();

	@Override
	public int size() {
		return list.size();
	}

	@Override
	public boolean isEmpty() {
		return list.isEmpty();
	}

	@Override
	public boolean containsKey(Object key) {
		return list.containsKey(key);
	}

	@Override
	public boolean containsValue(Object value) {
		return list.containsValue(value);
	}

	@Override
	public SoftwareUtil get(Object key) {
		return list.get(key);
	}

	@Override
	public SoftwareUtil put(String key, SoftwareUtil newSoft) {
		SoftwareUtil softwareUtils = list.get("key");
		if (softwareUtils != null) {
			if (softwareUtils.getIcon() == null && newSoft.getIcon() != null) {
				softwareUtils.setIcon(newSoft.getIcon());
			}
			if (softwareUtils.getInstallLocation() == null && newSoft.getInstallLocation() != null) {
				softwareUtils.setInstallLocation(newSoft.getInstallLocation());
			}
			if (softwareUtils.getVersion() == null && newSoft.getVersion() != null) {
				softwareUtils.setVersion(newSoft.getVersion());
			}
			softwareUtils.addRegKeys(newSoft.getRegKeys());
			return softwareUtils;
		}
		return list.put(key, newSoft);
	}

	@Override
	public SoftwareUtil remove(Object key) {
		return list.remove(key);
	}

	@Override
	public void putAll(Map<? extends String, ? extends SoftwareUtil> m) {
		list.putAll(m);
	}

	@Override
	public void clear() {
		list.clear();
	}

	@Override
	public Set<String> keySet() {
		return list.keySet();
	}

	@Override
	public Collection<SoftwareUtil> values() {
		return list.values();
	}

	@Override
	public Set<Entry<String, SoftwareUtil>> entrySet() {
		return list.entrySet();
	}
}
