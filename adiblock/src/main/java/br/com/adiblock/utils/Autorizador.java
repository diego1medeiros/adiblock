package br.com.adiblock.utils;


import javax.faces.application.NavigationHandler;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

import br.com.adiblock.dto.FuncionarioDto;


public class Autorizador implements PhaseListener {

	private static final long serialVersionUID = 1L;

	@Override
	public void afterPhase(PhaseEvent event) {
		FacesContext context = event.getFacesContext();
		String nomePagina = context.getViewRoot().getViewId();
		if ("/login.xhtml".equals(nomePagina)) {
			return;
		}
		FuncionarioDto funcionarioLogado = (FuncionarioDto) context.getExternalContext().getSessionMap()
				.get("funcionarioLogado");
		if (funcionarioLogado != null) {
			return;
		}
		NavigationHandler handler = context.getApplication().getNavigationHandler();
		handler.handleNavigation(context, null,"login?faces-redirect-true");
		context.renderResponse();
	}

	@Override
	public void beforePhase(PhaseEvent event) {
	}

	@Override
	public PhaseId getPhaseId() {
		return PhaseId.RESTORE_VIEW;
	}

}
