import java.util.*;
import java.time.*;
import java.time.format.*;
import java.time.temporal.*;

public class Member {
    public static final int FIELD_SIZE = 6;
    private String name;
    private LocalDate birthDate;
    private String phoneNumber;
    private String email;
    private LocalDate startDate;
    private double moneyOwed;//calculate

    public Member(String name, LocalDate birthDate, String phoneNumber, String email, LocalDate startDate) {
        this.name = name;
        this.birthDate = birthDate;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.startDate = startDate;
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
                + "\nMoney owed: " + Math.round(this.getMoneyOwed()*100.0D)/ 100.0D
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
        //this.name, this.birthDate, this.phoneNumber, this.email, this.active, this.senior, this.moneyOwed;
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
            return new Member(name, birthDate, phoneNumber, email, startDate).setMoneyOwed(moneyOwed);
        } else {
            boolean seniorTeam = Boolean.parseBoolean(arguments[6]);
            boolean[] activeDisciplines = new boolean[4];
            for (int i = 0; i < activeDisciplines.length; i++) {
                activeDisciplines[i] = Boolean.parseBoolean(arguments[7 + i]);
            }
            if (arguments.length == FIELD_SIZE + ActiveMember.FIELD_SIZE) {//should be 6 + 5 so 11
                return new ActiveMember(name, birthDate, phoneNumber, email, startDate, seniorTeam, activeDisciplines).setMoneyOwed(moneyOwed);
            } else {
                LocalDate[] datesOfBestTrainingResults =new LocalDate[4];
                for (int i = 0; i < datesOfBestTrainingResults.length; i++) {
                    String argument = arguments[11 + i];
                    if (argument != null && !"null".equals(argument)) {
                        datesOfBestTrainingResults[i] = LocalDate.ofEpochDay(Long.parseLong(argument));
                    }
                }
                int[] bestTrainingResults = new int[4];
                for (int i = 0; i < bestTrainingResults.length; i++) {
                    bestTrainingResults[i] = Integer.parseInt(arguments[15 + i]);
                }
                List<Convention> conventions = new ArrayList<>();
                int startingIndex = 15 + bestTrainingResults.length;//used to get conventions
                while (arguments.length > startingIndex) {
                    conventions.add(Convention.fromString(arguments, startingIndex));
                    startingIndex += Convention.FIELD_SIZE;
                }
                return new CompetitiveMember(name, birthDate, phoneNumber, email, startDate, seniorTeam, activeDisciplines, datesOfBestTrainingResults,bestTrainingResults, conventions).setMoneyOwed(moneyOwed);
            }
        }
    }

    public String getStringToSave() {
        return this.name + "," + this.birthDate.toEpochDay() + "," + this.phoneNumber + "," + this.email + "," + this.startDate.toEpochDay() + "," + this.moneyOwed;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDateString(LocalDate date) {
        //http://docs.oracle.com/javase/8/docs/api/java/time/format/DateTimeFormatter.html
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd LLLL yyyy");
        return date.format(formatter);
    }

    public LocalDate getBirthDate() {
        return this.birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }
    public long getAge() {
        return ChronoUnit.YEARS.between(this.birthDate, LocalDate.now(ZoneId.of("Europe/Paris")));
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getStartDate() {
        return this.startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public double getSubscriptionFee() {
        if (this.isActive()) {
            long age = this.getAge();
            if (age >= 60) {
                return 1200;//For medlemmer over 60 år gives der 25 % rabat af seniortaksten.
            } else if (age >= 18) {
                return 1600;//for seniorsvømmere (18 år og over) 1600 kr. årligt
            } else {
                return 1000;//For aktive medlemmer er kontingentet for ungdomssvømmere (under 18 år) 1000 kr. årligt,
            }
        } else {
            return 500;
        }
    }


    public double getMoneyOwed() {
        return this.moneyOwed;
    }

    public Member setMoneyOwed(double moneyOwed) {
        this.moneyOwed = moneyOwed;
        return this;
    }

    public boolean isActive() {
        return false;
    }
    public boolean isOnSeniorTeam() {
        return false;
    }
    public boolean isCompetitiveMember() {
        return false;
    }

    public List<String> getActiveDisciplines() {
        return new ArrayList<>();
    }

    public LocalDate[] getDatesOfBestTrainingResults() {
        return null;
    }
    public int[] getBestTrainingResults() {
        return null;
    }

    public int getTeamIndex(){
        if (this.isCompetitiveMember()) {
            if (this.isOnSeniorTeam()) {
                return 3;// Senior Competitive Team
            } else {
                return 2;// Junior Competitive Team
            }
        } else {
            if (this.isOnSeniorTeam()) {
                return 1;// Senior Team
            } else {
                return 0;// Junior Team
            }
        }
    }
}
