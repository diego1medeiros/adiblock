package br.com.adiblock.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;
import com.google.gson.Gson;

import br.com.adiblock.dto.EmpresaDto;
import br.com.adiblock.dto.FuncionarioDto;
import br.com.adiblock.service.EmpresaService;
import br.com.adiblock.utils.BuscaCep;
import br.com.adiblock.utils.Message;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Named(value = "empresabean")
@RequestScoped
public class EmpresaBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private FuncionarioBean bean = new FuncionarioBean();

	private EmpresaDto[] listaDeEmpresas;

	@Inject
	private EmpresaDto empresaDto;

	@Inject
	private EmpresaService empresaService;
	private FuncionarioDto funcionario;

//cadastar e atualizar empresas do banco de dados
	public String cadastrarEmpresa() {
		try {
			if (empresaDto.getId() == null) {
				FuncionarioDto funcionario = bean.getUsuarioLogado();
				empresaDto.getFuncionario().setId(funcionario.getId());
				empresaService.cadastrarEmpresaNoSpring(empresaDto);
				Message.info("Cadastro", "Empresa  cadastrado com Sucesso!!!");
			} else {
				empresaService.cadastrarEmpresaNoSpring(empresaDto);
				Message.info("Atualização", "Empresa  atualizado com Sucesso!!!");
			}
			listarEmpresas();
			limpar();
		} catch (Exception e) {
		}
		return null;
	}

	// lista empresas do banco de dados

	@PostConstruct
	public void listarEmpresas() {
		funcionario = bean.getUsuarioLogado();
		if (funcionario.getLogin().equals("adiblock")) {
			String json = empresaService.listarEmpresa();
			Gson gson = new Gson();
			listaDeEmpresas = gson.fromJson(json, EmpresaDto[].class);
		} else {
			String json = empresaService.listarEmpresasComFuncionarioLogado(funcionario.getId());
			Gson gson = new Gson();
			listaDeEmpresas = gson.fromJson(json, EmpresaDto[].class);
		}
	}

	// excluir empresas do banco de dados
	public void excluirEmpresa(EmpresaDto empresaDto) {
		try {
			empresaService.excluirEmpresaNoSpring(empresaDto.getId());
			Message.info("Excluir", "Empresa excluido com Sucesso!!!");
			listarEmpresas();
			limpar();
		} catch (Exception e) {
		}
	}

	// busca Cep
	public void encontraCEP() {
		BuscaCep buscaCep = new BuscaCep(empresaDto.getEndereco().getCep());

		if (buscaCep.getResultado() == 1) {
			empresaDto.getEndereco().setRua(buscaCep.getTipoLogradouro() + " " + buscaCep.getLogradouro());
			empresaDto.getEndereco().setEstado(buscaCep.getEstado());
			empresaDto.getEndereco().setCidade(buscaCep.getCidade());
			empresaDto.getEndereco().setBairro(buscaCep.getBairro());
		} else {

			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "CEP não foi encontrado", "CEP não foi encontrado"));
		}
	}

	// limpar dados da tela
	public void limpar() {
		empresaDto = new EmpresaDto();
	}

	public List<SelectItem> getListaNome() {
		List<SelectItem> lista = new ArrayList<>();
		FuncionarioDto funcionario = bean.getUsuarioLogado();
		String json = empresaService.listarEmpresasComFuncionarioLogado(funcionario.getId());
		Gson gson = new Gson();
		EmpresaDto[] listaDeEmpresas = gson.fromJson(json, EmpresaDto[].class);
		for (EmpresaDto empresaDto : listaDeEmpresas) {
			lista.add(new SelectItem(empresaDto.getId(), empresaDto.getRazaoSocial()));
		}
		return lista;
	}

	public void encontraEmpresa() {
		String json = empresaService.buscaEmpresa(empresaDto.getId());
		Gson gson = new Gson();
		EmpresaDto empresa = gson.fromJson(json, EmpresaDto.class);
		empresaDto = new EmpresaDto(empresa.getId(), empresa.getRazaoSocial(), empresa.getContato(), empresa.getCnpj(),
				empresa.getTelefone(), empresa.getEmail(), empresa.getInscricao(), empresa.getFuncionario(),
				empresa.getEndereco());

	}

}
