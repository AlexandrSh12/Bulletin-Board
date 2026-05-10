package ru.shapovalov.NauJava.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.shapovalov.NauJava.entity.Report;
import ru.shapovalov.NauJava.entity.ReportStatus;
import ru.shapovalov.NauJava.service.ReportService;

@RestController
@RequestMapping("/custom/reports")
public class ReportController {
    @Autowired
    private ReportService reportService;

    // Создаёт отчёт и запускает формирование асинхронно, сразу возвращает id
    @PostMapping("/create")
    public ResponseEntity<Long> createReport() {
        Long reportId = reportService.createReport();
        reportService.generateReport(reportId); // запускаем асинхронно, не ждём
        return ResponseEntity.ok(reportId);
    }

    // Возвращает содержимое отчёта по id
    @GetMapping(value = "/{id}", produces = "text/html; charset=UTF-8")
    public ResponseEntity<String> getReport(@PathVariable Long id) {
        Report report = reportService.getReport(id);
        if (report == null) {
            return ResponseEntity.notFound().build();
        }
        if (report.getStatus() == ReportStatus.CREATED) {
            return ResponseEntity.ok("Отчёт ещё формируется...");
        }
        if (report.getStatus() == ReportStatus.ERROR) {
            return ResponseEntity.ok("Ошибка формирования отчёта: " + report.getContent());
        }
        return ResponseEntity.ok(report.getContent());
    }
}
