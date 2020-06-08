package noticeiro;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

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
				publicacao.setLink(entry.getLink());
				publicacao.setSource(feed.getTitle());
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
}
