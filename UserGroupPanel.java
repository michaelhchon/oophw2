import java.awt.BorderLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

class UserGroupPanel extends JPanel implements AdminControlPanelObserver {
    private JTree userTree;
    private AdminControlPanel adminControlPanel;
    private UserGroupPanelObserver observer;

    public UserGroupPanel(AdminControlPanel adminControlPanel) {
        this.adminControlPanel = adminControlPanel;
        initComponents();
    }

    private void initComponents() {
        setLayout(new BorderLayout());
        JLabel treeViewLabel = new JLabel("Tree View");
        add(treeViewLabel, BorderLayout.NORTH);
        
        // Adding tree to the panel
        userTree = new JTree(adminControlPanel.getTreeModel());
        userTree.addTreeSelectionListener(e -> {
            DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) userTree.getLastSelectedPathComponent();
            if (selectedNode != null && selectedNode.getUserObject() instanceof User) {
                User selectedUser = (User) selectedNode.getUserObject();
                observer.userSelected(selectedUser);
            }
        });

        JScrollPane treeScrollPane = new JScrollPane(userTree);
        add(treeScrollPane, BorderLayout.CENTER);
    }
    
    public void refreshTreeModel() {
        userTree.setModel(adminControlPanel.getTreeModel());
    }

    public void setObserver(UserGroupPanelObserver observer) {
        this.observer = observer;
    }

	@Override
	public void updateUserInfo(DefaultTreeModel treeModel) {
		userTree.setModel(treeModel);
	}
}