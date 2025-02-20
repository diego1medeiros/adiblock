package br.com.adiblock.enumeradores;

public enum TipoEmbalagem {

	BOMBONA("Bombona"),
	TAMBOR("Tambor"),
	BAG("Bag"),
	SACO("Saco"),
	CONJUNTO("Conjunto"),
	BALDE("Balde");

	private TipoEmbalagem (String embalagem) {
		this.setEmbalagem(embalagem);
	}

	private String embalagem;


	public static String[] getEmbalagmeComboBox() {
		String[] listaRetorno = new String[6];
		int i = 0;
		for (TipoEmbalagem tipoEmbalagem : TipoEmbalagem.values()) {
			listaRetorno[i] = tipoEmbalagem.getEmbalagem();
			i++;
		}
		return listaRetorno;
	}

	public String getEmbalagem() {
		return embalagem;
	}

	public void setEmbalagem(String embalagem) {
		this.embalagem = embalagem;
	}

}