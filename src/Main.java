import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        List<List<Member>> teamMembers = new ArrayList<>();
        List<Member> members = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
        loadMembers(members, teamMembers);

        //members.add(new ActiveMember("Claus Larsen", LocalDate.parse("2000-10-05"), "12345678", "clag@nfjb", LocalDate.parse("2024-10-05"), true, new boolean[]{true, true, true, true}).setMoneyOwed(10.0));

        String choice;
        do {
            System.out.println("*----*----*----*----*----*----*MAIN MENU*----*----*----*----*----*----*");
            System.out.println("|          ➤ 1.  Member Menu                                         |");
            System.out.println("|          ➤ 2.  Team Menu                                           |");
            System.out.println("|          ➤ 3.  Cashier Menu                                        |");
            System.out.println("|          ➤ 4.  Exit Program                                        |");
            System.out.println("*----*----*----*----*----*----* </^\\>  *----*----*----*----*----*----*");

            choice = scanner.nextLine();
            switch (choice) {
                case "1":
                    memberMenu(members, teamMembers);
                    break;
                case "2":
                    teamMenu(members, teamMembers);
                    break;
                case "3":
                    cashierMenu(members);
                    break;
            }
        } while (!choice.equals("4"));
    }

    private static void memberMenu(List<Member> members, List<List<Member>> teamMembers) {
        Scanner scanner = new Scanner(System.in);
        boolean exitMenu = false;
        do {
            System.out.println("---> MEMBER LIST<---");
            for (Member memberInList : members) {
                System.out.println(memberInList);
                System.out.println();
            }
            System.out.println("""
                    >> FUNCTIONS <<
                    1 -  Create Member
                    2 -  Change Member
                    3 -  Delete Member
                    4 -  Exit Member Menu
                    """);
            String answer = scanner.next();
            switch (answer) {
                case "1":
                    createMember(members, teamMembers);
                    break;
                case "2":
                    changeMember(members, teamMembers);
                    break;
                case "3":
                    deleteMember(members, teamMembers);
                    break;
                case "4":
                    exitMenu = true;
                    break;
                default:
                    System.out.print("I did not get your answer. Try again.");
                    break;
            }
        } while (!exitMenu);
    }

    private static void createMember(List<Member> members, List<List<Member>> teamMembers) { //metode til at oprette medlemmer
        Member member;
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter the Members first- and lastname:");
        String name = scan.nextLine();
        System.out.println("Enter the Member's birth of date: (year-month-day)");
        LocalDate birthDate = LocalDate.parse(scan.nextLine());
        System.out.println("Enter the Member's phone number:");
        String phoneNumber = scan.nextLine();
        System.out.println("Enter the Members e-mail:");
        String email = scan.nextLine();
        System.out.println("Enter the Members start date (year-month-day)");
        LocalDate startDate = LocalDate.parse(scan.nextLine());
        System.out.println("Is the Member active? Enter 'yes', otherwise enter 'no' if it Passive.");
        boolean active = getBooleanFromScanner();
        if (active) {
            System.out.println("Is the Member on the senior team? Enter yes, otherwise enter no if they're on the junior team");
            boolean seniorTeam = getBooleanFromScanner();
            System.out.println("Is the Member active in the Butterfly discipline? Enter yes/no");
            boolean butterFly = getBooleanFromScanner();
            System.out.println("Is the Member active in the Crawl discipline? Enter yes/no");
            boolean crawl = getBooleanFromScanner();
            System.out.println("Is the Member active in the Backcrawl discipline? Enter yes/no");
            boolean backCrawl = getBooleanFromScanner();
            System.out.println("Is the Member active in the Breaststroke discipline? Enter yes/no");
            boolean breaststroke = getBooleanFromScanner();
            boolean[] activeDisciplines = new boolean[]{butterFly, crawl, backCrawl, breaststroke};
            System.out.println("Is the Member Competitive? Enter 'yes', otherwise enter 'no' if the Member is Exerciser.");
            boolean competitive = getBooleanFromScanner();
            if (competitive) {
                member = new CompetitiveMember(name, birthDate, phoneNumber, email, startDate, seniorTeam, activeDisciplines);
            } else {
                member = new ActiveMember(name, birthDate, phoneNumber, email, startDate, seniorTeam, activeDisciplines);
            }
        } else {
            member = new Member(name, birthDate, phoneNumber, email, startDate);
        }
        updateMoneyOwed(member, startDate);
        members.add(member);
        addMemberToTeamList(teamMembers, member);
        saveMembers(members);
    }

    private static void changeMember(List<Member> members, List<List<Member>> teamMembers) {
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter the phone number of the Member, you want to change:");
        String phoneNumberOfMemberToChange = scan.nextLine();
        int indexOfMemberToChange = -1;
        Member memberToChange = null;
        for (int i = 0; i < members.size(); i++) {
            Member member = members.get(i);
            if (member.getPhoneNumber().equals(phoneNumberOfMemberToChange)) {
                indexOfMemberToChange = i;
                memberToChange = member;
                break;
            }
        }
        if (memberToChange != null) {
            String choice;
            do {
                System.out.println("""
                        Now specify what you want to change:
                        Enter the number next to the information you want to change.
                        1 - Name
                        2 - Birthdate
                        3 - Phone number
                        4 - Email
                        5 - Start date
                        6 - Activity type
                        7 - Return to Member Menu
                        """);
                choice = scan.nextLine();
                switch (choice) {
                    case "1":
                        System.out.println("Enter the Members new first- and lastname");
                        memberToChange.setName(scan.nextLine());
                        break;
                    case "2":
                        System.out.println("Enter the new birthdate of the member");
                        memberToChange.setBirthDate(LocalDate.parse(scan.nextLine()));
                        break;
                    case "3":
                        System.out.println("Enter the new phone number of the Member");
                        memberToChange.setPhoneNumber(scan.nextLine());
                        break;
                    case "4":
                        System.out.println("Enter the email of the Member");
                        memberToChange.setEmail(scan.nextLine());
                        break;
                    case "5":
                        if (LocalDate.now().isAfter(memberToChange.getStartDate())) {
                            System.out.println("This member has already started, so you cannot change their start date");
                        } else {
                            System.out.println("Enter the new starting date of the member");
                            LocalDate startDate = LocalDate.parse(scan.nextLine());
                            memberToChange.setStartDate(startDate);
                            updateMoneyOwed(memberToChange, startDate);
                        }
                        break;
                    case "6":
                        System.out.println("Is the Member active? Enter 'yes', otherwise enter 'no' if it Passive.\"");
                        boolean active = getBooleanFromScanner();
                        if (!active) {
                            if (memberToChange.isActive()) {
                                members.set(indexOfMemberToChange, new Member(memberToChange.getName(), memberToChange.getBirthDate(), memberToChange.getPhoneNumber(), memberToChange.getEmail(), memberToChange.getStartDate()));
                            }
                            break;
                        }

                        int previousTeamIndex = memberToChange.getTeamIndex();
                        System.out.println("Is the Member on the senior team? Enter yes, otherwise enter no if they're on the junior team");
                        boolean seniorTeam = getBooleanFromScanner();
                        System.out.println("Is the Member active in the Butterfly discipline? Enter yes/no");
                        boolean butterFly = getBooleanFromScanner();
                        System.out.println("Is the Member active in the Crawl discipline? Enter yes/no");
                        boolean crawl = getBooleanFromScanner();
                        System.out.println("Is the Member active in the Backcrawl discipline? Enter yes/no");
                        boolean backCrawl = getBooleanFromScanner();
                        System.out.println("Is the Member active in the Breaststroke discipline? Enter yes/no");
                        boolean breaststroke = getBooleanFromScanner();
                        boolean[] activeDisciplines = new boolean[]{butterFly, crawl, backCrawl, breaststroke};
                        System.out.println("Is the Member Competitive? Enter 'yes', otherwise enter 'no' if the Member is Exerciser.");
                        boolean competitive = getBooleanFromScanner();
                        if (competitive) {
                            if (memberToChange instanceof CompetitiveMember competitiveMemberToChange) {
                                competitiveMemberToChange.setIsOnSeniorTeam(seniorTeam);
                                competitiveMemberToChange.setActiveDisciplines(activeDisciplines);
                            } else {
                                members.set(indexOfMemberToChange, memberToChange = new CompetitiveMember(memberToChange.getName(), memberToChange.getBirthDate(), memberToChange.getPhoneNumber(), memberToChange.getEmail(), memberToChange.getStartDate(), seniorTeam, activeDisciplines));
                            }
                        } else {
                            members.set(indexOfMemberToChange, memberToChange = new ActiveMember(memberToChange.getName(), memberToChange.getBirthDate(), memberToChange.getPhoneNumber(), memberToChange.getEmail(), memberToChange.getStartDate(), seniorTeam, activeDisciplines));
                        }
                        int teamIndex = memberToChange.getTeamIndex();
                        if (teamIndex != previousTeamIndex) {
                            removeMemberFromAllTeamLists(teamMembers, memberToChange);
                            addMemberToTeamList(teamMembers, memberToChange);
                        }
                        break;
                }
                saveMembers(members);
            } while (!choice.equals("7"));
        } else {
            System.out.println("Could not find a member with the phone number: " + phoneNumberOfMemberToChange);
        }
    }

    private static void deleteMember(List<Member> members, List<List<Member>> teamMembers) {
        boolean save = false;
        boolean found = false;
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter the phone number of the member you want to delete?");
        String phoneNumberOfMemberToDelete = scan.nextLine();
        for (int i = 0; i < members.size(); i++) {
            Member member = members.get(i);
            if (phoneNumberOfMemberToDelete.equals(member.getPhoneNumber())) {
                System.out.println("Are you sure you want to delete " + member + ". Enter yes/no");
                if (getBooleanFromScanner()) {
                    removeMemberFromAllTeamLists(teamMembers, member);
                    members.remove(i);
                    System.out.println("Member has been deleted");
                    save = true;
                }
                found = true;
                break;
            }
        }
        //
        if (!found) {
            System.out.println("Could not find a member with the phone number: " + phoneNumberOfMemberToDelete);
        } else if (save) {
            saveMembers(members);
        }
    }

    private static void updateMoneyOwed(Member member, LocalDate startDate) {
        double pricePerDay = member.getSubscriptionFee() / 365.0;
        LocalDate endOfYear = startDate.withYear(startDate.getYear() + 1).withDayOfYear(1);
        long daysUntilEndOfYear = ChronoUnit.DAYS.between(startDate, endOfYear);
        double moneyOwed = pricePerDay * daysUntilEndOfYear;
        System.out.printf("Has the member paid their starting fee of %.2f kr. Enter yes/no", moneyOwed);
        boolean hasPaid = getBooleanFromScanner();
        if (!hasPaid) {
            member.setMoneyOwed(moneyOwed);
        } else {
            member.setMoneyOwed(0);
        }
    }

    private static void teamMenu(List<Member> members, List<List<Member>> teamMembers) {
        Scanner scanner = new Scanner(System.in);
        List<Member> juniorTeam = teamMembers.get(0);
        List<Member> seniorTeam = teamMembers.get(1);
        List<Member> juniorCompetitiveTeam = teamMembers.get(2);
        List<Member> seniorCompetitiveTeam = teamMembers.get(3);
        boolean exitMenu = false;
        do {
            System.out.println(">> TEAM LISTS <<");
            System.out.println("1 -  Junior Team (" + juniorTeam.size() + ")");
            System.out.println("2 -  Senior Team (" + seniorTeam.size() + ")");
            System.out.println("3 -  Junior Competitive Team (" + juniorCompetitiveTeam.size() + ")");
            System.out.println("4 -  Senior Competitive Team (" + seniorCompetitiveTeam.size() + ")");
            System.out.println("5 -  Exit Team Menu");
            String answer = scanner.next();
            switch (answer) {
                case "1":
                    System.out.println("--> JUNIOR TEAM <--");
                    seeTeamList(members, juniorTeam, false);
                    break;
                case "2":
                    System.out.println("--> SENIOR TEAM <--");
                    seeTeamList(members, seniorTeam, false);
                    break;
                case "3":
                    System.out.println("--> JUNIOR COMPETITIVE TEAM <--");
                    seeTeamList(members, juniorCompetitiveTeam, true);
                    break;
                case "4":
                    System.out.println("--> SENIOR COMPETITIVE TEAM <--");
                    seeTeamList(members, seniorCompetitiveTeam, true);
                    break;
                case "5":
                    exitMenu = true;
                    break;
                default:
                    System.out.print("I did not get your answer. Try again.");
                    break;
            }
        } while (!exitMenu);
    }

    private static void seeTeamList(List<Member> members, List<Member> teamList, boolean competitive) {
        Scanner scan = new Scanner(System.in);
        for (Member member : teamList) {
            String string = "Name: " + member.getName()
                    + "\nBirthdate: " + member.getDateString(member.getBirthDate())
                    + "\nAge: " + member.getAge()
                    + "\nPhoneNumber: " + member.getPhoneNumber()
                    + "\nEmail: " + member.getEmail();
            string += "\nActive Disciplines ";
            List<String> activeDisciplines = member.getActiveDisciplines();
            for (int i = 0; i < activeDisciplines.size(); i++) {
                String activeDiscipline = activeDisciplines.get(i);
                if (i == 0) {
                    string += activeDiscipline;
                } else {
                    string += ", " + activeDiscipline;
                }
            }
            int[] bestTrainingResults = member.getBestTrainingResults();
            if (bestTrainingResults != null) {
                LocalDate[] datesOfBestTrainingResults = member.getDatesOfBestTrainingResults();
                List<String> bestTrainingResultText = new ArrayList<>();
                for (int i = 0; i < bestTrainingResults.length; i++) {
                    int milliseconds = bestTrainingResults[i];
                    if (milliseconds > 0) {
                        String line = "\n   " + getDisciplineFromIndex(i);
                        LocalDate date = datesOfBestTrainingResults[i];
                        if (date != null) {
                            line += "(" + member.getDateString(date) + ")";
                        }
                        line += ": " + getTimeFromMilliseconds(milliseconds);
                        bestTrainingResultText.add(line);
                    }
                }
                if (!bestTrainingResultText.isEmpty()) {
                    string += "\nBest Training Results:";
                    for (String line : bestTrainingResultText) {
                        string += line;
                    }
                }
            }
            System.out.println(string);
        }
        if (competitive) {
            boolean exitMenu = false;
            do {
                System.out.println("""
                                                
                        >> FUNCTIONS <<
                        1 -  Add Training Result
                        2 -  Add Swimming Gala
                        3 -  Top 5
                        4 -  Exit Team Menu
                        """);
                String choice = scan.nextLine();
                switch (choice) {
                    case "1":
                        addTrainingResult(members, teamList);
                        break;
                    case "2":
                        //addSwimmingGala()
                        break;
                    case "3":
                        break;
                    case "4":
                        exitMenu = true;
                        break;
                    default:
                        System.out.print("I did not get your answer. Try again.");
                        break;
                }
            } while (!exitMenu);
        }
    }

    private static void addTrainingResult(List<Member> members, List<Member> teamList) {
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter the phone number of the Member, you want to add a training result to:");
        String phoneNumberOfMemberToChange = scan.nextLine();
        CompetitiveMember memberToAddTrainingResultTo = null;
        for (int i = 0; i < teamList.size(); i++) {
            Member member = teamList.get(i);
            if (member.isCompetitiveMember() && member.getPhoneNumber().equals(phoneNumberOfMemberToChange)) {
                memberToAddTrainingResultTo = (CompetitiveMember) member;
                break;
            }
        }
        if (memberToAddTrainingResultTo != null) {
            System.out.println("""
                    Choose a discipline:
                     1 - Butterfly
                     2 - Crawl
                     3 - Backcrawl
                     4 - Breaststroke
                     """);
            int choice = scan.nextInt() - 1;
            scan.nextLine();
            int[] trainingResults = memberToAddTrainingResultTo.getBestTrainingResults();
            if (choice >= 0 && choice < trainingResults.length) {
                System.out.println("Enter race time (Format: min:sec:milli)");
                int newResult = getMillisecondsFromTime(scan.nextLine());
                int existingResult = trainingResults[choice];
                if (existingResult <= 0 || existingResult >= newResult) {
                    System.out.println("Enter date for training result (Format: year-month-day):");
                    LocalDate[] datesOfBestTrainingResults = memberToAddTrainingResultTo.getDatesOfBestTrainingResults();
                    datesOfBestTrainingResults[choice] = LocalDate.parse(scan.nextLine());
                    trainingResults[choice] = newResult;
                    saveMembers(members);
                    System.out.println("The race time has been saved");
                } else {
                    System.out.println("Swimmer's previous race time was quicker");
                }
            } else {
                System.out.println("Invalid choice");
            }
        }
    }

    private static void addMemberToTeamList(List<List<Member>> teamMembers, Member member) {
        teamMembers.get(member.getTeamIndex()).add(member);
    }

    private static void removeMemberFromAllTeamLists(List<List<Member>> teamMembers, Member member) {
        for (List<Member> teamList : teamMembers) {
            teamList.remove(member);
        }
    }

    private static void cashierMenu(List<Member> members) {
        Scanner scan = new Scanner(System.in);
        System.out.println("Members in arrears");
        for (Member member : members) {
            double moneyOwed = member.getMoneyOwed();
            if (moneyOwed > 0) {
                String string = "Name: " + member.getName()
                        + "\nPhoneNumber: " + member.getPhoneNumber()
                        + "\nEmail: " + member.getEmail()
                        + "\nStart Date: " + member.getDateString(member.getStartDate())
                        + "\nSubscription fee: " + member.getSubscriptionFee()
                        + "\nMoney owed: " + Math.round(moneyOwed * 100.0D) / 100.0D;
                System.out.println(string);
            }
        }
        boolean exitMenu = false;
        do {
            System.out.println("""
                    >> FUNCTIONS <<
                    1 - Change member in arrears
                    2 - Display expected annual income;
                    3 - Exit Cashier Menu
                    """);
            String choice = scan.nextLine();
            switch (choice) {
                case "1":
                    changeMemberInArrears(members);
                    break;
                case "2":
                    getExpectedAnnualIncome(members);
                    break;
                case "3":
                    exitMenu = true;
                    break;
            }
        } while (!exitMenu);
    }

    private static void changeMemberInArrears(List<Member> members) {
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter the phone number of the Member, you want to change arrears of:");
        String phoneNumberOfMemberToChange = scan.nextLine();
        boolean save = false;
        for (Member member : members) {
            double moneyOwed = member.getMoneyOwed();
            if (moneyOwed > 0 && member.getPhoneNumber().equals(phoneNumberOfMemberToChange)) {
                System.out.println("Enter amount of money that the member has paid");
                double moneyPaid = scan.nextDouble();
                double remainingMoneyOwed = moneyOwed - moneyPaid;
                if (remainingMoneyOwed < 0) {
                    System.out.println("You need to return " + (-remainingMoneyOwed) + " kr. to " + member.getName());
                    remainingMoneyOwed = 0;
                }
                member.setMoneyOwed(remainingMoneyOwed);
                System.out.println(member.getName() + " now owes " + member.getMoneyOwed() + " kr.");
                save = true;
                break;

            }
        }
        if (save) {
            saveMembers(members);
        } else {
            System.out.println("Could not find a member with the phone number: " + phoneNumberOfMemberToChange + ", who owes money");
        }
    }

    private static void getExpectedAnnualIncome(List<Member> members) {
        double expectedIncome = 0;
        for (Member member : members) {
            expectedIncome += member.getSubscriptionFee();
        }
        System.out.println("Expected annual income is " + expectedIncome + " kr.");
    }

    private static void loadMembers(List<Member> members, List<List<Member>> teamMembers) { //metode til at printe medlemmer til konsollen
        try {
            members.clear();
            teamMembers.clear();
            teamMembers.add(new ArrayList<>());// Junior Team
            teamMembers.add(new ArrayList<>());// Senior Team
            teamMembers.add(new ArrayList<>());// Junior Competitive Team
            teamMembers.add(new ArrayList<>());// Senior Competitive Team
            Scanner input = new Scanner(new File("src/members.txt"));
            while (input.hasNextLine()) {
                String line = input.nextLine();
                Member member = Member.fromString(line);
                members.add(member);
                addMemberToTeamList(teamMembers, member);
            }
            input.close();
        } catch (FileNotFoundException ignored) {
        } catch (Throwable exception) {
            exception.printStackTrace();
        }
    }

    private static void saveMembers(List<Member> members) { //metode til at gemme medlemmer til filen
        try {
            PrintStream output = new PrintStream(new File("src/members.txt"));
            for (Member member : members) {
                output.println(member.getStringToSave());
            }
            output.close();
        } catch (FileNotFoundException ignored) {
        }
    }

    private static boolean getBooleanFromScanner() {
        String nextLine = new Scanner(System.in).nextLine();
        return nextLine.equalsIgnoreCase("yes") || nextLine.equalsIgnoreCase("ja");
    }

    private static int getMillisecondsFromTime(String nextLine) {
        String[] arguments = nextLine.split(":");
        int minutes = Integer.parseInt(arguments[0]);
        int seconds = Integer.parseInt(arguments[1]);
        int milliseconds = Integer.parseInt(arguments[2]);
        return milliseconds + (seconds * 1000) + (minutes * 60000);
    }

    private static String getTimeFromMilliseconds(int result) {
        int minutes = result / 60000;
        int seconds = (result / 1000) - minutes * 60;
        int milliseconds = (result - (seconds * 1000)) - (minutes * 60000);
        return minutes + ":" + seconds + ":" + milliseconds;
    }

    public static String getDisciplineFromIndex(int index) {
        switch (index) {
            default:
                return "Butterfly";
            case 1:
                return "Crawl";
            case 2:
                return "Backcrawl";
            case 3:
                return "Breaststroke";
        }
    }
}
