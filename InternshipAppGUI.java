package com.internship;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.Serializable;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class InternshipAppGUI {
    private JFrame frame;
    private JTable mainTable;
    private DefaultTableModel mainTableModel;
    private JTable completedTable;
    private DefaultTableModel completedTableModel;
    private JButton createNewButton;
    private JButton saveButton;
    private JButton loadButton;

    private List<PotentialJob> postings;
    private List<PotentialJob> completedPostings;

    public InternshipAppGUI() {
        postings = new ArrayList<>();
        completedPostings = new ArrayList<>();

        frame = new JFrame("Posting List");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        String[] mainColumnNames = {"Job Title", "Company", "Compensation", "Due Date", "Location", "Job Link", "Complete"};
        mainTableModel = new DefaultTableModel(mainColumnNames, 0) {
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex == 5) {
                    return JButton.class;
                } else if (columnIndex == 6) {
                    return Boolean.class;
                }
                return Object.class;
            }
        };

        mainTable = new JTable(mainTableModel);
        mainTable.setAutoCreateRowSorter(true);

        String[] completedColumnNames = {"Job Title", "Company", "Compensation", "Due Date", "Location", "Job Link"};
        completedTableModel = new DefaultTableModel(completedColumnNames, 0) {
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                return columnIndex == 5 ? JButton.class : Object.class;
            }
        };
        completedTable = new JTable(completedTableModel);
        completedTable.setAutoCreateRowSorter(true);

        mainTable.getColumn("Job Link").setCellRenderer((TableCellRenderer) new ButtonRenderer());
        completedTable.getColumn("Job Link").setCellRenderer((TableCellRenderer) new ButtonRenderer());

        mainTable.getColumn("Complete").setCellRenderer(new CheckBoxRenderer());
        mainTable.getColumn("Complete").setCellEditor(new DefaultCellEditor(new JCheckBox()));

        JScrollPane mainScrollPane = new JScrollPane(mainTable);
        JScrollPane completedScrollPane = new JScrollPane(completedTable);

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Active Postings", mainScrollPane);
        tabbedPane.addTab("Completed Postings", completedScrollPane);

        createNewButton = new JButton("Create New Posting");
        saveButton = new JButton("Save");
        loadButton = new JButton("Load");

        createNewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createNewPosting();
            }
        });

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String dataName = JOptionPane.showInputDialog("Enter save name:");
                JPopupMenu update = new JPopupMenu(DataStorage.saveData(postings, completedPostings, dataName));
            }
        });

        loadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String dataName = JOptionPane.showInputDialog("Enter file name:");
                DataStorage.loadInternshipData(dataName, postings, completedPostings);
                refreshTable(mainTableModel, mainTable);
                refreshTable(completedTableModel, completedTable);
                if (postings.size() > 0) {
                    JOptionPane.showMessageDialog(frame, "Posting data loaded successfully.");
                } else {
                    JOptionPane.showMessageDialog(frame, "Failed to load data. Please try again.");
                }
            }
        });

        JPanel inputPanel = new JPanel(new FlowLayout());
        inputPanel.add(createNewButton);
        inputPanel.add(saveButton);
        inputPanel.add(loadButton);

        frame.getContentPane().add(inputPanel, BorderLayout.NORTH);
        frame.getContentPane().add(tabbedPane, BorderLayout.CENTER);

        frame.setVisible(true);
    }

    private void refreshTable(DefaultTableModel tableModel, JTable table) {
        while (tableModel.getRowCount() > 0) {
            tableModel.removeRow(0);
        }

        for (PotentialJob posting : postings) {
            Object[] rowData = {posting.getTitle(), posting.getCompany(), posting.getPay(),
                    posting.getDate(), posting.getLocation(), new JobLinkButton(posting.getLink()), false};
            tableModel.addRow(rowData);
        }
    }

    private void createNewPosting() {
        String jobTitle = JOptionPane.showInputDialog(frame, "Enter Job Title:");
        String company = JOptionPane.showInputDialog(frame, "Enter Company:");
        String compensation = JOptionPane.showInputDialog(frame, "Enter Compensation:");
        String dueDate = JOptionPane.showInputDialog(frame, "Enter Due Date:");
        String location = JOptionPane.showInputDialog(frame, "Enter Location:");
        String jobLink = JOptionPane.showInputDialog(frame, "Enter Job Posting Link:");

        PotentialJob newPosting = new PotentialJob(jobTitle, company, compensation, dueDate, location, jobLink);

        postings.add(newPosting);

        Object[] rowData = {newPosting.getTitle(), newPosting.getCompany(), newPosting.getPay(),
                newPosting.getDate(), newPosting.getLocation(), new JobLinkButton(newPosting.getLink()), false};
        mainTableModel.addRow(rowData);
    }

    private static class JobLinkButton extends JButton implements Serializable {
        private static final long serialVersionUID = 1L;
        private String link;

        public JobLinkButton(String link) {
            super("Job Link");
            final String url = link;
            addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    openLinkInBrowser(url);
                }
            });
        }

        private void openLinkInBrowser(String link) {
            try {
                Desktop.getDesktop().browse(new URI(link));
            } catch (IOException | URISyntaxException ex) {
                ex.printStackTrace();
            }
        }
    }

    private static class ButtonRenderer extends JButton implements TableCellRenderer {
        public ButtonRenderer() {
            setOpaque(true);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            if (isSelected) {
                setForeground(table.getSelectionForeground());
                setBackground(table.getSelectionBackground());
            } else {
                setForeground(table.getForeground());
                setBackground(UIManager.getColor("Button.background"));
            }

            setText((value == null) ? "" : value.toString());
            return this;
        }
    }

    private static class CheckBoxRenderer extends JCheckBox implements TableCellRenderer {
        public CheckBoxRenderer() {
            setHorizontalAlignment(JLabel.CENTER);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            setSelected((value != null && (boolean) value));
            return this;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new InternshipAppGUI();
            }
        });
    }
}
