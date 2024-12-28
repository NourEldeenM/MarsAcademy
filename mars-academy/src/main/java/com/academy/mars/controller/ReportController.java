package com.academy.mars.controller;

import com.academy.mars.service.ReportService;
import com.academy.mars.dto.ReportDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/reports")
public class ReportController {
    private final ReportService reportService;

    @Autowired
    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping("/course/{courseId}")
    public ResponseEntity<List<ReportDto>> getReportByCourse(@PathVariable Long courseId) {
        List<ReportDto> report = reportService.getReportByCourse(courseId);
        return report.isEmpty()
                ? ResponseEntity.notFound().build()
                : ResponseEntity.ok(report);
    }
}