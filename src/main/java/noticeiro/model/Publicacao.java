package noticeiro.model;

import java.util.Date;

public class Publicacao {
	String title;     // publication name 							(ex: "Breaking news: ...")
	Date date;
	// pubDate
	// pubTime
	// imageSrc
	String link;      // the link's address to the publication 		(ex: "https://g1.globo.com/.../Breaking-news-...")
	String source;    // the URL to the rss feed 					(ex: "https://g1.globo.com/rss/g1/")
	String feedTitle; // the name of the publication's publisher 	(ex: "G1")
	String description; //the description of the publication
	
	public String getFeedTitle() {
		return feedTitle;
	}
	public void setFeedTitle(String feedTitle) {
		this.feedTitle = feedTitle;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
}