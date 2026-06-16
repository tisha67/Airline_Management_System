package airlinemanagementsystem;

import java.io.Serializable;
import java.time.LocalDateTime;

class Staff implements Serializable {
    private static final long serialVersionUID = 1L;

    String name;
    String role;
    String id;
    String department;
    LocalDateTime joinDate;

    Staff(String name, String role, String department) {
        this.name = name;
        this.role = role;
        this.department = department;
        this.id = "ST" + System.currentTimeMillis();
        this.joinDate = LocalDateTime.now();
    }

    // Backward-compatible constructor.
    Staff(String name, String role) {
        this(name, role, "General");
    }
}
