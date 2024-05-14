import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.time.*

public class CompetitiveMember extends Member {
    private int activeDiscipline;//index used for results
    private int[] bestTrainingResults;//butterfly, crawl, rygcrawl og brystsvømning
    private List<Convention> conventions;

    public CompetitiveMember(String name, LocalDate birthDate, String phoneNumber, String email, boolean active, boolean senior, double moneyOwed, int activeDiscipline) {
        this(name,birthDate,phoneNumber,email,active,senior,moneyOwed, activeDiscipline, new int[4], new ArrayList<Convention>());
    }
    public CompetitiveMember(String name, LocalDate birthDate, String phoneNumber, String email, boolean active, boolean senior, double moneyOwed, int activeDiscipline, int[] bestTrainingResults, List<Convention> conventions) {
        super(name,birthDate,phoneNumber,email,active,senior,moneyOwed);
        this.bestTrainingResults = bestTrainingResults;
        this.activeDiscipline = activeDiscipline;
        this.conventions= conventions;

    }

    @Override
    public String getStringToSave() {
        String stringToSave = super.getStringToSave() + "," + this.activeDiscipline;
        for (int bestTrainingResult : this.bestTrainingResults) {
            stringToSave += "," + bestTrainingResult;
        }
        for (Convention convention : this.conventions) {
            stringToSave += "," + convention.getStringToSave();
        }
        return stringToSave;
    }
}
