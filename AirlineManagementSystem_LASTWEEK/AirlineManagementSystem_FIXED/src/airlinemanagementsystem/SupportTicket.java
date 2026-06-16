package airlinemanagementsystem;

import java.io.Serializable;
import java.time.LocalDateTime;

class SupportTicket implements Serializable {
    private static final long serialVersionUID = 1L;

    String id;
    String subject;
    String status;
    String description;
    String resolution;
    LocalDateTime createdDate;
    LocalDateTime resolvedDate;

    SupportTicket(String subject, String description) {
        this.subject = subject;
        this.description = description;
        this.id = "TK" + System.currentTimeMillis();
        this.status = "OPEN";
        this.createdDate = LocalDateTime.now();
    }

    public void resolve(String resolution) {
        this.status = "RESOLVED";
        this.resolution = resolution;
        this.resolvedDate = LocalDateTime.now();
    }
}
