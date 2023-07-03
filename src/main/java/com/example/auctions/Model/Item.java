package com.example.auctions.Model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Getter
@Setter
@Table(name = "items")
@NoArgsConstructor
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String description;

    private Double startingPrice;

    @OneToOne(mappedBy = "item", cascade = CascadeType.ALL)
    private Auction auction;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    public Item(String name, String description, Double startingPrice) {
        this.name = name;
        this.description = description;
        this.startingPrice = startingPrice;
    }
}
