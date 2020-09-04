package pl.kirg.rls.domain;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.NumberFormat;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Entity(name = "products")
@NoArgsConstructor(access = AccessLevel.PUBLIC, force = true)
public class Product
{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 150)
    @Column(length = 150)
    private String name;

    @Column(name = "short_desc")
    private String shortDescription;

    @NotNull
    @Size(max = 8000)
    @Column(name = "full_desc", length = 8000)
    private String fullDescription;

    @NotNull
    @Digits(integer = 5, fraction = 0)
    private Integer qty;

    @NotNull
    @Digits(integer = 2, fraction = 0)
    @Column(name = "availability_h")
    private Integer availabilityInHours;

    @NotNull
    @NumberFormat(style = NumberFormat.Style.CURRENCY)
    @Column(precision = 6, scale = 2)
    private BigDecimal price;

    @NumberFormat(style = NumberFormat.Style.PERCENT)
    @Digits(integer = 2, fraction = 0, message = "Max 99 percent")
    @Column(name = "discount_Pc", precision = 2)
    private BigDecimal discountPercent;

    @CreationTimestamp
    @Temporal(TemporalType.DATE)
    @Column(updatable = false)
    private final Date registered;

    @Override
    public String toString()
    {
        return "Product{" +
               "id=" + id +
               ", name='" + name + '\'' +
               ", shortDescription='" + shortDescription + '\'' +
               ", fullDescription='" + fullDescription + '\'' +
               ", qty=" + qty +
               ", availabilityInHours=" + availabilityInHours +
               ", price=" + price +
               ", discountPercent=" + discountPercent +
               ", registered=" + registered +
               '}';
    }
}
