package com.test.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Data
@Table(name = "subcategory")
public class Subcategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "subcategory_id")
    private Long subcategoryId;

    @Column(name = "subcategory_name")
    private String subcategoryName;

    @Column(name = "subcategory_description")
    private String subcategoryDescription;

    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "category_id")
    private Category category;

    // @OneToMany(mappedBy = "subcategory", cascade = CascadeType.ALL)
    // private List<MCQQuestion> mcqQuestions;
}