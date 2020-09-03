package pl.kirg.rls.domain;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Entity(name = "CT_orders")
@NoArgsConstructor(access = AccessLevel.PUBLIC, force = true)
public class Order
{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "order_status")
    private Status orderStatus;

    @Size(max = 500)
    @Column(name = "message", length = 500)
    private String customerMessage;

    @NotNull
    @Column(name = "total", precision = 6, scale = 2)
    private BigDecimal grandTotal;

    @DateTimeFormat
    @CreationTimestamp
    private final Timestamp created;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ship_addrss")
    private Address address;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable
            (name = "ct_orders_products",
             joinColumns = @JoinColumn(name = "order_id"),
             inverseJoinColumns = @JoinColumn
            )
    private List<Product> product;

    @OneToOne
            (fetch = FetchType.EAGER,
             cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE, CascadeType.DETACH},
             optional = false
            )
    @JoinColumn(name = "payment_id", updatable = false)
    private Payment payment;

    @Override
    public String toString()
    {
        return "Order{" +
               "id=" + id +
               ", orderStatus=" + orderStatus +
               ", customerMessage='" + customerMessage + '\'' +
               ", grandTotal=" + grandTotal +
               ", created=" + created +
               ", customer=" + customer +
               ", payment=" + payment +
               '}';
    }
}
