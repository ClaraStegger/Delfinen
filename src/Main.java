import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.time.LocalDate;
import java.util.*;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Member> members = new ArrayList<>();
        loadMembers(members);

        members.add(new ActiveMember ("Claus Larsen",LocalDate.parse("2000-10-05"), "12345678", "clag@nfjb", LocalDate.parse("2024-10-05"), 10.0, true,new boolean[]{true,true,true,true}));

        for (Member member : members) {
            System.out.println(member);
        }
        int choice;
        do {
            System.out.println("*----*----*----*----*Menu*----*----*----*----*");
            System.out.println("|          ➤ 1.  Create member               |");
            System.out.println("|          ➤ 2.  Change member information   |");
            System.out.println("|          ➤ 3.                              |");
            System.out.println("|          ➤ 4.                              |");
            System.out.println("|          ➤ 5.  Exit Program                |");
            System.out.println("*----*----*----*----*  </^\\>  *----*----*----*");


            choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    createMember();
                    break;
                case 5:
                    break;
            }
        } while (choice >= 1 && choice <= 4);


    }

    //todo: Oprette menu og undermenu
    //todo: Oprette medlemmer.



    private static void createMember() {
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter the members name:");
        String name = scan.nextLine();
        scan.nextLine();

        System.out.println("Enter the member's birth of date: (year-month-day)");
        LocalDate birthDate = LocalDate.parse(scan.nextLine());

        System.out.println("Enter the member's phone number:");
        int phoneNumber = scan.nextInt();

        System.out.println("Enter the members e-mail:");
        String email = scan.nextLine();


    }

    private static void loadMembers(List<Member> members) {
        try {
            members.clear();
            Scanner input = new Scanner(new File("src/member.txt"));
            while (input.hasNextLine()) {
                String line = input.nextLine();
                Member member = Member.fromString(line);
                members.add(member);
            }
            input.close();
        } catch (FileNotFoundException ignored) {
        }
    }

    private static void saveMembers(List<Member> members) {
        try {
            PrintStream output = new PrintStream(new File("src/member.txt"));
            for (Member member : members) {
                output.println(member.getStringToSave());
            }
            output.close();
        } catch (FileNotFoundException ignored) {
        }
    }
}
