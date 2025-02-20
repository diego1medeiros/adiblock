package br.com.adiblock.service;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import br.com.adiblock.dto.ProdutoDto;


public class ProdutoService {
	
	private static final String BASE_URI = "http://localhost:8082";

	public String listarProduto() {
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(BASE_URI).path("/produto/adiblock");
		String response = target.request(MediaType.APPLICATION_JSON).get(String.class);
		return response;
	}

	public String cadastrarProdutoNoBancoDeDados(ProdutoDto dto) {
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(BASE_URI).path("/produto/adiblock");
		String response = target.request(MediaType.APPLICATION_JSON)
				.post(Entity.entity(dto, MediaType.APPLICATION_JSON), String.class);
		return response;
	}

	public String excluirProdutoNoBancoDeDados(Long id) {
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(BASE_URI)
				.path("/produto/adiblock/{id}").resolveTemplate("id", id);
		String response = target.request(MediaType.APPLICATION_JSON).delete(String.class);
		return response;
	}

	public String atualizarrProdutoNoBancoDeDados(ProdutoDto dto) {
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(BASE_URI).path("/produto/adiblock");
		String response = target.request(MediaType.APPLICATION_JSON)
				.put(Entity.entity(dto, MediaType.APPLICATION_JSON), String.class);
		return response;
	}

	public String buscarProduto(Long id) {
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(BASE_URI)
				.path("/produto/adiblock/{id}").resolveTemplate("id", id);
		String response = target.request(MediaType.APPLICATION_JSON).get(String.class);
		return response;
	}
	
	public String buscarProdutosPeloLogin(Long id) {
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(BASE_URI)
				.path("/produto/adiblock/buscar/{id}").resolveTemplate("id", id);
		String response = target.request(MediaType.APPLICATION_JSON).get(String.class);
		return response;
	}


}
