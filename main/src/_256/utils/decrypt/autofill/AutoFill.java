package _256.utils.decrypt.autofill;

import java.io.File;
import java.util.Date;

public class AutoFill {

	protected String name;
	protected String value;
	protected Date expires;
	protected String path;
	protected String domain;
	protected boolean secure;
	protected boolean httpOnly;
	protected File autoFillStore;

	public AutoFill(String name, String value, Date expires, String path, String domain, boolean secure, boolean httpOnly, File autoFillStore) {
		this.name = name;
		this.value = value;
		this.expires = expires;
		this.path = path;
		this.domain = domain;
		this.secure = secure;
		this.httpOnly = httpOnly;
		this.autoFillStore = autoFillStore;
	}

	public String getName() {
		return name;
	}
}
