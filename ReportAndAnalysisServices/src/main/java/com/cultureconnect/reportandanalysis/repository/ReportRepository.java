package com.cultureconnect.reportandanalysis.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cultureconnect.reportandanalysis.entity.Report;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {
    List<Report> findByScope(Report.ReportScope scope);
}
