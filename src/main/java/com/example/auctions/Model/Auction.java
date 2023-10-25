package com.example.auctions.Model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "auctions")
public class Auction {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL, optional = true)
    @JoinColumn(name = "bid_id")
    private Bid highestBid;

    @Column(nullable = false)
    private LocalDateTime endTime;

//    @OneToOne(mappedBy = "auction", cascade = CascadeType.ALL)
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne(optional = false, targetEntity = User.class)
    private User user;

    @PrePersist
    public void initStartAndEndTime() {
        if (endTime == null) {
            endTime = LocalDateTime.now().plusDays(1);
        }
    }

    public boolean checkIfEnded() {
        LocalDateTime now = LocalDateTime.now();
        return now.isAfter(endTime);
    }
}
