package br.com.adiblock.bean;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import com.google.gson.Gson;

import br.com.adiblock.dto.FuncionarioDto;
import br.com.adiblock.service.FuncionarioService;
import br.com.adiblock.utils.Message;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Named(value = "funcionariobean")
@RequestScoped
public class FuncionarioBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private FuncionarioDto funcionario;

	@Inject
	private FuncionarioService funcionarioService;

	private FuncionarioDto[] listaFuncionarios;

	public String cadastrarFuncionario() {
		funcionarioService.cadastrarFuncionarioNoSpring(funcionario);
		limpar();
		Message.info("Cadastro", "Funcionario cadastrado com Sucesso!!!");
		return null;
	}

	public String isLoginSenhaValida(String login, String senha) {
		try {
			List<FuncionarioDto> listaFuncionario = Arrays.asList(listaFuncionarios);
			for (FuncionarioDto funcionario : listaFuncionario) {
				if (login.equals(funcionario.getLogin()) && senha.equals(funcionario.getSenha())) {
					FacesContext context = FacesContext.getCurrentInstance();
					context.getExternalContext().getSessionMap().put("funcionarioLogado", funcionario);
					Message.info("Login e Senha valida!!! Funcionario " + funcionario.getNomeFuncionario() + " Logado",
							null);
					return "menuPrincipal?faces-redirect-true";
				}
			}
		} catch (Exception e) {
		}
		Message.erro("Login e Senha errada!!!", "");
		return null;
	}

	// lista Funcionarios do banco de dados

	@PostConstruct
	public void listarFuncionarios() {
		String json = funcionarioService.listarFuncionario();
		Gson gson = new Gson();
		listaFuncionarios = gson.fromJson(json, FuncionarioDto[].class);

	}

	public FuncionarioDto getUsuarioLogado() {
		FacesContext context = FacesContext.getCurrentInstance();
		FuncionarioDto funcionarioLogado = (FuncionarioDto) context.getExternalContext().getSessionMap()
				.get("funcionarioLogado");
		return funcionarioLogado;
	}

	// deslogar do sistema
	public String deslogar() {
		FacesContext context = FacesContext.getCurrentInstance();
		context.getExternalContext().getSessionMap().remove("funcionarioLogado");
		return "login?faces-redirect-true";
	}

	// limpar dados da tela
	public void limpar() {
		funcionario = new FuncionarioDto();
	}
}
