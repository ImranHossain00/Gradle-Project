package com.techCottage.domain;

import java.time.LocalDateTime;

public abstract class Domain {
    private Long id;
    private Long version = 0L;
    private LocalDateTime dateCreated
            = LocalDateTime.now();
    private LocalDateTime lastDateUpdated
            = LocalDateTime.now();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public LocalDateTime getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(LocalDateTime dateCreated) {
        this.dateCreated = dateCreated;
    }

    public LocalDateTime getLastDateUpdated() {
        return lastDateUpdated;
    }

    public void setLastDateUpdated(LocalDateTime lastDateUpdated) {
        this.lastDateUpdated = lastDateUpdated;
    }
}
