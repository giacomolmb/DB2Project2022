package it.polimi.db69.telco.telcoejb.entities;

import javax.persistence.*;

@Entity
@DiscriminatorValue("fixedphone")
public class FixedPhone extends Service{
}
