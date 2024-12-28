package com.academy.mars.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LessonsFiles {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "lesson_id", nullable = false)
    private Lessons lesson;

    @Column(nullable = false)
    private String fileName;

    @Column(nullable = false)
    private String fileType;

    @Lob
    @Column(nullable = false)
    private byte[] data;

    @Temporal(TemporalType.TIMESTAMP)
    private Date uploadedAt;

    public LessonsFiles(Lessons lesson, String fileName, String fileType, byte[] data) {
        this.lesson = lesson;
        this.fileName = fileName;
        this.fileType = fileType;
        this.data = data;
        this.uploadedAt = new Date();
    }
}
