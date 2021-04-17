package com.system.clinical.repository.especialidade;

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

import com.system.clinical.model.Especialidade;

public class EspecialidadeRepositoryImpl implements EspecialidadeRepositoryQuery {
	
	@PersistenceContext
	private EntityManager manager;

	@Override
	public Page<Especialidade> filtrar(String descricao, Pageable pageable) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Especialidade> criteriaQuery = builder.createQuery(Especialidade.class);	
		Root<Especialidade> root = criteriaQuery.from(Especialidade.class);
		Predicate[] predicates = criarRestricoes(descricao, builder, root);
		criteriaQuery.where(predicates);
		TypedQuery<Especialidade> query = manager.createQuery(criteriaQuery);
		adicionarRestricoesDePaginacao(query, pageable);
		return new PageImpl<>(query.getResultList(), pageable, total(descricao));
	}
	
	
	private Predicate[] criarRestricoes(String descricao, CriteriaBuilder builder, Root<Especialidade> root) {		
		List<Predicate> predicates = new ArrayList<>();	
		if (descricao != null)
			predicates.add(builder.like(root.get("descricai"),"%" + descricao + "%"));
		return predicates.toArray(new Predicate[predicates.size()]);
	}
	
	private void adicionarRestricoesDePaginacao(TypedQuery<Especialidade> query, Pageable pageable) {
		int paginaAtual = pageable.getPageNumber();
		int totalRegistroPorPagina = pageable.getPageSize();
		int primeiroRegistroDapagina = paginaAtual * totalRegistroPorPagina;
		query.setFirstResult(primeiroRegistroDapagina);
		query.setMaxResults(totalRegistroPorPagina);
	}
	
	private Long total(String descricao) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
		Root<Especialidade> root = criteriaQuery.from(Especialidade.class);
		Predicate[] predicates = criarRestricoes(descricao, builder, root);
		criteriaQuery.where(predicates);
		criteriaQuery.select(builder.count(root));
		return manager.createQuery(criteriaQuery).getSingleResult();
	}

}
