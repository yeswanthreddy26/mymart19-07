package com.mymart.model;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="ratings")
public class Rating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    private double rating;
    
    private String review;

    private LocalDateTime dateTime;
    
    @ElementCollection
    @CollectionTable(name = "rating_images", joinColumns = @JoinColumn(name = "rating_id"))
    @Column(name = "image_path")
    private Set<String> imagePaths = new HashSet<>();
    
    private int helpfulVotes = 0;

    @OneToMany(mappedBy = "rating", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<HelpfulVote> helpfulVotesList = new HashSet<>();

    public int getHelpfulVotesYes() {
        return (int) helpfulVotesList.stream()
            .filter(HelpfulVote::isHelpful)
            .count();
    }

    public int getTotalVotes() {
        return helpfulVotesList.size();
    }

    public Set<HelpfulVote> getHelpfulVotesList() {
        return helpfulVotesList;
    }

    public void setHelpfulVotesList(Set<HelpfulVote> helpfulVotesList) {
        this.helpfulVotesList = helpfulVotesList;
    }
    
    public Set<String> getImagePaths() {
        return imagePaths;
    }

    public void setImagePaths(Set<String> imagePaths) {
        this.imagePaths = imagePaths;
    }
    
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public double getRating() {
		return rating;
	}

	public void setRating(double rating) {
		this.rating = rating;
	}

	public String getReview() {
		return review;
	}

	public void setReview(String review) {
		this.review = review;
	}

	public LocalDateTime getDateTime() {
		return dateTime;
	}

	public void setDateTime(LocalDateTime dateTime) {
		this.dateTime = dateTime;
	}

	public Rating(Long id, User user, Product product, double rating, String review) {
		super();
		this.id = id;
		this.user = user;
		this.product = product;
		this.rating = rating;
		this.review = review;
	}

	public Rating() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "Rating [id=" + id + ", user=" + user + ", product=" + product + ", rating=" + rating + ", review="
				+ review + "]";
	}

	
    

}
