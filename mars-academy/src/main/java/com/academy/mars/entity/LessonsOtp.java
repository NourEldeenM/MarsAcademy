package com.academy.mars.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

@Entity
@Setter
@Getter
@NoArgsConstructor
public class LessonsOtp {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL,orphanRemoval = true)
    @JsonIgnore
    @JoinColumn(name = "lesson_id", referencedColumnName = "id")
    private Lessons lesson;

    @Column(unique = true, nullable = false)
    private String otp;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;



    public LessonsOtp(Lessons lesson, String otp) {
        this.otp = otp;
        this.lesson = lesson;
        createdAt=LocalDateTime.now();
        updatedAt=LocalDateTime.now();
    }

    @Override
    public String toString() {
        return "Lesson id=" + lesson.getId() + ", " +
                "Lesson title='" + lesson.getTitle()  + "', " +
                "OTP='" + otp + "'";
    }

}
