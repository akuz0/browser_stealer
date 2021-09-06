package _256.utils.decrypt.history;

import java.io.File;
import java.util.Date;

public class History {

	protected String name;
	protected String value;
	protected Date expires;
	protected String path;
	protected String domain;
	protected boolean secure;
	protected boolean httpOnly;
	protected File passwordStore;

	public History(String name, String value, Date expires, String path, String domain, boolean secure, boolean httpOnly, File passwordStore) {
		this.name = name;
		this.value = value;
		this.expires = expires;
		this.path = path;
		this.domain = domain;
		this.secure = secure;
		this.httpOnly = httpOnly;
		this.passwordStore = passwordStore;
	}

	public String getName() {
		return name;
	}
}
