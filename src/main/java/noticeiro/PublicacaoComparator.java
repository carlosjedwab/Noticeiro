package noticeiro;

import java.util.Comparator;

import noticeiro.model.Publicacao;

public class PublicacaoComparator implements Comparator<Publicacao> {
	public int compare(Publicacao pub1, Publicacao pub2) {
		if(pub1.getDate() == null && pub2.getDate() == null) return 0;
		if(pub1.getDate() == null) return 1;
		if(pub2.getDate() == null) return -1;
		return pub2.getDate().compareTo(pub1.getDate());
	}
}
