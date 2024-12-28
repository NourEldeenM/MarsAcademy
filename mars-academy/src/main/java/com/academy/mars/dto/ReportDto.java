package com.academy.mars.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ReportDto {

    private Long studentId;
    private String studentName;
    private String courseName;
    private int grade;
    private String feedback;
}
