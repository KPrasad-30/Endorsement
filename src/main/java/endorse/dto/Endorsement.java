package endorse.dto;

import javax.persistence.*;

@Entity
public class Endorsement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "reviewee_id")
    private User reviewee;

    @ManyToOne
    @JoinColumn(name = "reviewer_id")
    private User reviewer;

    private String skill;
    private int actualScore;
    private int adjustedScore;
    private int coworker;
   private String reason;
    // Getters and setters

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getReviewee() {
        return reviewee;
    }

    public void setReviewee(User reviewee) {
        this.reviewee = reviewee;
    }

    public User getReviewer() {
        return reviewer;
    }

    public void setReviewer(User reviewer) {
        this.reviewer = reviewer;
    }

    public String getSkill() {
        return skill;
    }

    public void setSkill(String skill) {
        this.skill = skill;
    }

    public int getActualScore() {
        return actualScore;
    }

    public void setActualScore(int actualScore) {
        this.actualScore = actualScore;
    }

    public int getAdjustedScore() {
        return adjustedScore;
    }

    public void setAdjustedScore(int adjustedScore) {
        this.adjustedScore = adjustedScore;
    }

    public int getCoworker() {
        return coworker;
    }

    public void setCoworker(int coworker) {
        this.coworker = coworker;
    }
}
