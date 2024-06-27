package com.test.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "mcq_question")
public class MCQQuestion {
    
    @Id
    @Setter
    @Getter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "question_id")
    private Long id;

    @Setter
    @Getter
    private String category;

    @Setter
    @Getter
    private String question;

    @Setter
    @Getter
    @Column(name = "option_one")
    private String optionOne;

    @Setter
    @Getter
    @Column(name = "option_two")
    private String optionTwo;

    @Setter
    @Getter
    @Column(name = "option_three")
    private String optionThree;

    @Setter
    @Getter
    @Column(name = "option_four")
    private String optionFour;

    @Setter
    @Getter
    @Column(name = "correct_option")
    private String correctOption;

    @Setter
    @Getter
    @Column(name = "positive_mark")
    private int positiveMark;

    @Setter
    @Getter
    @Column(name = "negative_mark")
    private int negativeMark;

}
