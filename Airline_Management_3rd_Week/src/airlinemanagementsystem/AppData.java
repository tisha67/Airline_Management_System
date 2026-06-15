package airlinemanagementsystem;

import java.io.Serializable;
import java.util.ArrayList;

public class AppData implements Serializable {
    private static final long serialVersionUID = 4L;
    public ArrayList<User> users = new ArrayList<>();
    public ArrayList<Flight> flights = new ArrayList<>();
    public ArrayList<Reservation> reservations = new ArrayList<>();
}
