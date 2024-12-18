package com.example.regionaldelicacy.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Address extends BaseModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, columnDefinition = "NVARCHAR(MAX)")
    private String address;

    @Column(nullable = false, columnDefinition = "NVARCHAR(255)")
    private String city;

    @Column(nullable = false, columnDefinition = "NVARCHAR(255)")
    private String country;

    @Column(nullable = false)
    private String postalCode;

    @Column(nullable = false)
    private String phoneNumber;

    @Column(nullable = false, columnDefinition = "NVARCHAR(255)")
    private String customerName;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
