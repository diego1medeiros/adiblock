package br.com.adiblock.utils;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.text.MessageFormat;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;
import javax.servlet.ServletContext;

import br.com.adiblock.dto.VendaDto;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.faces.context.FacesContext;
import javax.mail.BodyPart;

import java.util.List;
import java.util.Properties;
import java.util.logging.Logger;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;


public class EnviarEmail<T> {


	public void getEnviarEmail(List<T> lista, VendaDto venda) throws JRException, IOException, URISyntaxException, MessagingException {
	   
		File pdfTemp = salvarPDFTemporario(lista);
	    enviarEmailComAnexo(pdfTemp, venda);
	}


	public File salvarPDFTemporario(List<T> lista) throws JRException, IOException, URISyntaxException {
	    ServletContext context1 = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
	    InputStream jasperStream = context1.getResourceAsStream("/relatorio/Blank_A4.jrxml");
	    JasperReport relatorio = JasperCompileManager.compileReport(jasperStream);

	    JasperPrint relatorioPreenchido = JasperFillManager.fillReport(relatorio, null,
	            new JRBeanCollectionDataSource(lista));

	    // Criar um arquivo temporário
	    File tempFile = File.createTempFile("relatorio-temp", ".pdf");

	    try (FileOutputStream fos = new FileOutputStream(tempFile)) {
	        JasperExportManager.exportReportToPdfStream(relatorioPreenchido, fos);
	    }

	    return tempFile;
	}


	public void enviarEmailComAnexo(File anexo,VendaDto venda) throws MessagingException, IOException {
		
	    
	    String remetente = "diegoravicielle@gmail.com";
	    String senha =  System.getenv("EMAIL_PASSWORD"); // Obtém a senha da variável de ambiente
	      
	      if (senha == null || senha.isEmpty()) {
	          throw new MessagingException("Senha do e-mail não especificada. Verifique a variável de ambiente EMAIL_PASSWORD.");
	      } 
	      
	      String destinatario = venda.getEmpresa().getEmail();
	      String assunto = "Proposta de Fornecimento";
	
	      Properties props = new Properties();
	      try (InputStream input = getClass().getClassLoader().getResourceAsStream("email.properties")) {
	          props.load(input);
	      } catch (IOException e) {
	          e.printStackTrace(); // Trate a exceção apropriadamente
	      } 
	      String templateCorpo = props.getProperty("corpo.email");
	      String corpo = MessageFormat.format(templateCorpo, venda.getEmpresa().getContato());


	     // Properties props = new Properties();
	      props.put("mail.smtp.host", "smtp.gmail.com");
	      props.put("mail.smtp.socketFactory.port", "465");
	      props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
	      props.put("mail.smtp.auth", "true");
	      props.put("mail.smtp.port", "465");
	      props.put("mail.smtp.mime.charset", "UTF-8");

	      Session session = Session.getInstance(props, new Authenticator() {
	          protected PasswordAuthentication getPasswordAuthentication() {
	              return new PasswordAuthentication(remetente, senha);
	          }
	      });

	      Message message = new MimeMessage(session);
	      message.setFrom(new InternetAddress(remetente));
	      message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destinatario));
	      message.setSubject(assunto);
	      message.setHeader("Content-Type", "text/html; charset=UTF-8");

	      MimeMultipart multipart = new MimeMultipart("related");

	      // Parte de texto
	      BodyPart messageBodyPart = new MimeBodyPart();
	      messageBodyPart.setContent(corpo, "text/html; charset=utf-8");
	      multipart.addBodyPart(messageBodyPart);

	      // Parte da imagem
	      MimeBodyPart imagePart = new MimeBodyPart();
	        InputStream imageStream = getClass().getClassLoader().getResourceAsStream("logoadiblock.png");
	        imagePart.setDataHandler(new DataHandler(new ByteArrayDataSource(imageStream, "image/png")));
	        imagePart.setHeader("Content-ID", "<image_id>");
	        imagePart.setDisposition(MimeBodyPart.INLINE);
	        multipart.addBodyPart(imagePart);

	      // Parte do anexo
	      MimeBodyPart attachmentPart = new MimeBodyPart();
	      DataSource source = new FileDataSource(anexo);
	      attachmentPart.setDataHandler(new DataHandler(source));
	      attachmentPart.setFileName(anexo.getName());
	      multipart.addBodyPart(attachmentPart);
	      message.setContent(multipart);

	      Transport.send(message);
	  }


	
	
	

}
