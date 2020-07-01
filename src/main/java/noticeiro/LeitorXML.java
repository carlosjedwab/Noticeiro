package noticeiro;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;

import noticeiro.model.Publicacao;

public class LeitorXML {
	static public List<Publicacao> lerRSS(String url) {
		List<Publicacao> publicacoes = new ArrayList<>();
		
		URL feedSource = null;
		
		try {
			feedSource = new URL(url);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}

		SyndFeedInput input = new SyndFeedInput();
		
		try {
			SyndFeed feed = input.build(new XmlReader(feedSource));
			@SuppressWarnings("unchecked")
			List<SyndEntry> entries = feed.getEntries();
			for(SyndEntry entry: entries) {
				Publicacao publicacao = new Publicacao();
				publicacao.setTitle(entry.getTitle());
				publicacao.setDate(entry.getPublishedDate());
				publicacao.setLink(entry.getLink());
				publicacao.setFeedTitle(feed.getTitle());
				publicacao.setSource(url);
				publicacao.setDescription(entry.getDescription().getValue());
				publicacoes.add(publicacao);
				
			}
		} catch (IllegalArgumentException | FeedException | IOException e) {
			e.printStackTrace();
		}
		
		return publicacoes;
	}
	
	static public boolean checarValidadeDoRSS(String url) {
        try {
            URL feedSource = new URL(url);
            SyndFeedInput input = new SyndFeedInput();
            @SuppressWarnings("unused")
			SyndFeed feed = input.build(new XmlReader(feedSource));
        } catch (Exception e) {
            return false;
        }
        return true;

    }
	
	static public List<String> dividirTag (String tags) {
		List<String> listTag= new ArrayList<>();
		String regexString = "\"[^\"]*\"|(?=\\S)\\S*(?<=\\S)";
		Pattern pattern = Pattern.compile(regexString);
		Matcher matcher = pattern.matcher(tags);
		while(matcher.find()) {
			String group = matcher.group();
			if (group.contains("regex=")) {
				listTag.add(matcher.group());
			}
			else {
				listTag.add(matcher.group().replace("\"", ""));
			}
		}
		return listTag;
	}


	static public List<Publicacao> lerRSS(String url, List<String> tags) {
		List<Publicacao> publicacoes = new ArrayList<>();
		
		URL feedSource = null;
		
		try {
			feedSource = new URL(url);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}

		SyndFeedInput input = new SyndFeedInput();
		
		try {
			SyndFeed feed = input.build(new XmlReader(feedSource));
			@SuppressWarnings("unchecked")
			List<SyndEntry> entries = feed.getEntries();
			for(SyndEntry entry: entries) {
				
				if( filtrarPub(tags, entry.getTitle(), entry.getDescription().getValue() ) ) {
					Publicacao publicacao = new Publicacao();
					publicacao.setTitle(entry.getTitle());
					publicacao.setDate(entry.getPublishedDate());
					publicacao.setLink(entry.getLink());
					publicacao.setFeedTitle(feed.getTitle());
					publicacao.setSource(url);
					publicacao.setDescription(entry.getDescription().getValue());
					publicacoes.add(publicacao);
				}
				
			}
		} catch (IllegalArgumentException | FeedException | IOException e) {
			e.printStackTrace();
		}
		
		return publicacoes;
	}
	
	static public boolean filtrarPub (List<String> tags, String titulo, String descricao) {
		boolean temTag = false;
		 
		for(String tag: tags) {
			Pattern pattern;
			if (tag.contains("regex=")) {
				pattern = Pattern.compile(tag.replace("regex=", ""));
			}
			else {
				pattern = Pattern.compile("(?<![a-z])" + tag + "(?![a-z])", Pattern.CASE_INSENSITIVE);
			}
			Matcher tituloM = pattern.matcher(titulo);
			Matcher descricaoM = pattern.matcher(descricao);
			 if(tituloM.find() || descricaoM.find() ) 
				 temTag = true;	 		
		}
		
		if(tags.isEmpty()) {
			temTag = true;
		}
		
		return temTag;
	}
	

}
