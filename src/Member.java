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
    public String toString() {
        long age = this.getAge();
        String string = "Name: " + this.name
                + "\nBirthdate: " + this.getDateString(this.birthDate)
                + "\nAge: " + age
                + "\nPhoneNumber: " + this.phoneNumber
                + "\nEmail: " + this.email
                + "\nStart Date: " + this.getDateString(this.startDate)
                + "\nSubscription fee: " + this.getSubscriptionFee()
                + "\nActivity Type: ";
        if (this.isActive()) {
            if (age >= 18) {
                string += "Senior Swimmer";
            } else {
                string += "Junior Swimmer";
            }

            if (this.isCompetitiveMember()) {
                string += ", Competitive ";// Konkurrence svømmer
            } else {
                string += ", Exerciser";// Motionist
            }

            string += "\nTeam: ";
            if (this.isOnSeniorTeam()) {
                string += "Senior ";
            } else {
                string += "Junior ";
            }
            if (this.isCompetitiveMember()) {
                string += "Competitive ";
            }
            string += "Team";

            string += "\nActive Disciplines ";
            List<String> activeDisciplines = this.getActiveDisciplines();
            for (int i = 0; i < activeDisciplines.size(); i++) {
                String activeDiscipline = activeDisciplines.get(i);
                if (i == 0) {
                    string += activeDiscipline;
                } else {
                    string += ", " + activeDiscipline;
                }
            }
        } else {
            string += "Passive";
        }
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
        LocalDate startDate = LocalDate.ofEpochDay(Long.parseLong(arguments[4]));
        double moneyOwed = Double.parseDouble(arguments[5]);
        if (arguments.length == FIELD_SIZE) {//should be 6
            return new Member(name, birthDate, phoneNumber, email, startDate, moneyOwed);
        } else {
            boolean seniorTeam = Boolean.parseBoolean(arguments[6]);
            boolean[] activeDisciplines = new boolean[4];
            for (int i = 0; i < activeDisciplines.length; i++) {
                activeDisciplines[i] = Boolean.parseBoolean(arguments[7 + i]);
            }
            if (arguments.length == FIELD_SIZE + ActiveMember.FIELD_SIZE) {//should be 6 + 5 so 11
                return new ActiveMember(name, birthDate, phoneNumber, email, startDate, moneyOwed, seniorTeam, activeDisciplines);
            } else {
                int[] bestTrainingResults = new int[4];
                for (int i = 0; i < bestTrainingResults.length; i++) {
                    bestTrainingResults[i] = Integer.parseInt(arguments[11 + i]);
                }
                List<Convention> conventions = new ArrayList<>();
                int startingIndex = 11 + bestTrainingResults.length;//used to get conventions
                while (arguments.length >= startingIndex) {
                    conventions.add(Convention.fromString(arguments, startingIndex));
                    startingIndex += Convention.FIELD_SIZE;
                }
                return new CompetitiveMember(name, birthDate, phoneNumber, email, startDate, moneyOwed, seniorTeam, activeDisciplines, bestTrainingResults, conventions);
            }
        }
    }

    public String getStringToSave() {
        return this.name + "," + this.birthDate.toEpochDay() + "," + this.phoneNumber + "," + this.email + "," + this.active + "," + this.senior + "," + this.moneyOwed;
    }



}
