package ru.shapovalov.NauJava.service;

import ru.shapovalov.NauJava.entity.Report;

import java.util.concurrent.CompletableFuture;

public interface ReportService {
    Long createReport();
    Report getReport(Long id);
    CompletableFuture<Void> generateReport(Long reportId);
}
