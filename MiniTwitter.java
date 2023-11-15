import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JSplitPane;
import javax.swing.SwingUtilities;

// Observer pattern
interface UserViewObserver {
    void updateNewsFeed(String message);
}
// Observer pattern
interface UserGroupPanelObserver {
	void userSelected(User user);
}
// Visitor pattern
interface UserVisitor {
	void visit(User user);
}

public class MiniTwitter extends JFrame {
    private AdminControlPanel adminControlPanel;
    private UserGroupPanel userGroupPanel;
    private UserViewPanel userViewPanel;

    public MiniTwitter() {
        this.adminControlPanel = AdminControlPanel.getInstance();
        this.userGroupPanel = new UserGroupPanel(adminControlPanel);
        this.userViewPanel = new UserViewPanel(adminControlPanel);

        initComponents();
    }

    private void initComponents() {
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        setTitle("Mini Twitter Admin Panel");
        // Split the GUI into two sides, one for the tree one for the interface
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, userGroupPanel, userViewPanel);
        splitPane.setResizeWeight(0.5);

        add(splitPane, BorderLayout.CENTER);

        userGroupPanel.setObserver(userViewPanel);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MiniTwitter());
    }
}