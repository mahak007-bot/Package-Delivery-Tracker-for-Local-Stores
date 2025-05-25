import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class PackageDeliveryTrackerGUI extends JFrame {
    private DeliveryTracker tracker;

    public PackageDeliveryTrackerGUI() {
        tracker = new DeliveryTracker();

        setTitle("Package Delivery Tracker for Local Stores");
        setSize(600, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Main Buttons Panel
        JPanel panel = new JPanel(new GridLayout(5, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(30, 100, 30, 100));

        JButton addBtn = new JButton("Add Package");
        JButton updateBtn = new JButton("Update Package Status");
        JButton trackBtn = new JButton("Track Package");
        JButton viewBtn = new JButton("View All Packages");
        JButton exitBtn = new JButton("Exit");

        panel.add(addBtn);
        panel.add(updateBtn);
        panel.add(trackBtn);
        panel.add(viewBtn);
        panel.add(exitBtn);

        add(panel);

        // Button actions
        addBtn.addActionListener(e -> showAddDialog());
        updateBtn.addActionListener(e -> showUpdateDialog());
        trackBtn.addActionListener(e -> showTrackDialog());
        viewBtn.addActionListener(e -> showAllPackages());
        exitBtn.addActionListener(e -> System.exit(0));
    }

    // --------------------- Dialogs ------------------------

    private void showAddDialog() {
        JTextField sender = new JTextField();
        JTextField recipient = new JTextField();
        JTextField address = new JTextField();

        Object[] fields = {
                "Sender Name:", sender,
                "Recipient Name:", recipient,
                "Address:", address
        };

        int result = JOptionPane.showConfirmDialog(this, fields, "Add Package", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            if (sender.getText().isEmpty() || recipient.getText().isEmpty() || address.getText().isEmpty()) {
                showError("All fields are required.");
                return;
            }
            tracker.addPackage(sender.getText(), recipient.getText(), address.getText());
        }
    }

    private void showUpdateDialog() {
        JTextField idField = new JTextField();
        String[] statuses = {"Pending", "Dispatched", "In Transit", "Delivered"};
        JComboBox<String> statusBox = new JComboBox<>(statuses);

        Object[] fields = {
                "Package ID:", idField,
                "New Status:", statusBox
        };

        int result = JOptionPane.showConfirmDialog(this, fields, "Update Status", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            try {
                int id = Integer.parseInt(idField.getText().trim());
                String status = (String) statusBox.getSelectedItem();
                tracker.updateStatus(id, status);
            } catch (NumberFormatException e) {
                showError("Package ID must be a number.");
            }
        }
    }

    private void showTrackDialog() {
        String input = JOptionPane.showInputDialog(this, "Enter Package ID:");
        if (input != null) {
            try {
                int id = Integer.parseInt(input.trim());
                Package p = tracker.getPackageById(id);
                if (p != null) {
                    showInfo(p.getDetails(), "Package Details");
                } else {
                    showError("Package ID not found.");
                }
            } catch (NumberFormatException e) {
                showError("Invalid Package ID.");
            }
        }
    }

    private void showAllPackages() {
        List<Package> packages = tracker.getAllPackages();
        if (packages.isEmpty()) {
            showInfo("No packages available.");
        } else {
            StringBuilder sb = new StringBuilder();
            for (Package p : packages) {
                sb.append(p.getDetails()).append("\n");
            }
            JTextArea textArea = new JTextArea(sb.toString());
            textArea.setEditable(false);
            JScrollPane scrollPane = new JScrollPane(textArea);
            scrollPane.setPreferredSize(new Dimension(500, 300));
            JOptionPane.showMessageDialog(this, scrollPane, "All Packages", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    // ------------------- Utility Methods -----------------------

    private void showError(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Error", JOptionPane.ERROR_MESSAGE);
    }

    private void showInfo(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Info", JOptionPane.INFORMATION_MESSAGE);
    }

    private void showInfo(String msg, String title) {
        JOptionPane.showMessageDialog(this, msg, title, JOptionPane.INFORMATION_MESSAGE);
    }

    // ----------------- Entry Point ----------------------------

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new PackageDeliveryTrackerGUI().setVisible(true));
    }

    // ----------------- Core Classes ---------------------------

    static class Package {
        private static int idCounter = 1000;
        private final int packageId;
        private final String sender;
        private final String recipient;
        private final String address;
        private String status;

        public Package(String sender, String recipient, String address) {
            this.packageId = idCounter++;
            this.sender = sender;
            this.recipient = recipient;
            this.address = address;
            this.status = "Pending";
        }

        public int getPackageId() {
            return packageId;
        }

        public void updateStatus(String newStatus) {
            this.status = newStatus;
        }

        public String getDetails() {
            return "Package ID: " + packageId + "\n" +
                    "Sender: " + sender + "\n" +
                    "Recipient: " + recipient + "\n" +
                    "Address: " + address + "\n" +
                    "Status: " + status + "\n" +
                    "---------------------------";
        }
    }

    static class DeliveryTracker {
        private final List<Package> packageList = new ArrayList<>();

        public void addPackage(String sender, String recipient, String address) {
            Package p = new Package(sender, recipient, address);
            packageList.add(p);
            JOptionPane.showMessageDialog(null, "Package added with ID: " + p.getPackageId());
        }

        public void updateStatus(int id, String newStatus) {
            for (Package p : packageList) {
                if (p.getPackageId() == id) {
                    p.updateStatus(newStatus);
                    JOptionPane.showMessageDialog(null, "Status updated.");
                    return;
                }
            }
            JOptionPane.showMessageDialog(null, "Package ID not found.");
        }

        public Package getPackageById(int id) {
            for (Package p : packageList) {
                if (p.getPackageId() == id) {
                    return p;
                }
            }
            return null;
        }

        public List<Package> getAllPackages() {
            return packageList;
        }
    }
}