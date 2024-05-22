import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.time.*;

public class CompetitiveMember extends ActiveMember {
    private int[] bestTrainingResults;//butterfly, crawl, rygcrawl og brystsvømning
    private LocalDate[] datesOfBestTrainingResults;
    private List<Convention> conventions;

    public CompetitiveMember(String name, LocalDate birthDate, String phoneNumber, String email, LocalDate startDate, boolean seniorTeam, boolean[] activeDisciplines) {
        this(name, birthDate, phoneNumber, email, startDate, seniorTeam, activeDisciplines, new LocalDate[4], new int[4], new ArrayList<Convention>());
    }

    public CompetitiveMember(String name, LocalDate birthDate, String phoneNumber, String email, LocalDate startDate, boolean seniorTeam, boolean[] activeDisciplines, LocalDate[] datesOfBestTrainingResults, int[] bestTrainingResults, List<Convention> conventions) {
        super(name, birthDate, phoneNumber, email, startDate, seniorTeam, activeDisciplines);
        this.datesOfBestTrainingResults = datesOfBestTrainingResults;
        this.bestTrainingResults = bestTrainingResults;
        this.conventions = conventions;
    }

    @Override
    public String getStringToSave() {
        String stringToSave = super.getStringToSave();
        for (LocalDate dateOfBestTrainingResult : this.datesOfBestTrainingResults) {
            stringToSave += "," + dateOfBestTrainingResult.toEpochDay();
        }
        for (int bestTrainingResult : this.bestTrainingResults) {
            stringToSave += "," + bestTrainingResult;
        }
        for (Convention convention : this.conventions) {
            stringToSave += "," + convention.getStringToSave();
        }
        return stringToSave;
    }

    @Override
    public LocalDate[] getDatesOfBestTrainingResults() {
        return this.datesOfBestTrainingResults;
    }

    @Override
    public int[] getBestTrainingResults() {
        return this.bestTrainingResults;
    }

    @Override
    public boolean isCompetitiveMember() {
        return true;
    }
}


