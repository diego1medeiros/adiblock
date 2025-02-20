package br.com.adiblock.service;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import br.com.adiblock.dto.EmpresaDto;

public class EmpresaService {

	private static final String BASE_URI = "http://localhost:8082";

	public String listarEmpresa() {
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(BASE_URI).path("/empresa/adiblock");
		String response = target.request(MediaType.APPLICATION_JSON).get(String.class);
		return response;
	}

	public String cadastrarEmpresaNoSpring(EmpresaDto dto) {
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(BASE_URI).path("/empresa/adiblock");
		String response = target.request(MediaType.APPLICATION_JSON)
				.post(Entity.entity(dto, MediaType.APPLICATION_JSON), String.class);
		return response;
	}

	public String excluirEmpresaNoSpring(Long id) {
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(BASE_URI)
				.path("/empresa/adiblock/{id}").resolveTemplate("id", id);
		String response = target.request(MediaType.APPLICATION_JSON).delete(String.class);
		return response;
	}

	public String atualizarrEmpresaNoSpring(EmpresaDto dto) {
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(BASE_URI).path("/empresa/adiblock");
		String response = target.request(MediaType.APPLICATION_JSON)
				.put(Entity.entity(dto, MediaType.APPLICATION_JSON), String.class);
		return response;
	}
	
	public String buscaEmpresa(Long id) {
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(BASE_URI)
				.path("/empresa/adiblock/{id}").resolveTemplate("id", id);
		String response = target.request(MediaType.APPLICATION_JSON).get(String.class);
		return response;
	}

	public String listarEmpresasComFuncionarioLogado(Long id) {
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(BASE_URI)
				.path("/empresa/adiblock/buscar/{id}").resolveTemplate("id", id);
		String response = target.request(MediaType.APPLICATION_JSON).get(String.class);
		return response;
	}	
	
}
