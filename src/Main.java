import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class Main {

    public static void main(String[] args) {
        List<List<Member>> teamMembers = new ArrayList<>();
        List<Member> members = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
        loadMembers(members, teamMembers);
        //try {
        //    generateRandomMember(members, teamMembers);
        //} catch (Exception exception) {
        //    exception.printStackTrace();
        //}

        //members.add(new ActiveMember("Claus Larsen", LocalDate.parse("2000-10-05"), "12345678", "clag@nfjb", LocalDate.parse("2024-10-05"), true, new boolean[]{true, true, true, true}).setMoneyOwed(10.0));

        String choice;
        do {
            System.out.println("*----*----*----*----*----*----*MAIN MENU*----*----*----*----*----*----*");
            System.out.println("|          ➤ 1.  Member Menu                                          |");
            System.out.println("|          ➤ 2.  Team Menu                                            |");
            System.out.println("|          ➤ 3.  Cashier Menu                                         |");
            System.out.println("|          ➤ 4.  Exit Program                                         |");
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
                        2 -  Add Swimming Gala Result
                        3 -  See Top 5
                        4 -  Exit Team Menu
                        """);
                String choice = scan.nextLine();
                switch (choice) {
                    case "1":
                        addTrainingResult(members, teamList);
                        break;
                    case "2":
                        addSwimGalaResults(members, teamList);
                        break;
                    case "3":
                        seeTopFive(teamList);
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
        String phoneNumber = scan.nextLine();
        CompetitiveMember memberToAddTrainingResultTo = null;
        for (Member member : teamList) {
            if (member.isCompetitiveMember() && member.getPhoneNumber().equals(phoneNumber)) {
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
        } else {
            System.out.println("Could not find a member with the phone number: " + phoneNumber);
        }
    }

    private static void addSwimGalaResults(List<Member> members, List<Member> teamList) {
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter the phone number of the Member, you want to add a training result to:");
        String phoneNumber = scan.nextLine();
        CompetitiveMember memberFound = null;
        for (Member member : teamList) {
            if (member.isCompetitiveMember() && member.getPhoneNumber().equals(phoneNumber)) {
                memberFound = (CompetitiveMember) member;
                break;
            }
        }
        if (memberFound != null) {
            System.out.println("Enter the date for the Swimming Gala (format: year-month-day):");
            LocalDate date = LocalDate.parse(scan.nextLine());
            System.out.println("Enter the location for the Swimming Gala:");
            String location = scan.nextLine();
            int[] results = new int[4];
            for (int i = 0; i < results.length; i++) {
                System.out.println("Enter race time for the " + getDisciplineFromIndex(i) + " discipline (Format: min:sec:milli) (if not applicable enter '0')");
                results[i] = getMillisecondsFromTime(scan.nextLine());
            }
            Convention convention = new Convention(date, location, results);
            memberFound.getConventions().add(convention);
            saveMembers(members);
            System.out.println("Swimming Gala results has been saved for " + memberFound.getName());
        } else {
            System.out.println("Could not find a member with the phone number: " + phoneNumber);
        }
    }

    private static void seeTopFive(List<Member> teamList) {
        for (int i = 0; i < 4; i++) {
            final int index = i;
            List<Member> topInDiscipline = new ArrayList<>(teamList);
            topInDiscipline.sort(new Comparator<Member>() {
                @Override
                public int compare(Member member1, Member member2) {
                    int[] trainingResultsOne = member1.getBestTrainingResults();
                    int[] trainingResultsTwo = member2.getBestTrainingResults();
                    //
                    int trainingResultOne = trainingResultsOne != null ? trainingResultsOne[index] : Integer.MAX_VALUE;
                    if (trainingResultOne <= 0) {
                        trainingResultOne = Integer.MAX_VALUE;
                    }
                    int trainingResultTwo = trainingResultsTwo != null ? trainingResultsTwo[index] : Integer.MAX_VALUE;
                    if (trainingResultTwo <= 0) {
                        trainingResultTwo = Integer.MAX_VALUE;
                    }
                    //
                    return trainingResultOne - trainingResultTwo;
                }
            });
            if (!topInDiscipline.isEmpty()) {
                System.out.println("\n--> Top 5 - " + getDisciplineFromIndex(index) + " <--");
                for (int j = 0; j < Math.min(topInDiscipline.size(), 5); j++) {
                    Member member = topInDiscipline.get(j);
                    int[] trainingResults = member.getBestTrainingResults();
                    if (trainingResults != null) {
                        int trainingResult = trainingResults[index];
                        if (trainingResult > 0) {
                            int placement = j + 1;
                            String string = placement + ". " + member.getName() + " - " + getTimeFromMilliseconds(trainingResult);
                            System.out.println(string);
                        }
                    }
                }
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
                System.out.println();
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
        String minuteString;
        if (minutes < 10) {
            minuteString = "0" + minutes;
        } else {
            minuteString = String.valueOf(minutes);
        }
        int seconds = (result / 1000) - minutes * 60;
        String secondString;
        if (seconds < 10) {
            secondString = "0" + seconds;
        } else {
            secondString = String.valueOf(seconds);
        }
        int milliseconds = (result - (seconds * 1000)) - (minutes * 60000);
        return minuteString + ":" + secondString + ":" + milliseconds;
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

    private static void generateRandomMember(List<Member> members, List<List<Member>> teamMembers) {
        Random random = new Random();
        List<String> names = new ArrayList<>();
        names.add("Aaron Smith");
        names.add("Abby Johnson");
        names.add("Adam Brown");
        names.add("Alice Davis");
        names.add("Alan Wilson");
        names.add("Amanda Taylor");
        names.add("Andrew Anderson");
        names.add("Angela Thomas");
        names.add("Anthony Jackson");
        names.add("April White");
        names.add("Arthur Harris");
        names.add("Audrey Martin");
        names.add("Austin Thompson");
        names.add("Ava Garcia");
        names.add("Ben Clark");
        names.add("Beth Lewis");
        names.add("Brian Lee");
        names.add("Brianna Walker");
        names.add("Caleb Hall");
        names.add("Camille Young");
        names.add("Cameron King");
        names.add("Carly Wright");
        names.add("Charles Scott");
        names.add("Chloe Green");
        names.add("Chris Adams");
        names.add("Claire Baker");
        names.add("Cody Gonzalez");
        names.add("Courtney Nelson");
        names.add("Colin Carter");
        names.add("Daisy Mitchell");
        names.add("Daniel Perez");
        names.add("Danielle Roberts");
        names.add("David Turner");
        names.add("Denise Phillips");
        names.add("Dylan Campbell");
        names.add("Diana Parker");
        names.add("Edward Evans");
        names.add("Emma Edwards");
        names.add("Ethan Collins");
        names.add("Eva Stewart");
        names.add("Felix Sanchez");
        names.add("Fiona Morris");
        names.add("Frank Rogers");
        names.add("Faith Reed");
        names.add("Gavin Cook");
        names.add("Grace Morgan");
        names.add("George Bell");
        names.add("Gina Murphy");
        names.add("Greg Bailey");
        names.add("Hannah Rivera");
        names.add("Henry Cooper");
        names.add("Holly Richardson");
        names.add("Isaac Cox");
        names.add("Iris Bailey");
        names.add("Ivan Powell");
        names.add("Ivy Howard");
        names.add("Jack Ward");
        names.add("Jenna Brooks");
        names.add("James Foster");
        names.add("Julie Gray");
        names.add("Jason Bryant");
        names.add("Jill Price");
        names.add("Jeff Peterson");
        names.add("Jane Russell");
        names.add("Justin Diaz");
        names.add("Judy Griffin");
        names.add("Kevin Simmons");
        names.add("Kate Bennett");
        names.add("Kyle Ross");
        names.add("Kelly Coleman");
        names.add("Liam Patterson");
        names.add("Laura Jenkins");
        names.add("Luke Perry");
        names.add("Linda Sanders");
        names.add("Logan Long");
        names.add("Lily Patterson");
        names.add("Mark Flores");
        names.add("Megan Hughes");
        names.add("Mason Perry");
        names.add("Molly Sullivan");
        names.add("Matthew Bryant");
        names.add("Mia Bell");
        names.add("Michael Foster");
        names.add("Morgan Simmons");
        names.add("Nathan Price");
        names.add("Natalie Bennett");
        names.add("Noah Coleman");
        names.add("Nora Jenkins");
        names.add("Oliver Brooks");
        names.add("Olivia Gray");
        names.add("Oscar Ross");
        names.add("Paige Russell");
        names.add("Owen Diaz");
        names.add("Penny Griffin");
        names.add("Paul Hughes");
        names.add("Ruby Flores");
        names.add("Peter Perry");
        names.add("Sally Long");
        names.add("Quinn Ward");
        names.add("Sarah Bell");
        names.add("Rachel Gray");
        names.add("Ryan Bryant");
        names.add("Rebecca Foster");
        names.add("Robert Simmons");
        names.add("Samuel Price");
        names.add("Sophie Bennett");
        names.add("Scott Coleman");
        names.add("Stephanie Jenkins");
        names.add("Sean Brooks");
        names.add("Sydney Perry");
        names.add("Spencer Russell");
        names.add("Tara Griffin");
        names.add("Steven Hughes");
        names.add("Taylor Ross");
        names.add("Thomas Diaz");
        names.add("Vanessa Long");
        names.add("Tim Ward");
        names.add("Victoria Bell");
        names.add("Travis Gray");
        names.add("Wendy Bryant");
        names.add("Trevor Foster");
        names.add("Whitney Simmons");
        names.add("Tyler Price");
        names.add("Willow Bennett");
        names.add("Vincent Coleman");
        names.add("Wendy Jenkins");
        names.add("Walter Brooks");
        names.add("Yolanda Bell");
        names.add("Warren Gray");
        names.add("Zoey Ross");
        names.add("Wesley Bryant");
        names.add("Zoe Foster");
        names.add("Wyatt Simmons");
        names.add("Zane Price");
        names.add("Xavier Bennett");
        names.add("Yasmin Coleman");
        names.add("Zachary Jenkins");
        names.add("Angela Walker");
        names.add("Brian Young");
        names.add("Christina King");
        names.add("Dennis Wright");
        names.add("Elaine Scott");
        names.add("Fred Green");
        names.add("Gina Adams");
        names.add("Hector Baker");
        names.add("Irene Gonzalez");
        names.add("Jack Nelson");
        names.add("Karen Carter");
        names.add("Louis Mitchell");
        names.add("Maria Perez");
        names.add("Nicholas Roberts");
        names.add("Olivia Turner");
        names.add("Patrick Phillips");
        names.add("Quinn Campbell");
        names.add("Raymond Parker");
        names.add("Samantha Evans");
        names.add("Theodore Edwards");
        names.add("Ursula Collins");
        names.add("Victor Stewart");
        names.add("Wendy Sanchez");
        names.add("Xavier Morris");
        names.add("Yolanda Rogers");
        names.add("Zeke Reed");
        names.add("Alex Cook");
        names.add("Bella Morgan");
        names.add("Carter Bell");
        names.add("Dana Murphy");
        names.add("Eli Cooper");
        names.add("Fiona Richardson");
        names.add("Gavin Cox");
        names.add("Haley Howard");
        names.add("Ian Ward");
        names.add("Jenna Bailey");
        names.add("Kevin Powell");
        names.add("Laura Patterson");
        names.add("Mason Perry");
        names.add("Nina Long");
        names.add("Oscar Ward");
        names.add("Penny Bailey");
        names.add("Quentin Powell");
        names.add("Rachel Patterson");
        names.add("Steven Perry");
        names.add("Tina Long");
        names.add("Ulysses Ward");
        names.add("Valerie Bailey");
        names.add("William Powell");
        names.add("Xavier Patterson");
        names.add("Yvonne Perry");
        names.add("Zachary Long");
        names.add("Abby Young");
        names.add("Brandon King");
        names.add("Chelsea Wright");
        names.add("Derek Scott");
        names.add("Ella Green");
        names.add("Frank Adams");
        names.add("Grace Baker");
        names.add("Harry Gonzalez");
        names.add("Isla Nelson");
        names.add("Jack Carter");
        names.add("Karen Mitchell");

        for (int i = 0; i < names.size(); i++) {

            int timeOne = random.nextInt(300000) + 1;
            int timeTwo = random.nextInt(300000) + 1;
            int timeThree = random.nextInt(300000) + 1;
            int timeFour = random.nextInt(300000) + 1;

            boolean owesMoney = random.nextInt(4) == 0;
            int moneyOwed = owesMoney ? random.nextInt(1600) + 1 : 0;
            boolean seniorTeam = random.nextBoolean();
            String phoneNumber = "";
            for (int j = 0; j < 8; j++) {
                phoneNumber += random.nextInt(10);
            }

            int year = 1950 + random.nextInt(75);
            int month = random.nextInt(12) + 1;
            int day = random.nextInt(28) + 1;

            String monthString = String.valueOf(month);
            if (month < 10) {
                monthString = "0" + monthString;
            }

            String dayString = String.valueOf(day);
            if (day < 10) {
                dayString = "0" + dayString;
            }

            LocalDate birthDate = LocalDate.parse(year + "-" + monthString + "-" + dayString);

            int joinYear = 2014 + random.nextInt(11);
            int joinMonth = random.nextInt(12) + 1;
            int joinDay = random.nextInt(28) + 1;


            String joinMonthString = String.valueOf(joinMonth);
            if (joinMonth < 10) {
                joinMonthString = "0" + joinMonthString;
            }

            String joinDayString = String.valueOf(joinDay);
            if (joinDay < 10) {
                joinDayString = "0" + joinDayString;
            }

            LocalDate joinDate = LocalDate.parse(joinYear + "-" + joinMonthString + "-" + joinDayString);


            String line = names.get(i) + "," + birthDate.toEpochDay() + "," + phoneNumber + ",email@gmail.com," + joinDate.toEpochDay() + "," + moneyOwed;
            if (random.nextInt(8) != 0) {
                line += "," + seniorTeam + "," + random.nextBoolean() + "," + random.nextBoolean() + "," + random.nextBoolean() + "," + random.nextBoolean();
                if (random.nextBoolean()) {
                    line += ",19865,19865,19865,19865," + timeOne + "," + timeTwo + "," + timeThree + "," + timeFour;
                }
            }

            Member member = Member.fromString(line);
            members.add(member);
            addMemberToTeamList(teamMembers, member);
        }

        //Claus Larsen,11235,12345678,clag@nfjb,20001,0.0,true,true,true,true,false,19865,null,null,null,131012,0,0,0
        //Erik Bølleberg,11315,45678902,erik@bølleberg.dk,18035,0.0,false,true,true,true,true,null,null,null,null,0,0,0,0
        //Birgitte Larsen,7387,23456543,bir@larsen.dk,18401,0.0,true,true,true,false,true,null,null,null,null,0,0,0,0
        //Hans Børge,14421,45674567,hans@boerge.dk,19105,0.0,false,true,false,false,false,null,null,null,null,0,0,0,0

        saveMembers(members);
    }
}
