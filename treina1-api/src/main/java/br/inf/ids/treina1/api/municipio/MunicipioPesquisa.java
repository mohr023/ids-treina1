package br.inf.ids.treina1.api.municipio;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import com.ordnaelmedeiros.jpafluidselect.querybuilder.QueryBuilder;
import com.ordnaelmedeiros.jpafluidselect.querybuilder.select.pagination.PaginationResult;

@RequestScoped
public class MunicipioPesquisa {

	@Inject
	EntityManager em;
	
	public PaginationResult<Municipio> executar(Integer pagina, String valor) {
		
		return new QueryBuilder(em)
			.select(Municipio.class)
			.where().orGroup(w -> {
				if (valor!=null && !valor.isEmpty()) {
					try {
						Long id = Long.valueOf(valor);
						w.field(Municipio_.id).eq(id);
					} catch (Exception e) {
						w.field(Municipio_.nome).ilike("%"+valor+"%");
					}
				}
			})
			.pagination()
				.page(pagina)
				.getResultList();
		
	}
	
}
