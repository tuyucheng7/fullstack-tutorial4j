package cn.tuyucheng.taketoday.easymock;

import java.util.List;

public class TuyuchengReader {

	private ArticleReader articleReader;
	private ArticleWriter articleWriter;

	public TuyuchengReader() {
	}

	public TuyuchengReader(ArticleReader articleReader) {
		this.articleReader = articleReader;
	}

	public TuyuchengReader(ArticleWriter writer) {
		this.articleWriter = writer;
	}

	public TuyuchengReader(ArticleReader articleReader, ArticleWriter writer) {
		this.articleReader = articleReader;
		this.articleWriter = writer;
	}

	public TuyuchengArticle readNext() {
		return articleReader.next();
	}

	public List<TuyuchengArticle> readTopic(String topic) {
		return articleReader.ofTopic(topic);
	}

	public String write(String title, String content) {
		return articleWriter.write(title, content);
	}
}