import java.util.ArrayList;
import java.util.List;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

// Observer pattern
interface AdminControlPanelObserver {
	void updateUserInfo(DefaultTreeModel treeModel);
}

// Singleton pattern
class AdminControlPanel {
    private static AdminControlPanel instance;
    private UserGroupPanel userGroupPanel;

    private List<User> users;
    private List<UserGroup> userGroups;
    private DefaultTreeModel treeModel;
    private List<AdminControlPanelObserver> observers = new ArrayList<>();

    public AdminControlPanel() {
        this.users = new ArrayList<>();
        this.userGroups = new ArrayList<>();
        this.treeModel = createTreeModel();
        userGroupPanel = new UserGroupPanel(this);
        addObserver(userGroupPanel);
    }

    public static AdminControlPanel getInstance() {
        if (instance == null) {
            instance = new AdminControlPanel();
        }
        return instance;
    }

    // Create a new user with username
    public void createUser(String userID) {
        User newUser = new User(userID);
        users.add(newUser);
        updateUserTree();
    }

    // Create group based off ID
    public void createGroup(String groupID) {
        UserGroup newGroup = new UserGroup(groupID);
        userGroups.add(newGroup);
        updateUserTree();
    }

    public void addUserToGroup(String userID, String groupID) {
        User user = getUserByID(userID);
        UserGroup group = getUserGroupByID(groupID);

        if (user != null && group != null) {
            group.addUser(user);
            updateUserTree();
        }
    }

    // Tree model with "Root" as default
    private DefaultTreeModel createTreeModel() {
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("Root");

        // Iterate through users to add to the tree
        for (User user : users) {
            DefaultMutableTreeNode userNode = new DefaultMutableTreeNode(user);
            root.add(userNode);
        }

        for (UserGroup group : userGroups) {
            DefaultMutableTreeNode groupNode = new DefaultMutableTreeNode(group);
            root.add(groupNode);
            populateGroupNode(group, groupNode);
        }

        return new DefaultTreeModel(root);
    }

    private void populateGroupNode(UserGroup group, DefaultMutableTreeNode groupNode) {
        for (User user : group.getUsers()) {
            DefaultMutableTreeNode userNode = new DefaultMutableTreeNode(user);
            groupNode.add(userNode);
        }

        for (UserGroup subgroup : group.getSubgroups()) {
            DefaultMutableTreeNode subgroupNode = new DefaultMutableTreeNode(subgroup);
            groupNode.add(subgroupNode);
            populateGroupNode(subgroup, subgroupNode);
        }
    }

    private void updateUserTree() {
        notifyObservers();
    }

    public DefaultTreeModel getTreeModel() {
        return treeModel;
    }

    public List<User> getUsers() {
        return users;
    }

    public User getUserByID(String userID) {
        for (User user : users) {
            if (user.getUserID().equals(userID)) {
                return user;
            }
        }
        return null;
    }
    
    public List<UserGroup> getGroups() {
        return userGroups;
    }

    private UserGroup getUserGroupByID(String groupID) {
        for (UserGroup group : userGroups) {
            if (group.getGroupID().equals(groupID)) {
                return group;
            }
        }
        return null;
    }
    
    public void addObserver(AdminControlPanelObserver observer) {
        observers.add(observer);
    }

    // Notify other classes that have observer
    private void notifyObservers() {
        DefaultTreeModel updatedTreeModel = createTreeModel();
        for (AdminControlPanelObserver observer : observers) {
            observer.updateUserInfo(updatedTreeModel);
        }
    }
}
