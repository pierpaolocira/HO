package module.pluginFeedback;

import javax.swing.*;
import java.awt.*;

public class FeedbackPanel extends JFrame{

    GridBagLayout layout;
    JLabel lineupRecommendation;
    JTextArea jtaCopyPaste;

    public FeedbackPanel() {
        initComponents();
    }

    private void initComponents() {

//        grid = new GridBagLayout();
        GridBagConstraints gbc = new GridBagConstraints();
//        setLayout(grid);
        setTitle("Feedback Plugin");
        layout = new GridBagLayout();
        this.setLayout(layout);

        // Lineup recommendation =====================================================================
        gbc.fill =  GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0,5,0,5);
        gbc.gridwidth = 5;
        gbc.gridx = 0;
        gbc.gridy = 0;
        lineupRecommendation = new JLabel("<html><font color='red' font-weight='bold'>1.</font> Please set the <b>same lineup both in HO and in HT</b>, as per the following recommendation:</html>");
        this.add(lineupRecommendation, gbc);
        // ==========================================================================================

        // Lineup ======================================================================
        // GK ======================================================================
        gbc.insets = new Insets(15,5,5,5);  //top padding
        gbc.gridwidth = 1;
        gbc.ipadx = 10;
        gbc.weightx = 1;
        gbc.gridx = 2;
        gbc.gridy = 1;
        JLabel GK = new JLabel("GK", JLabel.CENTER);
        GK.setOpaque(true);
        GK.setBackground(Color.WHITE);
        GK.setForeground(Color.BLACK);
        GK.setBorder(BorderFactory.createLineBorder(Color.GREEN, 2));
        this.add(GK, gbc);
        // WBr ======================================================================
        gbc.insets = new Insets(5,5,5,5);  //top padding
        gbc.gridx = 0;
        gbc.gridy = 2;
        JLabel WBr = new JLabel("WB", JLabel.CENTER);
        WBr.setOpaque(true);
        WBr.setBackground(Color.WHITE);
        WBr.setForeground(Color.GRAY);
        WBr.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        this.add(WBr, gbc);
        // CDr ======================================================================
        gbc.gridx = 1;
        gbc.gridy = 2;
        JLabel CDr = new JLabel("CD", JLabel.CENTER);
        CDr.setOpaque(true);
        CDr.setBackground(Color.WHITE);
        CDr.setForeground(Color.GRAY);
        CDr.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        this.add(CDr, gbc);
        // CDc ======================================================================
        gbc.gridx = 2;
        gbc.gridy = 2;
        JLabel CDc = new JLabel("CD", JLabel.CENTER);
        CDc.setOpaque(true);
        CDc.setBackground(Color.WHITE);
        CDc.setForeground(Color.GRAY);
        CDc.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        this.add(CDc, gbc);
        // CDl ======================================================================
        gbc.gridx = 3;
        gbc.gridy = 2;
        JLabel CDl = new JLabel("CD", JLabel.CENTER);
        CDl.setOpaque(true);
        CDl.setBackground(Color.WHITE);
        CDl.setForeground(Color.GRAY);
        CDl.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        this.add(CDl, gbc);
        // WBl ======================================================================
        gbc.gridx = 4;
        gbc.gridy = 2;
        JLabel WBl = new JLabel("WB", JLabel.CENTER);
        WBl.setOpaque(true);
        WBl.setBackground(Color.WHITE);
        WBl.setForeground(Color.GRAY);
        WBl.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        this.add(WBl, gbc);
        // WIr ======================================================================
        gbc.insets = new Insets(5,5,5,5);  //top padding
        gbc.gridx = 0;
        gbc.gridy = 3;
        JLabel WIr = new JLabel("WI", JLabel.CENTER);
        WIr.setOpaque(true);
        WIr.setBackground(Color.WHITE);
        WIr.setForeground(Color.GRAY);
        WIr.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        this.add(WIr, gbc);
        // IMr ======================================================================
        gbc.gridx = 1;
        gbc.gridy = 3;
        JLabel IMr = new JLabel("IM", JLabel.CENTER);
        IMr.setOpaque(true);
        IMr.setBackground(Color.WHITE);
        IMr.setForeground(Color.GRAY);
        IMr.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        this.add(IMr, gbc);
        // IMc ======================================================================
        gbc.gridx = 2;
        gbc.gridy = 3;
        JLabel IMc = new JLabel("IM", JLabel.CENTER);
        IMc.setOpaque(true);
        IMc.setBackground(Color.WHITE);
        IMc.setForeground(Color.GRAY);
        IMc.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        this.add(IMc, gbc);
        // IMl ======================================================================
        gbc.gridx = 3;
        gbc.gridy = 3;
        JLabel IMl = new JLabel("IM", JLabel.CENTER);
        IMl.setOpaque(true);
        IMl.setBackground(Color.WHITE);
        IMl.setForeground(Color.GRAY);
        IMl.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        this.add(IMl, gbc);
        // WIl ======================================================================
        gbc.gridx = 4;
        gbc.gridy = 3;
        JLabel WIl = new JLabel("WI", JLabel.CENTER);
        WIl.setOpaque(true);
        WIl.setBackground(Color.WHITE);
        WIl.setForeground(Color.GRAY);
        WIl.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        this.add(WIl, gbc);
        // FWr ======================================================================
        gbc.gridx = 1;
        gbc.gridy = 4;
        JLabel FWr = new JLabel("FW", JLabel.CENTER);
        FWr.setOpaque(true);
        FWr.setBackground(Color.WHITE);
        FWr.setForeground(Color.GRAY);
        FWr.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        this.add(FWr, gbc);
        // FWc ======================================================================
        gbc.gridx = 2;
        gbc.gridy = 4;
        JLabel FWc = new JLabel("FW", JLabel.CENTER);
        FWc.setOpaque(true);
        FWc.setBackground(Color.WHITE);
        FWc.setForeground(Color.GRAY);
        FWc.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        this.add(FWc, gbc);
        // FWl ======================================================================
        gbc.gridx = 3;
        gbc.gridy = 4;
        JLabel FWl = new JLabel("FW", JLabel.CENTER);
        FWl.setOpaque(true);
        FWl.setBackground(Color.WHITE);
        FWl.setForeground(Color.GRAY);
        FWl.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        this.add(FWl, gbc);
        // ==========================================================================================

        // Copy Paste Area =====================================================================
        gbc.fill =  GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(30,5,0,5);
        gbc.gridwidth = 5;
        gbc.gridx = 0;
        gbc.gridy = 5;
        lineupRecommendation = new JLabel("<html><font color='red' font-weight='bold'>2.</font> Paste the prediction ratings provided by HT</html>");
        this.add(lineupRecommendation, gbc);
        // ==========================================================================================

        // Copy Paste Area  ==========================================================================
        gbc.fill =  GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10,5,0,10);
        gbc.gridwidth = 5;
        gbc.ipadx = 0;
        gbc.ipady = 100;
        gbc.weightx = 0;
        gbc.gridx = 0;
        gbc.gridy = 6;
        jtaCopyPaste = new JTextArea();
        jtaCopyPaste.setFont(new Font("Serif", Font.ITALIC, 10));
        jtaCopyPaste.setLineWrap(true);
        jtaCopyPaste.setWrapStyleWord(true);
        JScrollPane areaScrollPane = new JScrollPane(jtaCopyPaste);
        areaScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
//        areaScrollPane.setSize(new Dimension(380, 100));
        this.add(areaScrollPane, gbc);
        // ==========================================================================================

//
//        gbc.gridx = 1;
//        gbc.gridy = 1;
//        this.add(new Button("Button Four"), gbc);
//        gbc.gridx = 0;
//        gbc.gridy = 2;
//        gbc.fill = GridBagConstraints.HORIZONTAL;
//        gbc.gridwidth = 2;
//        this.add(new Button("Button Five"), gbc);
        setSize(400, 600);
        setPreferredSize(getSize());
        setResizable(false);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);



//
//        lineupRecommendation.setBounds(50, 50, 100, 30);
////        JButton b=new JButton("click");//creating instance of JButton
////        b.setBounds(130,100,100, 40);//x axis, y axis, width, height
//

//        jtaCopyPaste.setBounds(50,200, 200,200);
//
//        f.add(lineupRecommendation);//adding button in JFrame
//        f.add(jtaCopyPaste);//adding button in JFrame
//
//        f.setSize(600,600);
//        f.setLayout(null);//using no layout managers
//        f.setVisible(true);//making the frame visible
//        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

}
