
package com.example.demo.repository;

import com.example.demo.model.Employee;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.CriteriaUpdate;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.util.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class EmployeeRepositoryImpl implements EmployeeRepositoryCustom {

    @PersistenceContext
    private EntityManager em;
    
    @Override
    public void saveEmployee(Employee employee) {
        if (employee.getId() == null) {
            em.persist(employee);
        }
    }

    @Override
    public Employee fetchActiveEmployee(Long id) {
    	CriteriaBuilder cb = em.getCriteriaBuilder();
    	CriteriaQuery<Employee> cq = cb.createQuery(Employee.class);
    	
    	// From clause
    	Root<Employee> root = cq.from(Employee.class);
    	
    	// Fetch joins (Left join)
    	root.fetch("education",JoinType.LEFT);
    	root.fetch("postal",JoinType.LEFT);
    	
    	// Condition where ...
    	Predicate idPredicate = cb.equal(root.get("id"), id);
    	Predicate activePredicate = cb.isTrue(root.get("isActive"));
    	
    	// Combining both predicates
    	cq.select(root).where(cb.and(idPredicate, activePredicate));
    	
        return em.createQuery(cq)
        		.getResultStream()
        		.findFirst()
        		.orElse(null);
    }

    @Override
    public Page<Employee> fetchAllActiveEmployees(Pageable pageable) {
    	
    	// Content Query
    	CriteriaBuilder cb = em.getCriteriaBuilder();
    	CriteriaQuery<Employee> cq = cb.createQuery(Employee.class);
    	
    	Root<Employee> root = cq.from(Employee.class);   	
    	Predicate activePredicate = cb.isTrue(root.get("isActive"));
    	
    	
    	// Filtered Data which is true for isActive
    	cq.select(root).where(activePredicate);
    	
    	// This query returns Employee Objects
    	TypedQuery<Employee> query = em.createQuery(cq);
    	query.setFirstResult((int) pageable.getOffset()); // Number * Size
    	query.setMaxResults(pageable.getPageSize());      // Size 
    	
    	List<Employee> content = query.getResultList();
    	
    	// Count Query
        CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
        Root<Employee> countRoot = countQuery.from(Employee.class);

        countQuery.select(cb.count(countRoot))
                  .where(cb.isTrue(countRoot.get("isActive")));

        Long count = em.createQuery(countQuery).getSingleResult();
        
        // Page Result
        return new PageImpl<>(content, pageable, count);
    }
    
//    @Override
//    public Employee fetchActiveEmployeeForDelete(Long id) {
//    	
//    	// Provides methods to create: Query, Predicate
//        CriteriaBuilder cb = em.getCriteriaBuilder();
//        
//        // Create a Query that will return Object of type Employee
//        CriteriaQuery<Employee> cq = cb.createQuery(Employee.class);
//        
//        // From Clause
//        Root<Employee> root = cq.from(Employee.class);
//        
//        // Left Join
//        root.fetch("education", JoinType.LEFT);
//        root.fetch("postal",JoinType.LEFT);
//        
//        // Create a condition by specific ID
//        Predicate idPredicate  = cb.equal(root.get("id"), id);
//        Predicate activePredicate = cb.isTrue(root.get("isActive"));
//        
//        // Combining both Predicate
//        cq.select(root).where(cb.and(idPredicate, activePredicate));
//        
//        // Execeute the result
//        return em.createQuery(cq)
//        		.getResultStream()
//        		.findFirst()
//        		.orElse(null);
//    }
//    
    
//    @Override
//    public Employee fetchActiveEmployeeById(Long id) {
//        CriteriaBuilder cb = em.getCriteriaBuilder();
//        CriteriaQuery<Employee> cq = cb.createQuery(Employee.class);
//
//        Root<Employee> root = cq.from(Employee.class);
//        root.fetch("education", JoinType.LEFT);
//        root.fetch("postal", JoinType.LEFT);
//
//        Predicate idPredicate = cb.equal(root.get("id"), id);
//        Predicate activePredicate = cb.isTrue(root.get("isActive"));
//
//        cq.select(root).where(cb.and(idPredicate, activePredicate));
//
//        return em.createQuery(cq)
//                 .getResultStream()
//                 .findFirst()
//                 .orElse(null);
//    }
    
    @Override
    public List<Employee> searchByIdOrName(Long id, String name) {

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Employee> cq = cb.createQuery(Employee.class);
        Root<Employee> root = cq.from(Employee.class);

        root.fetch("education", JoinType.LEFT);
        root.fetch("postal", JoinType.LEFT);

        List<Predicate> predicates = new ArrayList<>();
        predicates.add(cb.isTrue(root.get("isActive")));

        if (id != null) {
            predicates.add(cb.equal(root.get("id"), id));
        }

        if (name != null && !name.isBlank()) {
            predicates.add(cb.like(
                    cb.lower(root.get("name")),
                    "%" + name.toLowerCase() + "%"
                    )
            );
            
        }
        cq.select(root).where(predicates.toArray(new Predicate[0]));
        
        
        return em.createQuery(cq).getResultList();
    }

    
    @Override
    public void bulkSoftDelete(List<Long> ids) {

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaUpdate<Employee> update = cb.createCriteriaUpdate(Employee.class);
       
        Root<Employee> root = update.from(Employee.class);

        update.set("isActive", false)
              .where(
                  cb.and(
                      root.get("id").in(ids),
                      cb.isTrue(root.get("isActive"))
                  )
              );

        em.createQuery(update).executeUpdate();
    }
}
