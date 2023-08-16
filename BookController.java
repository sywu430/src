import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Collections;

public class BookController {
    private static String username = "";

    public static void setUsername(String username) {
        BookController.username = username;
    }

    static ArrayList<Ticket> searchTicket(String t_depart, String t_arrival, String t_date) {
        try {
            HashSet<Integer> bookedIds = new HashSet<Integer>();
            BufferedReader br = new BufferedReader(new java.io.FileReader("books.txt"));
            String line;
            while ((line = br.readLine()) != null) {
                String[] tokens = line.split(",");
                String username = tokens[0];
                int id = Integer.parseInt(tokens[1]);

                if (username.equals(BookController.username))
                    bookedIds.add(id);
            }
            br.close();

            br = new BufferedReader(new java.io.FileReader("tickets.txt"));
            ArrayList<Ticket> tickets = new ArrayList<Ticket>();

            while ((line = br.readLine()) != null) {
                String[] tokens = line.split(",");
                int id = Integer.parseInt(tokens[0]);
                String time = tokens[1];
                String date = tokens[2];
                String departure = tokens[3];
                String arrival = tokens[4];

                if (!arrival.equals(t_arrival))
                    continue;
                if (!departure.equals(t_depart))
                    continue;
                if (!date.equals(t_date))
                    continue;
                if (bookedIds.contains(id))
                    continue;
                tickets.add(new Ticket(id, time, date, departure, arrival));
            }
            br.close();
            return tickets;
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

        return null;
    }

    public static void bookTicket(int id) {
        try {
            BufferedWriter bw = new BufferedWriter(new java.io.FileWriter("books.txt", true));
            bw.write(username + "," + id);
            bw.newLine();
            bw.close();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static ArrayList<Ticket> getBookedTicket() {
        try {
            BufferedReader br = new BufferedReader(new java.io.FileReader("books.txt"));
            ArrayList<Integer> bookedIds = new ArrayList<>();
            ArrayList<Ticket> tickets = new ArrayList<Ticket>();

            String line;
            while ((line = br.readLine()) != null) {
                String[] tokens = line.split(",");
                String username = tokens[0];
                int id = Integer.parseInt(tokens[1]);

                if (username.equals(BookController.username)) {
                    bookedIds.add(id);
                }
            }
            br.close();

            br = new BufferedReader(new java.io.FileReader("tickets.txt"));
            while ((line = br.readLine()) != null) {
                String[] tokens = line.split(",");
                int id = Integer.parseInt(tokens[0]);
                String time = tokens[1];
                String date = tokens[2];
                String departure = tokens[3];
                String arrival = tokens[4];

                if (bookedIds.contains(id)) {
                    tickets.add(new Ticket(id, time, date, departure, arrival));
                }
            }
            Collections.sort(tickets, (a, b) -> a.getId() - b.getId());
            br.close();
            return tickets;
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

        return null;
    }

    public static void cancelBook(int id) {
        try {
            BufferedReader br = new BufferedReader(new java.io.FileReader("books.txt"));
            ArrayList<String> lines = new ArrayList<String>();

            String line;
            while ((line = br.readLine()) != null) {
                String[] tokens = line.split(",");
                String username = tokens[0];
                int ticketId = Integer.parseInt(tokens[1]);

                if (username.equals(BookController.username) && ticketId == id) {
                    continue;
                }
                lines.add(line);
            }
            br.close();

            BufferedWriter bw = new BufferedWriter(new java.io.FileWriter("books.txt"));
            for (String l : lines) {
                bw.write(l);
                bw.newLine();
            }
            bw.close();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
