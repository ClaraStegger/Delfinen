import java.time.LocalDate;
import java.util.*;
 public class ActiveMember extends  Member {
     public static final int FIELD_SIZE = 5; //because the boolean array has a size of 4
    private boolean[] activeDisciplines;//size of 4
     private boolean seniorTeam;

    public ActiveMember(String name, LocalDate birthDate, String phoneNumber, String email, LocalDate startDate, boolean seniorTeam, boolean[] activeDisciplines){
        super(name, birthDate, phoneNumber, email, startDate);
        this.activeDisciplines = activeDisciplines;
        this.seniorTeam = seniorTeam;
    }

     @Override
     public String getStringToSave() {
         String stringToSave = super.getStringToSave() + "," + this.seniorTeam;
         for (boolean active : this.activeDisciplines) {
             stringToSave += "," + active;
         }
         return stringToSave;
     }

     @Override
     public boolean isActive() {
         return true;
     }

     @Override
     public boolean isOnSeniorTeam() {
         return this.seniorTeam;
     }

     public void setIsOnSeniorTeam(boolean seniorTeam) {
         this.seniorTeam = seniorTeam;
     }

     @Override
     public List<String> getActiveDisciplines() {
         List<String> activeDisciplineText =new ArrayList<>();
         if (this.activeDisciplines[0]) {
             activeDisciplineText.add("Butterfly");
         }
         if (this.activeDisciplines[1]) {
             activeDisciplineText.add("Crawl");
         }
         if (this.activeDisciplines[2]) {
             activeDisciplineText.add("Backcrawl");
         }
         if (this.activeDisciplines[3]) {
             activeDisciplineText.add("Breaststroke");
         }
         return activeDisciplineText;
     }

     public void setActiveDisciplines(boolean[] activeDisciplines) {
         this.activeDisciplines = activeDisciplines;
     }
 }
