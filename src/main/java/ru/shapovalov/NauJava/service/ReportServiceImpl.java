package ru.shapovalov.NauJava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.shapovalov.NauJava.entity.Item;
import ru.shapovalov.NauJava.entity.Report;
import ru.shapovalov.NauJava.entity.ReportStatus;
import ru.shapovalov.NauJava.repository.ItemRepository;
import ru.shapovalov.NauJava.repository.ReportRepository;
import ru.shapovalov.NauJava.repository.UserRepository;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class ReportServiceImpl implements ReportService{
    @Autowired
    private ReportRepository reportRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Override
    public Long createReport() {
        Report report = new Report();
        report.setStatus(ReportStatus.CREATED);
        reportRepository.save(report);
        return report.getId();
    }

    @Override
    public Report getReport(Long id) {
        return reportRepository.findById(id).orElse(null);
    }
    @Override
    public CompletableFuture<Void> generateReport(Long reportId) {
        return CompletableFuture.runAsync(() -> {
            try {
                long totalStart = System.currentTimeMillis();

                // Результаты из потоков
                AtomicLong userCount = new AtomicLong();
                AtomicLong userTime = new AtomicLong();
                AtomicReference<List<Item>> itemList = new AtomicReference<>();
                AtomicLong itemTime = new AtomicLong();

                // Поток 1 — подсчёт пользователей
                Thread userThread = new Thread(() -> {
                    long start = System.currentTimeMillis();
                    long count = ((List<?>) userRepository.findAll()).size();
                    userCount.set(count);
                    userTime.set(System.currentTimeMillis() - start);
                });

                // Поток 2 — получение списка объявлений
                Thread itemThread = new Thread(() -> {
                    long start = System.currentTimeMillis();
                    List<Item> items = (List<Item>) itemRepository.findAll();
                    itemList.set(items);
                    itemTime.set(System.currentTimeMillis() - start);
                });

                userThread.start();
                itemThread.start();

                userThread.join();
                itemThread.join();

                long totalTime = System.currentTimeMillis() - totalStart;

                // Формируем HTML отчёт
                String content = buildHtmlReport(userCount.get(), userTime.get(),
                        itemList.get(), itemTime.get(), totalTime);

                Report report = reportRepository.findById(reportId).orElseThrow();
                report.setContent(content);
                report.setStatus(ReportStatus.COMPLETED);
                reportRepository.save(report);

            } catch (Exception e) {
                Report report = reportRepository.findById(reportId).orElseThrow();
                report.setStatus(ReportStatus.ERROR);
                report.setContent("Ошибка: " + e.getMessage());
                reportRepository.save(report);
            }
        });
    }
    private String buildHtmlReport(long userCount, long userTime,
                                   List<Item> items, long itemTime, long totalTime) {
        StringBuilder sb = new StringBuilder();
        sb.append("<!DOCTYPE html><html><head><meta charset='UTF-8'>")
                .append("<title>Отчёт</title>")
                .append("<style>body{font-family:Arial,sans-serif;margin:40px}")
                .append("table{border-collapse:collapse;width:100%}")
                .append("th,td{border:1px solid #ccc;padding:8px;text-align:left}")
                .append("th{background:#f2f2f2}</style></head><body>")
                .append("<h1>Отчёт о состоянии системы</h1>")

                .append("<h2>Статистика</h2>")
                .append("<table><tr><th>Показатель</th><th>Значение</th><th>Время вычисления</th></tr>")
                .append("<tr><td>Количество пользователей</td><td>").append(userCount)
                .append("</td><td>").append(userTime).append(" мс</td></tr>")
                .append("</table>")

                .append("<h2>Список объявлений</h2>")
                .append("<p>Время получения: ").append(itemTime).append(" мс</p>")
                .append("<table><tr><th>ID</th><th>Название</th><th>Цена</th><th>Статус</th></tr>");

        for (Item item : items) {
            sb.append("<tr>")
                    .append("<td>").append(item.getId()).append("</td>")
                    .append("<td>").append(item.getTitle()).append("</td>")
                    .append("<td>").append(item.getPrice()).append("</td>")
                    .append("<td>").append(item.getStatus()).append("</td>")
                    .append("</tr>");
        }

        sb.append("</table>")
                .append("<h2>Общее время формирования отчёта: ").append(totalTime).append(" мс</h2>")
                .append("</body></html>");

        return sb.toString();
    }
}
