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
         for (int i = 0; i < this.activeDisciplines.length; i++) {
             boolean active =this.activeDisciplines[i];
             if (active) {
                 activeDisciplineText.add(Main.getDisciplineFromIndex(i));
             }
         }
         return activeDisciplineText;
     }

     public void setActiveDisciplines(boolean[] activeDisciplines) {
         this.activeDisciplines = activeDisciplines;
     }
 }
