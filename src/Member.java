import java.util.*;
import java.time.*;

public class Member {
    public static final int FIELD_SIZE = 7;
    private String name;
    private LocalDate birthDate;
    private String phoneNumber;
    private String email;
    private boolean active;
    private boolean senior;
    private double moneyOwed;//calculate

    public Member(String name, LocalDate birthDate, String phoneNumber, String email, boolean active, boolean senior, double moneyOwed) {
        this.name = name;
        this.birthDate = birthDate;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.active = active;
        this.senior = senior;
        this.moneyOwed = moneyOwed;
    }

    @Override
    public String toString(){
        String string = "Name: " + this.name
                + "Birthdate: " + this.getBirthDateString()
                + "Age: " + getAge()
                + "PhoneNumber: " + this.phoneNumber
                + "Email: " + this.email;
        if (this.active && getAge > ){
        string += "Senior Swimmer";
        }else if ()




        this.birthDate.toString()
        string +=
        return string;
        this.name, this.birthDate, this.phoneNumber, this.email, this.active, this.senior, this.moneyOwed;
    }
    public int getAge() {
        return 18;//todo: calculate age based on birthdate
    }

    public String getBirthDateString() {

        LocalDate now = LocalDate.now();


        return "2020, 1 januar";      //todo: format from localdate
    }

    public static Member fromString(String line) {
        String[] arguments = line.split(",");
        String name = arguments[0];
        LocalDate birthDate = LocalDate.ofEpochDay(Long.parseLong(arguments[1]));
        String phoneNumber = arguments[2];
        String email = arguments[3];
        boolean active = Boolean.parseBoolean(arguments[4]);
        boolean senior = Boolean.parseBoolean(arguments[5]);
        double moneyOwed = Double.parseDouble(arguments[6]);
        if (arguments.length == FIELD_SIZE) {
            return new Member(name, birthDate, phoneNumber, email, active, senior, moneyOwed);
        } else {
            int activeDiscipline = Integer.parseInt(arguments[7]);
            int[] bestTrainingResults = new int[4];
            for (int i = 0; i < bestTrainingResults.length; i++) {
                bestTrainingResults[i] = Integer.parseInt(arguments[8 + i]);
            }

            List<Convention> conventions = new ArrayList<>();
            int startingIndex = 8 + bestTrainingResults.length;//used to get conventions
            while (arguments.length >= startingIndex) {
                conventions.add(Convention.fromString(arguments, startingIndex));
                startingIndex += Convention.FIELD_SIZE;
            }
            return new CompetitiveMember(name, birthDate, phoneNumber, email, active, senior, moneyOwed, activeDiscipline,bestTrainingResults,conventions);
        }
    }

    public String getStringToSave() {
        return this.name + "," + this.birthDate.toEpochDay() + "," + this.phoneNumber + "," + this.email + "," + this.active + "," + this.senior + "," + this.moneyOwed;
    }



}
