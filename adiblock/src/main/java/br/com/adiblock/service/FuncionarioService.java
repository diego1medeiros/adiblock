package br.com.adiblock.service;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import br.com.adiblock.dto.FuncionarioDto;


public class FuncionarioService {

	
	private static final String BASE_URI = "http://localhost:8082";

	public String listarFuncionario() {
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(BASE_URI).path("/funcionario/adiblock");
		String response = target.request(MediaType.APPLICATION_JSON).get(String.class);
		return response;
	}

	public String cadastrarFuncionarioNoSpring(FuncionarioDto dto) {
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(BASE_URI).path("/funcionario/adiblock");
		String response = target.request(MediaType.APPLICATION_JSON)
				.post(Entity.entity(dto, MediaType.APPLICATION_JSON), String.class);
		return response;
	}

	public String excluirFuncionarioNoSpring(Long id) {
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(BASE_URI)
				.path("/funcionario/adiblock/{id}").resolveTemplate("id", id);
		String response = target.request(MediaType.APPLICATION_JSON).delete(String.class);
		return response;
	}

	public String atualizarrFuncionarioNoSpring(FuncionarioDto dto) {
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(BASE_URI).path("/funcionario/adiblock");
		String response = target.request(MediaType.APPLICATION_JSON)
				.put(Entity.entity(dto, MediaType.APPLICATION_JSON), String.class);
		return response;
	}
}
