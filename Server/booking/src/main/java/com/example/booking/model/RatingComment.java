package com.example.booking.model;

import com.example.booking.dto.RatingCommentDisplayDTO;
import com.example.booking.model.enums.RatingCommentType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor
@Setter
@Getter
@ToString
@Entity
public class RatingComment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private RatingCommentType type;

    @ManyToOne
    @JoinColumn(name = "guest_id")
    private Guest guest;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private Owner owner;

    @ManyToOne
    @JoinColumn(name = "accommodation_id")
    private Accommodation accommodation;

    private float rating;

    private String comment;

    private LocalDateTime time;

    private boolean approved;

    private boolean reported;

    public RatingComment(RatingCommentType type, Guest guest, Owner owner, Accommodation accommodation, float rating, String comment){
        this.type = type;
        if (type == RatingCommentType.FOR_OWNER){
            this.approved = true;
        } else {
            this.approved = false;
        }
        this.guest = guest;
        this.owner = owner;
        this.accommodation = accommodation;
        this.rating = rating;
        this.comment = comment;
        this.time = LocalDateTime.now();
        this.reported = false;
    }

    public RatingCommentDisplayDTO parseToDisplay(){
        if (type == RatingCommentType.FOR_OWNER){
            return new RatingCommentDisplayDTO(id, guest.getId(), guest.getName() + guest.getSurname(), type, owner.getId(), null, rating, comment, time);
        }
        return new RatingCommentDisplayDTO(id, guest.getId(), guest.getName() + " " + guest.getSurname(), type, null, accommodation.getId(), rating, comment, time);
    }

}
