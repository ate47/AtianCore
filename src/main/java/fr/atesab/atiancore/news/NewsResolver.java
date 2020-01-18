package fr.atesab.atiancore.news;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class NewsResolver extends Thread {
	private final Gson GSON = new GsonBuilder().create();
	private String news;
	private File storage;
	private NewsData data;

	public NewsResolver(String news, File storage) {
		this.news = news;
		this.storage = storage;
	}

	private void downloadData() {
		BufferedReader reader = null;
		URLConnection conn;
		try {
			// creating connection
			conn = new URL(news).openConnection();
			conn.setReadTimeout(7_500);
			conn.setConnectTimeout(7_500);
			conn.setDoOutput(true);
			conn.connect();
			// reading response
			reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

			setData(GSON.fromJson(reader, NewsData.class));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				reader.close();
			} catch (Exception e) {
			}
		}
	}

	public synchronized NewsData getData() {
		return data;
	}

	public File getStorage() {
		return storage;
	}

	@Override
	public void run() {
		this.downloadData();
	}

	public synchronized void setData(NewsData data) {
		this.data = data;
	}
}
