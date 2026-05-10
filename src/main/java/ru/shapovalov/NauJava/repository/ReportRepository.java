package ru.shapovalov.NauJava.repository;

import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.shapovalov.NauJava.entity.Report;
import org.springframework.data.repository.CrudRepository;

@RepositoryRestResource
public interface ReportRepository extends CrudRepository<Report, Long> {
}
