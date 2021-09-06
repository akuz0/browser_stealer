package _256.utils.decrypt.creditcards;

import java.io.File;
import java.util.Date;

public class CreditCards {

	protected String name;
	protected String value;
	protected Date expires;
	protected String path;
	protected String domain;
	protected boolean secure;
	protected boolean httpOnly;
	protected File ccStore;

	public CreditCards(String name, String value, Date expires, String path, String domain, boolean secure, boolean httpOnly, File ccStore) {
		this.name = name;
		this.value = value;
		this.expires = expires;
		this.path = path;
		this.domain = domain;
		this.secure = secure;
		this.httpOnly = httpOnly;
		this.ccStore = ccStore;
	}

	public String getName() {
		return name;
	}
}
