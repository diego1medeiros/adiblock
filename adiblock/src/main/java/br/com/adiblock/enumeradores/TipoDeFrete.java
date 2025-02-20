package br.com.adiblock.enumeradores;

import java.util.Locale;
import java.util.ResourceBundle;


public enum TipoDeFrete {

	FOB("frete.fob"),
    CIF("frete.cif");

	private String tipoDeFrete;

	private TipoDeFrete(String tipoDeFrete) {
		this.setTipoDeFrete(tipoDeFrete);
	}

	public String getTipoDeFrete() {
		  Locale ptBrLocale = new Locale("pt", "BR");
	        ResourceBundle bundle = ResourceBundle.getBundle("TipoDeFrete", ptBrLocale);
	        return bundle.getString(tipoDeFrete);
	}
	
	
	
	public void setTipoDeFrete(String tipoDeFrete) {
		this.tipoDeFrete = tipoDeFrete;
	}

	public static String[] gettipoFreteComboBox() {
		String[] listaRetorno = new String[2];
		int i = 0;
        Locale.setDefault(new Locale("pt", "BR"));

		for (TipoDeFrete tipoDeFrete : TipoDeFrete.values()) {
			listaRetorno[i] = tipoDeFrete.getTipoDeFrete();
			i++;
		}
		return listaRetorno;
	}

}