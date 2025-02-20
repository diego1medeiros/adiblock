package br.com.adiblock.utils;


import java.io.InputStream;
import java.io.IOException;
import java.io.ByteArrayOutputStream;
import java.net.URISyntaxException;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;


public class Relatorio<T> {

	
	private HttpServletResponse response;
	private FacesContext context;

	public Relatorio() {
		this.context = FacesContext.getCurrentInstance();
		this.response = (HttpServletResponse) this.context.getExternalContext().getResponse();
	}

	public void getRelatorio(List<T> lista) throws JRException, IOException, URISyntaxException{

		ServletContext context1 = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
		InputStream jasperStream = context1.getResourceAsStream("/relatorio/Blank_A4.jrxml");
		JasperReport relatorio = JasperCompileManager.compileReport(jasperStream);

		JasperPrint relatorioPreenchido = JasperFillManager.fillReport(relatorio, null,
				new JRBeanCollectionDataSource(lista));

		ByteArrayOutputStream baos = new ByteArrayOutputStream();

		JasperExportManager.exportReportToPdfStream(relatorioPreenchido, baos);
		
	        
	        response.reset();
		response.setContentType("application/pdf");
		response.setContentLength(baos.size());
		response.setHeader("Content-disposition", "inline; filename=relatorio.pdf");
		response.getOutputStream().write(baos.toByteArray());
		response.getOutputStream().flush();
		response.getOutputStream().close();
		context.responseComplete();
		
	}
	}
	
	


	

	


