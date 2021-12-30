package it.polimi.db69.telco.telcoejb.entities;

import javax.persistence.*;

@Entity
@Table(name = "productreport")
@NamedQueries({
        @NamedQuery(name = "ProductReport.getAll", query = "SELECT ps FROM ProductSales ps ORDER BY ps.salesValue DESC"),
        @NamedQuery(name = "ProductReport.getById", query = "SELECT ps FROM ProductSales ps WHERE ps.product = :product")
})
public class ProductSales {
    @Id
    @OneToOne
    @JoinColumn(name = "productid")
    private Product product;

    @Column(name = "name")
    private String name;

    @Column(name = "sales")
    private int sales;

    @Column(name = "salesValue")
    private double salesValue;

    public Product getProduct() {
        return product;
    }

    public String getName() {
        return name;
    }

    public int getSales() {
        return sales;
    }

    public double getSalesValue() {
        return salesValue;
    }
}
