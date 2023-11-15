import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTree;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

class UserViewPanel extends JPanel implements UserGroupPanelObserver {
    private JTextArea tweetTextArea;
    private JList<User> followingsList;
    private JList<String> newsFeedList;
    private JButton postButton;

    private JTextField userInputField;
    private JButton createUserButton;

    private JTextField groupInputField;
    private JButton createGroupButton;

    private JButton openUserViewButton;

    private JButton showUserTotalButton;
    private JButton showGroupTotalButton;
    private JButton showTweetTotalButton;
    private JButton showPositivePercentageButton;
    
    private JTree userTree;
    private AdminControlPanel adminControlPanel;

    private User currentUser;

    public UserViewPanel(AdminControlPanel adminControlPanel) {
    	this.adminControlPanel = adminControlPanel;
        initComponents();
    }

    private void initComponents() {
        setLayout(new BorderLayout());

        followingsList = new JList<>();
        newsFeedList = new JList<>();

        userInputField = new JTextField(10);
        createUserButton = new JButton("Create User");
        groupInputField = new JTextField(10);
        createGroupButton = new JButton("Create Group");

        openUserViewButton = new JButton("Open User View");

        showUserTotalButton = new JButton("Show User Total");
        showGroupTotalButton = new JButton("Show Group Total");
        showTweetTotalButton = new JButton("Show Tweet Total");
        showPositivePercentageButton = new JButton("Show Positive Percentage");

        // Action listener for button activation
        createUserButton.addActionListener(e -> createUser());
        createGroupButton.addActionListener(e -> createGroup());
        openUserViewButton.addActionListener(e -> openUserView());
        showUserTotalButton.addActionListener(e -> showUserTotal());
        showGroupTotalButton.addActionListener(e -> showGroupTotal());
        showTweetTotalButton.addActionListener(e -> showTweetTotal());
        showPositivePercentageButton.addActionListener(e -> showPositivePercentage());

        // Adding the top components of the interface
        JPanel topPanel = new JPanel(new GridLayout(2, 2));
        topPanel.add(new JLabel("User ID:"));
        topPanel.add(userInputField);
        topPanel.add(createUserButton);
        topPanel.add(new JLabel("Group ID:"));
        topPanel.add(groupInputField);
        topPanel.add(createGroupButton);
        topPanel.add(openUserViewButton);

        // Adding User View Button in the middle
        JPanel midPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        midPanel.add(openUserViewButton, gbc);
        
        // Adding 4 buttons on the bottom
        JPanel bottomPanel = new JPanel(new GridLayout(2, 2));
        bottomPanel.add(showUserTotalButton);
        bottomPanel.add(showGroupTotalButton);
        bottomPanel.add(showTweetTotalButton);
        bottomPanel.add(showPositivePercentageButton);

        add(topPanel, BorderLayout.NORTH);
        add(midPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    private void createUser() {
        String userID = userInputField.getText().trim();
        if (!userID.isEmpty()) {
            adminControlPanel.createUser(userID);
            currentUser = adminControlPanel.getUserByID(userID);
            refreshUI();
        }
    }

    private void createGroup() {
        String groupID = groupInputField.getText().trim();
        if (!groupID.isEmpty()) {
            adminControlPanel.createGroup(groupID);
            refreshUI();
        }
    }

    private void openUserView() {
    	String selectedUserID = userInputField.getText().trim();
        User selectedUser = adminControlPanel.getUserByID(selectedUserID);

        if (selectedUser != null) {
        	// New panel
        	JFrame userViewFrame = new JFrame("User View - " + selectedUserID);
        	JPanel userViewPanel = new JPanel(new GridLayout(4, 1));

        	// User ID and follow button
        	JPanel topPanel = new JPanel(new FlowLayout());
        	JLabel userIDLabel = new JLabel("User ID: " + selectedUser.getUserID());
        	JButton followButton = new JButton("Follow");
        	followButton.addActionListener(e -> followUser(selectedUser));
        	topPanel.add(userIDLabel);
        	topPanel.add(followButton);
        	userViewPanel.add(topPanel);

        	// Current followings
        	JPanel followingsPanel = new JPanel(new BorderLayout());
        	JPanel labelPanel = new JPanel(new FlowLayout());
        	JLabel followLabel = new JLabel("Currently Following");
        	labelPanel.add(followLabel);
        	followingsPanel.add(labelPanel, BorderLayout.NORTH);
        	JList<User> followingsListView = new JList<>();
        	JScrollPane followingsScrollPane = new JScrollPane(followingsListView);
        	followingsPanel.add(followingsScrollPane, BorderLayout.CENTER);
        	userViewPanel.add(followingsPanel);

        	// Tweet input and post button
        	JPanel tweetPanel = new JPanel(new FlowLayout());
        	JTextField tweetInputField = new JTextField(50);
        	JButton postTweetButton = new JButton("Post Tweet");
        	postTweetButton.addActionListener(e -> postTweet(selectedUser, tweetInputField.getText().trim()));
        	tweetPanel.add(tweetInputField);
        	tweetPanel.add(postTweetButton);
        	userViewPanel.add(tweetPanel);

        	// News feed
        	JPanel newsFeedPanel = new JPanel(new BorderLayout());
        	JPanel labelPanelNewsFeed = new JPanel(new FlowLayout());
        	JLabel newsFeedLabel = new JLabel("News Feed");
        	labelPanelNewsFeed.add(newsFeedLabel);
        	newsFeedPanel.add(labelPanelNewsFeed, BorderLayout.NORTH);
        	JList<String> newsFeedListView = new JList<>();
        	JScrollPane newsFeedScrollPane = new JScrollPane(newsFeedListView);
        	newsFeedPanel.add(newsFeedScrollPane, BorderLayout.CENTER);
        	userViewPanel.add(newsFeedPanel);

        	userViewFrame.add(userViewPanel);

        	userViewFrame.setSize(600, 400);
        	userViewFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        	userViewFrame.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "User not found.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void showUserTotal() {
    	List<User> users = adminControlPanel.getUsers();
    	int totalUsers = users.size();
        JOptionPane.showMessageDialog(this, "Total Users: " + totalUsers, "User Total", JOptionPane.INFORMATION_MESSAGE);
    }

    private void showGroupTotal() {
    	List<UserGroup> groups = adminControlPanel.getGroups();
        int totalGroups = groups.size();
        JOptionPane.showMessageDialog(this, "Total Groups: " + totalGroups, "Group Total", JOptionPane.INFORMATION_MESSAGE);
    }

    
    private void showTweetTotal() {
    	List<User> users = adminControlPanel.getUsers();
    	int totalTweets = 0;
        for (User user : users) {
            totalTweets += user.getNewsFeed().size();
        }
        JOptionPane.showMessageDialog(this, "Total Tweets: " + totalTweets, "Tweet Total", JOptionPane.INFORMATION_MESSAGE);
    }

    private void showPositivePercentage() {
        double positivePercentage = 0;
        JOptionPane.showMessageDialog(this, "Positive Percentage: " + positivePercentage + "%", "Positive Percentage", JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void followUser(User userToFollow) {
        refreshUI();
    }

    private void postTweet(User currentUser, String tweet) {
        refreshUI();
    }

    private void refreshUI() {
        followingsList.setListData(currentUser.getFollowings().toArray(new User[0]));
        newsFeedList.setListData(currentUser.getNewsFeed().toArray(new String[0]));
    }

    @Override
    public void userSelected(User user) {
        this.currentUser = user;
        refreshUI();
    }
}