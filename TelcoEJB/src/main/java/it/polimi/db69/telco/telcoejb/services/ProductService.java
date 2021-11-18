package it.polimi.db69.telco.telcoejb.services;

import it.polimi.db69.telco.telcoejb.entities.Product;
import it.polimi.db69.telco.telcoejb.exceptions.NonUniqueException;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class ProductService {
    @PersistenceContext(unitName = "TELCOEJB")
    private EntityManager em;

    public ProductService(){}

    public Product createProduct(String name, double fee) throws NonUniqueException{
        if (em.createNamedQuery("Product.getProductByName", Product.class)
                .setParameter("name", name).setMaxResults(1).getResultStream().findFirst().orElse(null) != null)
            throw new NonUniqueException("The product \"" + name + "\" already exists!");

        Product product = new Product(name, fee);
        em.persist(product);

        return product;
    }
}
