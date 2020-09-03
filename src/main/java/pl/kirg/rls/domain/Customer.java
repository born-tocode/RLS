package pl.kirg.rls.domain;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Entity
@Table(name = "customers",
       uniqueConstraints = @UniqueConstraint
               (
                       name = "UNQ_FIRSTNAME_LASTNAME_PHONENUMBER_EMAIL",
                       columnNames =
                               {
                                       "first_name",
                                       "last_name",
                                       "phone_number"
                               }
               )
)
@NoArgsConstructor(access = AccessLevel.PUBLIC, force = true)
public class Customer
{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 2, max = 40)
    @Column(name = "first_name", updatable = false)
    private String firstName;

    @NotNull
    @Size(min = 2, max = 40)
    @Column(name = "last_name", updatable = false)
    private String lastName;

    @NotNull
    @DateTimeFormat
    @Column(name = "birth_date", updatable = false)
    private Date birthDate;

    @NotNull
    @Digits(integer = 16, fraction = 0)
    @Column(name = "phone_number")
    private String phoneNumber;

    @OneToOne(mappedBy = "customer", optional = false)
    private User user;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable
            (name = "customers_orders",
             joinColumns = @JoinColumn(name = "customer_id"),
             inverseJoinColumns = @JoinColumn
            )
    private List<Order> order;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable
            (name = "customers_addresses",
             joinColumns = @JoinColumn(name = "customer_id"),
             inverseJoinColumns = @JoinColumn
            )
    private List<Address> address;

    @Override
    public String toString()
    {
        return "Customer{" +
               "id=" + id +
               ", firstName='" + firstName + '\'' +
               ", lastName='" + lastName + '\'' +
               ", birthDate=" + birthDate +
               ", phoneNumber='" + phoneNumber + '\'' +
               '}';
    }
}
