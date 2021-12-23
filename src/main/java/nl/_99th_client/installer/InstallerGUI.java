package nl._99th_client.installer;

import nl._99th_client.Config;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;

public class InstallerGUI extends JFrame {
    private JLabel ivjLabelClientVersion = null;

    private JLabel ivjLabelMcVersion = null;

    private JPanel ivjPanelCenter = null;

    private JButton ivjButtonInstall = null;

    private JButton ivjButtonClose = null;

    private JPanel ivjPanelBottom = null;

    private JPanel ivjPanelContentPane = null;

    IvjEventHandler ivjEventHandler = new IvjEventHandler();

    private JTextArea ivjTextArea = null;

    private JLabel ivjLabelFolder = null;

    private JTextField ivjFieldFolder = null;

    private JButton ivjButtonFolder = null;

    class IvjEventHandler implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == InstallerGUI.this.getButtonClose())
                InstallerGUI.this.connEtoC2(e);
            if (e.getSource() == InstallerGUI.this.getButtonFolder())
                InstallerGUI.this.connEtoC4(e);
            if (e.getSource() == InstallerGUI.this.getButtonInstall())
                InstallerGUI.this.connEtoC1(e);
        }
    }

    public InstallerGUI() {
        initialize();
    }

    private void customInit() {
        try {
            pack();
            setDefaultCloseOperation(3);
            File dirMc = Utils.getWorkingDirectory();
            getFieldFolder().setText(dirMc.getPath());
            getButtonInstall().setEnabled(false);
            String clientVer = Installer.getClientVersion();
            String clientMCVer = Installer.getClientMCVersion();
            Utils.dbg(Config.clientName + " Version: " + clientVer);
            Utils.dbg("Minecraft Version: " + clientMCVer);
            getLabelClientVersion().setText(Config.clientName + "-" + clientVer);
            getLabelMcVersion().setText("for Minecraft " + clientMCVer);
            getButtonInstall().setEnabled(true);
            getButtonInstall().requestFocus();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handleException(Throwable e) {
        String msg = e.getMessage();
        if (msg != null && msg.equals("QUIET"))
            return;
        e.printStackTrace();
        String str = Utils.getExceptionStackTrace(e);
        str = str.replace("\t", "  ");
        JTextArea textArea = new JTextArea(str);
        textArea.setEditable(false);
        Font f = textArea.getFont();
        Font f2 = new Font("Monospaced", f.getStyle(), f.getSize());
        textArea.setFont(f2);
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(600, 400));
        JOptionPane.showMessageDialog(null, scrollPane, "Error", 0);
    }

    private JLabel getLabelClientVersion() {
        if (this.ivjLabelClientVersion == null)
            try {
                this.ivjLabelClientVersion = new JLabel();
                this.ivjLabelClientVersion.setName("LabelClientVersion");
                this.ivjLabelClientVersion.setBounds(2, 5, 585, 42);
                this.ivjLabelClientVersion.setFont(new Font("Dialog", 1, 18));
                this.ivjLabelClientVersion.setHorizontalAlignment(0);
                this.ivjLabelClientVersion.setPreferredSize(new Dimension(585, 42));
                this.ivjLabelClientVersion.setText(Config.clientName + " ...");
            } catch (Throwable ivjExc) {
                handleException(ivjExc);
            }
        return this.ivjLabelClientVersion;
    }

    private JLabel getLabelMcVersion() {
        if (this.ivjLabelMcVersion == null)
            try {
                this.ivjLabelMcVersion = new JLabel();
                this.ivjLabelMcVersion.setName("LabelMcVersion");
                this.ivjLabelMcVersion.setBounds(2, 38, 585, 25);
                this.ivjLabelMcVersion.setFont(new Font("Dialog", 1, 14));
                this.ivjLabelMcVersion.setHorizontalAlignment(0);
                this.ivjLabelMcVersion.setPreferredSize(new Dimension(585, 25));
                this.ivjLabelMcVersion.setText("for Minecraft ...");
            } catch (Throwable ivjExc) {
                handleException(ivjExc);
            }
        return this.ivjLabelMcVersion;
    }

    private JPanel getPanelCenter() {
        if (this.ivjPanelCenter == null)
            try {
                this.ivjPanelCenter = new JPanel();
                this.ivjPanelCenter.setName("PanelCenter");
                this.ivjPanelCenter.setLayout((LayoutManager)null);
                this.ivjPanelCenter.add(getLabelClientVersion(), getLabelClientVersion().getName());
                this.ivjPanelCenter.add(getLabelMcVersion(), getLabelMcVersion().getName());
                this.ivjPanelCenter.add(getTextArea(), getTextArea().getName());
                this.ivjPanelCenter.add(getLabelFolder(), getLabelFolder().getName());
                this.ivjPanelCenter.add(getFieldFolder(), getFieldFolder().getName());
                this.ivjPanelCenter.add(getButtonFolder(), getButtonFolder().getName());
            } catch (Throwable ivjExc) {
                handleException(ivjExc);
            }
        return this.ivjPanelCenter;
    }

    private JButton getButtonInstall() {
        if (this.ivjButtonInstall == null)
            try {
                this.ivjButtonInstall = new JButton();
                this.ivjButtonInstall.setName("ButtonInstall");
                this.ivjButtonInstall.setPreferredSize(new Dimension(100, 26));
                this.ivjButtonInstall.setText("Install");
            } catch (Throwable ivjExc) {
                handleException(ivjExc);
            }
        return this.ivjButtonInstall;
    }

    private JButton getButtonClose() {
        if (this.ivjButtonClose == null)
            try {
                this.ivjButtonClose = new JButton();
                this.ivjButtonClose.setName("ButtonClose");
                this.ivjButtonClose.setPreferredSize(new Dimension(100, 26));
                this.ivjButtonClose.setText("Cancel");
            } catch (Throwable ivjExc) {
                handleException(ivjExc);
            }
        return this.ivjButtonClose;
    }

    private JPanel getPanelBottom() {
        if (this.ivjPanelBottom == null)
            try {
                this.ivjPanelBottom = new JPanel();
                this.ivjPanelBottom.setName("PanelBottom");
                this.ivjPanelBottom.setLayout(new FlowLayout(1, 15, 10));
                this.ivjPanelBottom.setPreferredSize(new Dimension(586, 55));
                this.ivjPanelBottom.add(getButtonInstall(), getButtonInstall().getName());
                this.ivjPanelBottom.add(getButtonClose(), getButtonClose().getName());
            } catch (Throwable ivjExc) {
                handleException(ivjExc);
            }
        return this.ivjPanelBottom;
    }

    private JPanel getPanelContentPane() {
        if (this.ivjPanelContentPane == null)
            try {
                this.ivjPanelContentPane = new JPanel();
                this.ivjPanelContentPane.setName("PanelContentPane");
                this.ivjPanelContentPane.setLayout(new BorderLayout(5, 5));
                this.ivjPanelContentPane.setPreferredSize(new Dimension(590, 367));
                this.ivjPanelContentPane.add(getPanelCenter(), "Center");
                this.ivjPanelContentPane.add(getPanelBottom(), "South");
            } catch (Throwable ivjExc) {
                handleException(ivjExc);
            }
        return this.ivjPanelContentPane;
    }

    private void initialize() {
        try {
            setName("InstallerFrame");
            setSize(600, 350);
            setDefaultCloseOperation(0);
            setResizable(false);
            setTitle(Config.clientName + " Installer");
            setContentPane(getPanelContentPane());
            initConnections();
        } catch (Throwable ivjExc) {
            handleException(ivjExc);
        }
        customInit();
    }

    public void onInstall() {
        this.ivjButtonInstall.setText("Installing...");
        this.ivjButtonInstall.setEnabled(false);
        this.ivjButtonClose.setEnabled(false);
        this.ivjFieldFolder.setEnabled(false);
        this.ivjButtonFolder.setEnabled(false);

        Thread thread = new Thread(() -> {
            try {
                File dirMc = new File(getFieldFolder().getText());
                if (!dirMc.exists()) {
                    Utils.showErrorMessage("Folder not found: " + dirMc.getPath());
                    return;
                }
                if (!dirMc.isDirectory()) {
                    Utils.showErrorMessage("Not a folder: " + dirMc.getPath());
                    return;
                }
                Installer.doInstall(dirMc);
                Utils.showMessage(Config.clientName + " is successfully installed.");
                dispose();
            } catch (Exception e) {
                handleException(e);
            } finally {
                this.ivjButtonInstall.setText("Install");
                this.ivjButtonInstall.setEnabled(true);
                this.ivjButtonClose.setEnabled(true);
                this.ivjFieldFolder.setEnabled(true);
                this.ivjButtonFolder.setEnabled(true);
            }
        });

        thread.start();
    }

    public void onClose() {
        dispose();
    }

    private void connEtoC1(ActionEvent arg1) {
        try {
            onInstall();
        } catch (Throwable ivjExc) {
            handleException(ivjExc);
        }
    }

    private void connEtoC2(ActionEvent arg1) {
        try {
            onClose();
        } catch (Throwable ivjExc) {
            handleException(ivjExc);
        }
    }

    private void initConnections() throws Exception {
        getButtonFolder().addActionListener(this.ivjEventHandler);
        getButtonInstall().addActionListener(this.ivjEventHandler);
        getButtonClose().addActionListener(this.ivjEventHandler);
    }

    private JTextArea getTextArea() {
        if (this.ivjTextArea == null)
            try {
                this.ivjTextArea = new JTextArea();
                this.ivjTextArea.setName("TextArea");
                this.ivjTextArea.setBounds(65, 96, 465, 44);
                this.ivjTextArea.setEditable(false);
                this.ivjTextArea.setEnabled(true);
                this.ivjTextArea.setFont(new Font("Dialog", 0, 12));
                this.ivjTextArea.setLineWrap(true);
                this.ivjTextArea.setOpaque(false);
                this.ivjTextArea.setPreferredSize(new Dimension(465, 44));
                this.ivjTextArea.setText("This installer will install the " + Config.clientName + " in the official Minecraft launcher and will create a new profile \"" + Config.clientName + "\" for it.");
                this.ivjTextArea.setWrapStyleWord(true);
            } catch (Throwable ivjExc) {
                handleException(ivjExc);
            }
        return this.ivjTextArea;
    }

    private JLabel getLabelFolder() {
        if (this.ivjLabelFolder == null)
            try {
                this.ivjLabelFolder = new JLabel();
                this.ivjLabelFolder.setName("LabelFolder");
                this.ivjLabelFolder.setBounds(115, 166, 47, 16);
                this.ivjLabelFolder.setPreferredSize(new Dimension(47, 16));
                this.ivjLabelFolder.setText("Folder");
            } catch (Throwable ivjExc) {
                handleException(ivjExc);
            }
        return this.ivjLabelFolder;
    }

    private JTextField getFieldFolder() {
        if (this.ivjFieldFolder == null)
            try {
                this.ivjFieldFolder = new JTextField();
                this.ivjFieldFolder.setName("FieldFolder");
                this.ivjFieldFolder.setBounds(162, 164, 287, 20);
                this.ivjFieldFolder.setEditable(false);
                this.ivjFieldFolder.setPreferredSize(new Dimension(287, 20));
            } catch (Throwable ivjExc) {
                handleException(ivjExc);
            }
        return this.ivjFieldFolder;
    }

    private JButton getButtonFolder() {
        if (this.ivjButtonFolder == null)
            try {
                this.ivjButtonFolder = new JButton();
                this.ivjButtonFolder.setName("ButtonFolder");
                this.ivjButtonFolder.setBounds(450, 164, 25, 20);
                this.ivjButtonFolder.setMargin(new Insets(2, 2, 2, 2));
                this.ivjButtonFolder.setPreferredSize(new Dimension(25, 20));
                this.ivjButtonFolder.setText("...");
            } catch (Throwable ivjExc) {
                handleException(ivjExc);
            }
        return this.ivjButtonFolder;
    }

    public void onFolderSelect() {
        File dirMc = new File(getFieldFolder().getText());
        JFileChooser jfc = new JFileChooser(dirMc);
        jfc.setFileSelectionMode(1);
        jfc.setAcceptAllFileFilterUsed(false);
        if (jfc.showOpenDialog(this) == 0) {
            File dir = jfc.getSelectedFile();
            getFieldFolder().setText(dir.getPath());
        }
    }

    private void connEtoC4(ActionEvent arg1) {
        try {
            onFolderSelect();
        } catch (Throwable ivjExc) {
            handleException(ivjExc);
        }
    }
}

