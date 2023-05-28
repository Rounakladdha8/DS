
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        // Set up the system clocks in terms of hours and minutes
        ArrayList<String> systemClocks = new ArrayList<>();
        systemClocks.add("03:00");
        systemClocks.add("02:50");
        systemClocks.add("03:25");
        System.out.println("System clocks: " + systemClocks);
        // Calculate the master clock
        System.out.println("Enter master Clock: ");
        String masterClock = sc.next();
        System.out.println("Master clock: " + masterClock);
        // Calculate the clock offsets
        ArrayList<Integer> clockOffsets = new ArrayList<>();
        for (String clock : systemClocks) {
            int offset = timeToMinutes(clock) - timeToMinutes(masterClock);
            clockOffsets.add(offset);
        }
        System.out.println("Clock offsets (in minutes): " + clockOffsets);
        // Calculate the average clock offset
        int totalOffset = 0;
        for (int offset : clockOffsets) {
            totalOffset += offset;
        }
        int averageOffset = totalOffset / clockOffsets.size();
        System.out.println("Average clock offset (in minutes): " + averageOffset);
        int Tcorr = timeToMinutes(masterClock) + averageOffset;
        // Adjust the client offset
        ArrayList<Integer> sentOffset = new ArrayList<>();
        for (String clock : systemClocks) {
            int adjustedMinutes = Tcorr - timeToMinutes(clock);
            sentOffset.add(adjustedMinutes);
        }
        System.out.println("Sent clock offset : " + sentOffset);
        // Adjust the client clocks
        ArrayList<String> adjustedClocks = new ArrayList<>();
        for (int i = 0; i < systemClocks.size(); i++) {
            int adj = timeToMinutes(systemClocks.get(i)) + sentOffset.get(i);
            adjustedClocks.add(minutesToTime(adj));
        }
        System.out.println("Adjusted system clocks: " + adjustedClocks);
    }

    // Helper function to convert a time string in "hh:mm" format to minutes
    private static int timeToMinutes(String time) {
        String[] parts = time.split(":");
        int hours = Integer.parseInt(parts[0]);
        int minutes = Integer.parseInt(parts[1]);
        return hours * 60 + minutes;
    }

    // Helper function to convert minutes to a time string in "hh:mm" format
    private static String minutesToTime(int minutes) {
        int hours = minutes / 60;
        int remainingMinutes = minutes % 60;
        return String.format("%02d:%02d", hours, remainingMinutes);
    }
}
