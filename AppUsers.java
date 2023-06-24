import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class AppUsers {
    // public static List<VillageUser> userList = new ArrayList<>();
    public static List<VillageUser> userList = deserializeUsers();

    // Serialization method to store user object
    public static void serializeUsers(VillageUser user) {
        AppConstants.println("Serializing User Account...", "blue");
        printAllUsers();

        // add the user to the list
        userList.add(user);

        try {
            // String fileName = this.name.toLowerCase() + ".ser";
            FileOutputStream fileOut = new FileOutputStream(AppConstants.USER_FILE_NAME);
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(userList);
            objectOut.close();
            fileOut.close();
            // AppConstants.println("User serialized successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }

        AppConstants.println("\nNew User Account...", "blue");
        printAllUsers();
    }

    // Deserialization method to obtain user object
    // Deserialization method to obtain user object
    public static List<VillageUser> deserializeUsers() {

        try {
            FileInputStream fileIn = new FileInputStream(AppConstants.USER_FILE_NAME);
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);
            List<VillageUser> userList = (List<VillageUser>) objectIn.readObject();
            objectIn.close();
            fileIn.close();
            return userList;
        } catch (IOException | ClassNotFoundException e) {
            // e.printStackTrace();
            AppConstants.println("Error: User Account File doesn't exist!", "red");
            return new ArrayList<>();
        }
    }

    // get the current user from the list of users
    public static VillageUser getCurrentUser(String name, String password) {
        List<VillageUser> userList = deserializeUsers();

        // check if list is empty
        if (userList == null || userList.size() == 0) {
            return null;
        } else {
            // current user is the one whose name and password matches
            VillageUser currentUser = null;
            for (VillageUser user : userList) {
                if (user.name.equals(name) && user.password.equals(password)) {
                    currentUser = user;
                }
            }

            //
            return currentUser;
        }
    }

    // print all users
    public static void printAllUsers() {
        List<VillageUser> userList = deserializeUsers();

        // check if list is empty
        if (userList == null || userList.size() == 0) {
            AppConstants.println("No users found!", "red");
        } else {
            // print all users
            for (VillageUser user : userList) {
                AppConstants.println(user.toString(), "blue");
            }
        }
    }

}
