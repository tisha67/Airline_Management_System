package airlinemanagementsystem;

import java.io.Serializable;

public class Passenger implements Serializable {
    private static final long serialVersionUID = 1L;

    private String name;
    private String phone;
    private String selectedFlight;

    public Passenger(String name, String phone, String selectedFlight) {
        this.name = name;
        this.phone = phone;
        this.selectedFlight = selectedFlight;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getSelectedFlight() {
        return selectedFlight;
    }

    public void displayPassenger() {
        System.out.println("Passenger Name : " + name);
        System.out.println("Phone Number   : " + phone);
        System.out.println("Flight         : " + selectedFlight);
        System.out.println("--------------------------------");
    }
}
