package noticeiro;

import java.util.Comparator;

import noticeiro.model.Publicacao;

public class PublicacaoComparator implements Comparator<Publicacao> {
	public int compare(Publicacao pub1, Publicacao pub2) {
		return pub2.getDate().compareTo(pub1.getDate());
	}
}
