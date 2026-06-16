package airlinemanagementsystem;

import java.io.Serializable;
import java.util.ArrayList;

class AppData implements Serializable {
    private static final long serialVersionUID = 2L;

    public ArrayList<User> users = new ArrayList<>();
    public ArrayList<Flight> flights = new ArrayList<>();
    public ArrayList<Reservation> reservations = new ArrayList<>();
    public ArrayList<Staff> staffMembers = new ArrayList<>();
    public ArrayList<SupportTicket> supportTickets = new ArrayList<>();
}
