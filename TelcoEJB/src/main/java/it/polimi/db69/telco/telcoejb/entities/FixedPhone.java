package it.polimi.db69.telco.telcoejb.entities;

import javax.persistence.*;

@Entity
@DiscriminatorValue("fixedphone")
public class FixedPhone extends Service{
    @Override
    public String printService() {
        return "Fixed Phone";
    }

    @Override
    public String printExtraFees() {
        return "No extra fees";
    }
}
