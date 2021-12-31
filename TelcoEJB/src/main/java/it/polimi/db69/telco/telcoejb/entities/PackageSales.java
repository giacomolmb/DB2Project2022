package it.polimi.db69.telco.telcoejb.entities;

import javax.persistence.*;

@Entity
@Table(name="packagesales")
@NamedQueries({
        @NamedQuery(name = "Sales.getAll", query = "SELECT s FROM PackageSales s ORDER BY s.totalAmount DESC"),
        @NamedQuery(name = "Sales.getByPackage", query = "SELECT s FROM PackageSales s WHERE s.servicePackage = :package ORDER BY s.totalAmount DESC"),
        @NamedQuery(name = "Sales.getByVp", query = "SELECT s FROM PackageSales s WHERE s.validityPeriod = :vp ORDER BY s.totalAmount DESC"),
})
public class PackageSales {
    @Id
    @OneToOne
    @JoinColumn(name = "vpid")
    private ValidityPeriod validityPeriod;

    @ManyToOne
    @JoinColumn(name = "packageid")
    private ServicePackage servicePackage;

    @Column(name = "num_sales")
    private int numSales;

    @Column(name = "base_amount")
    private int baseAmount;

    @Column(name = "total_amount")
    private int totalAmount;

    @Column(name = "num_products")
    private int numProducts;

    public ServicePackage getServicePackage() {
        return servicePackage;
    }

    public ValidityPeriod getValidityPeriod() {
        return validityPeriod;
    }

    public int getNumSales() {
        return numSales;
    }

    public int getBaseAmount() {
        return baseAmount;
    }

    public int getTotalAmount() {
        return totalAmount;
    }

    public int getNumProducts() {
        return numProducts;
    }

    public int getAvgProducts(){
        return Math.round(numProducts/(float) numSales);
    }
}
