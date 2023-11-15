import java.util.ArrayList;
import java.util.List;

// Composite pattern
class UserGroup {
    private String groupID;
    private List<User> users;
    private List<UserGroup> subgroups;

    public UserGroup(String groupID) {
        this.groupID = groupID;
        this.users = new ArrayList<>();
        this.subgroups = new ArrayList<>();
    }

    public void addUser(User user) {
        users.add(user);
    }

    public List<User> getUsers() {
        return users;
    }

    public void addSubgroup(UserGroup subgroup) {
        subgroups.add(subgroup);
    }

    public List<UserGroup> getSubgroups() {
        return subgroups;
    }

    public String getGroupID() {
        return groupID;
    }

    @Override
    public String toString() {
        return groupID;
    }
}
