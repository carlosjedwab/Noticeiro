package noticeiro.model;

public class FiltroInfo {
	String dataMinima;
	String dataMaxima;
	String horarioMinimo;
	String horarioMaximo;
	
	public FiltroInfo(String dataMinima, String dataMaxima, String horarioMinimo, String horarioMaximo) {
		this.dataMinima = dataMinima.equals("0") ? null: dataMinima;
		this.dataMaxima = dataMaxima.equals("0") ? null: dataMaxima;
		this.horarioMinimo = horarioMinimo.equals("0") ? null: horarioMinimo;
		this.horarioMaximo = horarioMaximo.equals("0") ? null: horarioMaximo;
	}
	
	public String gerarInfoDoFiltro() {
		String mensagem = "Filtros aplicados:";		
		if(dataMinima != null) {
			mensagem += "Mostrando notícias somente a partir do dia " + dataMinima + ".";
		}
		if(dataMaxima != null) {
			mensagem += "Data máxima de publicação até " + dataMaxima + ".";
		}
		if(horarioMinimo != null) {
			mensagem += "Mostrando publicações a partir do horário " + horarioMinimo + ".";
		}
		if(horarioMaximo != null) {
			mensagem += "Horário máximo de publicação até " + horarioMaximo + ".";
		}
		return mensagem;
	}
	
	public String getDataMinima() {
		return dataMinima;
	}
	public String getDataMaxima() {
		return dataMaxima;
	}
	public String getHorarioMinimo() {
		return horarioMinimo;
	}
	public String getHorarioMaximo() {
		return horarioMaximo;
	}
	
	
}
