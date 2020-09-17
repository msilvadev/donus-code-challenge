package br.com.donuscodechallenge.metric.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "METRIC_SERVICE")
public class Metric {

    @Id
    private Integer id;
    private LocalDateTime dateOfMetric;
    private long elapsed;
    private String description;

    public Metric(long elapsed, String description) {
        this.elapsed = elapsed;
        this.description = description;
    }

    public Metric() {
    }

    @PrePersist
    private void prePersist() {
        this.dateOfMetric = LocalDateTime.now();
    }

    public Integer getId() {
        return id;
    }

    public LocalDateTime getDateOfMetric() {
        return dateOfMetric;
    }

    public void setDateOfMetric(LocalDateTime dateOfMetric) {
        this.dateOfMetric = dateOfMetric;
    }

    public long getElapsed() {
        return elapsed;
    }

    public void setElapsed(long elapsed) {
        this.elapsed = elapsed;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
