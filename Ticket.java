public class Ticket {
    private int id;
    private String departure;
    private String arrival;
    private String date;
    private String time;

    public Ticket(int id, String time, String date, String departure, String arrival) {
        this.id = id;
        this.departure = departure;
        this.arrival = arrival;
        this.date = date;
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public String getDeparture() {
        return departure;
    }

    public String getArrival() {
        return arrival;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String[] toStringArray() {
        String[] arr = { Integer.toString(id), time, date, departure, arrival };
        return arr;
    }
}
