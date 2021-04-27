package com.system.clinical.repository.convenio;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.system.clinical.model.Convenio;
import com.system.clinical.repository.filter.ConvenioFilter;

public class ConvenioRepositoryImpl implements ConvenioRepositoryQuery {
	
	@PersistenceContext
	private EntityManager manager;

	@Override
	public Page<Convenio> filtrar(ConvenioFilter filter, Pageable pageable) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Convenio> criteriaQuery = builder.createQuery(Convenio.class);	
		Root<Convenio> root = criteriaQuery.from(Convenio.class);
		Predicate[] predicates = criarRestricoes(filter, builder, root);
		criteriaQuery.where(predicates);
		TypedQuery<Convenio> query = manager.createQuery(criteriaQuery);
		adicionarRestricoesDePaginacao(query, pageable);
		return new PageImpl<>(query.getResultList(), pageable, total(filter));
	}
	
	@Override
	public List<Convenio> listaFiltrada(ConvenioFilter filter) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Convenio> criteriaQuery = builder.createQuery(Convenio.class);	
		Root<Convenio> root = criteriaQuery.from(Convenio.class);
		Predicate[] predicates = criarRestricoes(filter, builder, root);
		criteriaQuery.where(predicates);
		TypedQuery<Convenio> query = manager.createQuery(criteriaQuery);
		return query.getResultList();
	
	}
	private Predicate[] criarRestricoes(ConvenioFilter filter, CriteriaBuilder builder, Root<Convenio> root) {		
		List<Predicate> predicates = new ArrayList<>();	
		if (filter.getNome() != null)
			predicates.add(builder.like(root.get("nome"),"%" + filter.getNome() + "%"));
		if (filter.getCnpj() != null)
			predicates.add(builder.equal(root.get("cnpj"), filter.getCnpj()));
		if (filter.getStatus() != null)
			predicates.add(builder.equal(root.get("status"), filter.getStatus()));
		return predicates.toArray(new Predicate[predicates.size()]);
	}
	
	private void adicionarRestricoesDePaginacao(TypedQuery<Convenio> query, Pageable pageable) {
		int paginaAtual = pageable.getPageNumber();
		int totalRegistroPorPagina = pageable.getPageSize();
		int primeiroRegistroDapagina = paginaAtual * totalRegistroPorPagina;
		query.setFirstResult(primeiroRegistroDapagina);
		query.setMaxResults(totalRegistroPorPagina);
	}
	
	private Long total(ConvenioFilter filter) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
		Root<Convenio> root = criteriaQuery.from(Convenio.class);
		Predicate[] predicates = criarRestricoes(filter, builder, root);
		criteriaQuery.where(predicates);
		criteriaQuery.select(builder.count(root));
		return manager.createQuery(criteriaQuery).getSingleResult();
	}


}
