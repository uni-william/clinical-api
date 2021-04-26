package com.system.clinical.repository.cargo;

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

import com.system.clinical.model.Cargo;

public class CargoRepositoryImpl implements CargoRepositoryQuery {
	
	@PersistenceContext
	private EntityManager manager;

	@Override
	public Page<Cargo> filtrar(String descricao, Pageable pageable) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Cargo> criteriaQuery = builder.createQuery(Cargo.class);	
		Root<Cargo> root = criteriaQuery.from(Cargo.class);
		Predicate[] predicates = criarRestricoes(descricao, builder, root);
		criteriaQuery.where(predicates);
		TypedQuery<Cargo> query = manager.createQuery(criteriaQuery);
		adicionarRestricoesDePaginacao(query, pageable);
		return new PageImpl<>(query.getResultList(), pageable, total(descricao));
	}
	

	@Override
	public List<Cargo> listaFiltrada(String descricao) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Cargo> criteriaQuery = builder.createQuery(Cargo.class);	
		Root<Cargo> root = criteriaQuery.from(Cargo.class);
		Predicate[] predicates = criarRestricoes(descricao, builder, root);
		criteriaQuery.where(predicates);
		TypedQuery<Cargo> query = manager.createQuery(criteriaQuery);
		return query.getResultList();
	}
	
	
	private Predicate[] criarRestricoes(String descricao, CriteriaBuilder builder, Root<Cargo> root) {		
		List<Predicate> predicates = new ArrayList<>();	
		if (descricao != null)
			predicates.add(builder.like(root.get("descricao"),"%" + descricao + "%"));
		return predicates.toArray(new Predicate[predicates.size()]);
	}
	
	private void adicionarRestricoesDePaginacao(TypedQuery<Cargo> query, Pageable pageable) {
		int paginaAtual = pageable.getPageNumber();
		int totalRegistroPorPagina = pageable.getPageSize();
		int primeiroRegistroDapagina = paginaAtual * totalRegistroPorPagina;
		query.setFirstResult(primeiroRegistroDapagina);
		query.setMaxResults(totalRegistroPorPagina);
	}
	
	private Long total(String descricao) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
		Root<Cargo> root = criteriaQuery.from(Cargo.class);
		Predicate[] predicates = criarRestricoes(descricao, builder, root);
		criteriaQuery.where(predicates);
		criteriaQuery.select(builder.count(root));
		return manager.createQuery(criteriaQuery).getSingleResult();
	}



}
