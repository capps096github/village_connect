import java.util.*;

public class CommunityGroups {

        public static List<VillageGroup> villageGroups = defaultGroups();

        // random groups
        public static List<VillageGroup> defaultGroups() {
                // empty grop list
                List<VillageGroup> groups = new ArrayList<>();

                VillageGroup group1 = new VillageGroup("Kisaru Village",
                                "Kisaru Village is a picturesque community nestled amidst lush greenery and rolling hills. Known for its serene ambiance and close-knit community, this village prides itself on preserving its natural surroundings. Residents of Willowbrook Village actively participate in eco-friendly initiatives, maintaining a harmonious balance between modern living and environmental conservation. The village offers a range of recreational activities, including hiking trails, community gardens, and regular cultural events that celebrate the local arts and traditions.",
                                "cephas");

                VillageGroup group2 = new VillageGroup("Kasenyi Grove",
                                "A group for organizing regular meetups and events within the village community.",
                                "cephas");

                group2.messages.add("Hello, villagers! Let's plan our next meetup.");
                group2.messages.add("Share your ideas for activities and suggest a date.");

                VillageGroup group3 = new VillageGroup("Village Social Club",
                                "A club dedicated to fostering social connections and organizing recreational activities in the village.",
                                "cephas");

                group3.messages.add("Welcome to the Village Social Club!");
                group3.messages.add(
                                "Let's plan some fun activities like game nights, movie screenings, and potluck dinners.");

                // create groups from 3 and 4
                VillageGroup group4 = new VillageGroup("Harmony Haven",
                                "Harmony Haven is a close-knit village renowned for its emphasis on well-being and harmonious living. The village offers a range of holistic health and wellness practices, including yoga retreats, meditation sessions, and organic food markets. Residents of Harmony Haven actively promote a healthy and balanced lifestyle, fostering a sense of unity and mindfulness. The village prides itself on its supportive community, where neighbors often come together to organize community projects, cultural events, and volunteer initiatives, creating a haven of harmony and positivity.",
                                "cephas");

                group4.messages.add("Welcome to Harmony Haven!");
                group4.messages.add(
                                "Let's plan some fun activities like yoga retreats, meditation sessions, and organic food markets.");

                VillageGroup group5 = new VillageGroup("Oakwood Village",
                                "Nestled amidst majestic oak trees, Oakwood Village is a charming community known for its rich history and natural beauty. The village boasts picturesque landscapes, with sprawling oak forests that provide a tranquil sanctuary for residents and visitors alike. Oakwood Village takes pride in its heritage, preserving historical landmarks, and organizing cultural festivals that showcase traditional arts, crafts, and local folklore. The village encourages outdoor activities such as hiking, birdwatching, and nature trails, allowing residents to connect with nature and enjoy the serenity of their surroundings.",
                                "cephas");

                groups.add(group1);
                groups.add(group2);
                groups.add(group3);
                groups.add(group4);
                groups.add(group5);

                return groups;
        }

        // print groups
        public static void printGroups(List<VillageGroup> villageGroups) {
                for (int i = 0; i < villageGroups.size(); i++) {
                        VillageGroup group = villageGroups.get(i);
                        AppConstants.println((i + 1) + ". " + group.name);
                }
        }

        // add new group
        public static void addGroup() {
                Scanner scanner = new Scanner(System.in);

                // Create a Group
                AppConstants.print("\nWhat is the name of the new group? e.g Chairmanz Group:  ");
                String groupName = scanner.nextLine();

                AppConstants.print(
                                "\nWhat is the description of the new group? e.g Chairman's group:  ");
                String groupDescription = scanner.nextLine();

                AppConstants.print("\nWho is the admin of the new group? e.g Admin1:  ");
                String groupAdmin = scanner.nextLine();

                VillageGroup newGroup = new VillageGroup(groupName, groupDescription, groupAdmin);

                // show new group menu
                try {
                        // serialize the new group
                        // serializeThisGroup(newGroup);
                        AppConstants.println("\nNew group created: " + newGroup.toString() + "\n", "green");

                        newGroup.showMenu();
                } catch (Exception e) {
                        e.printStackTrace();
                }

                // close scanner and exit
                scanner.close();

                // exit
                System.exit(0);
        }

        // select a group
        public static void selectGroup() throws Exception {
                Scanner scanner = new Scanner(System.in);

                // Print the list of groups
                AppConstants.println("\n\nVillage Connect Available Groups:\n", "white");

                printGroups(villageGroups);

                // go back to main menu
                AppConstants.print("0. Back to Main Menu\n", "white");

                // Read the user's choice
                AppConstants.print("\nSelect a group you want to Join (1 -" + villageGroups.size() + "):  ", "white");

                int groupChoice = scanner.nextInt();

                if (groupChoice == 0) {
                        // Select the group
                        // VillageGroup selectedGroup = villageGroups.get(groupChoice - 1);
                        // // ! Update the group
                        // CommunityGroups.updateGroup(selectedGroup);

                        // go back to main menu
                        AppConstants.println("\nGoing back to Main Menu...", "blue");
                        AppLogic.mainMenu();
                } else if (groupChoice >= 1 && groupChoice <= villageGroups.size()) {
                        // Select the group
                        VillageGroup selectedGroup = villageGroups.get(groupChoice - 1);

                        VillageUser existingUser = AppConstants.currentUser;

                        // Add the user to the group
                        selectedGroup.addUser(existingUser.name);

                        // ! Update the group
                        CommunityGroups.updateGroup(selectedGroup);

                        // Show the group menu
                        selectedGroup.showMenu();

                } else {
                        AppConstants.printError("Invalid choice!");
                        selectGroup();
                }

        }

        public static void updateGroup(VillageGroup villageGroup) {
                // get the index of the group
                int index = villageGroups.indexOf(villageGroup);

                // update the group
                villageGroups.set(index, villageGroup);

        }

}
