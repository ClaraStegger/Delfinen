import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.time.*;

public class CompetitiveMember extends ActiveMember {
    private int[] bestTrainingResults;//butterfly, crawl, rygcrawl og brystsvømning
    private List<Convention> conventions;

    public CompetitiveMember(String name, LocalDate birthDate, String phoneNumber, String email, LocalDate startDate, boolean seniorTeam, boolean[] activeDisciplines) {
        this(name,birthDate,phoneNumber,email,startDate, seniorTeam, activeDisciplines, new int[4], new ArrayList<Convention>());
    }
    public CompetitiveMember(String name, LocalDate birthDate, String phoneNumber, String email, LocalDate startDate, boolean seniorTeam, boolean[] activeDisciplines, int[] bestTrainingResults, List<Convention> conventions) {
        super(name,birthDate,phoneNumber,email,startDate, seniorTeam,activeDisciplines);
        this.bestTrainingResults = bestTrainingResults;
        this.conventions= conventions;
    }

    @Override
    public String getStringToSave() {
        String stringToSave = super.getStringToSave();
        for (int bestTrainingResult : this.bestTrainingResults) {
            stringToSave += "," + bestTrainingResult;
        }
        for (Convention convention : this.conventions) {
            stringToSave += "," + convention.getStringToSave();
        }
        return stringToSave;
    }

    @Override
    public boolean isCompetitiveMember() {
        return true;
    }
}


