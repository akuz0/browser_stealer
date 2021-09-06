package _256.utils.decrypt.cookie;

import java.io.File;
import java.util.Date;

public class Cookie {

	protected String name;
	protected String value;
	protected Date expires;
	protected String path;
	protected String domain;
	protected boolean secure;
	protected boolean httpOnly;
	protected File cookieStore;

	public Cookie(String name, String value, Date expires, String path, String domain, boolean secure, boolean httpOnly, File cookieStore) {
		this.name = name;
		this.value = value;
		this.expires = expires;
		this.path = path;
		this.domain = domain;
		this.secure = secure;
		this.httpOnly = httpOnly;
		this.cookieStore = cookieStore;
	}

	public String getName() {
		return name;
	}
}
