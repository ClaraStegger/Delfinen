import java.util.Date;
import java.time.*;

public class Convention {
    public static final int FIELD_SIZE = 6;
    private LocalDate date;
    private String location;
    private int[] results;//butterfly, crawl, rygcrawl og brystsvømning

    public Convention(LocalDate date, String location, int[] results) {
        this.location = location;
        this.results = results;
        this.date = date;
    }

    public static Convention fromString(String[] arguments, int startingIndex) {
        LocalDate date = LocalDate.ofEpochDay(Long.parseLong(arguments[startingIndex]));
        String location = arguments[startingIndex + 1];
        int[] results = new int[4];
        for (int i = 0; i < results.length; i++) {
            results[i] = Integer.parseInt(arguments[startingIndex + 2 + i]);
        }
        return new Convention(date,location,results);
    }

    public String getStringToSave() {
        String stringToSave = this.date.toEpochDay() + "," + this.location;
        for (int result : this.results) {
            stringToSave += "," + result;
        }
        return stringToSave;
    }


}
