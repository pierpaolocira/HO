package module.pluginFeedback;

import com.google.gson.Gson;
import core.model.HOVerwaltung;
import core.model.Ratings;
import core.model.player.IMatchRoleID;
import core.model.player.MatchRoleID;
import module.lineup.Lineup;
import module.lineup.RatingComparisonPanel;
import module.teamAnalyzer.vo.MatchRating;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


public class FeedbackPanel extends JFrame{

    GridBagLayout layout;
    JLabel lineupRecommendation;
    JLabel GK, WBr;
    JTextArea jtaCopyPaste;
    JButton jbRefresh, jbSend;
    Map<Integer, Byte> requiredLineup = new HashMap<>();
    Lineup HOLineup;
    Ratings HORatings;
    MatchRating HTRatings;
    Boolean bFetchLineupSuccess;
    Boolean isHOLineupValid = false;
    RatingComparisonPanel HOPredictionRating, HTPredictionRating, DeltaPredictionRating;

    public FeedbackPanel() {
        HORatings = HOVerwaltung.instance().getModel().getLineup().getRatings();
        HOLineup = HOVerwaltung.instance().getModel().getLineup();

        bFetchLineupSuccess = fetchRequiredLineup();
        if (bFetchLineupSuccess) isHOLineupValid = checkHOLineup();
        initComponents();
        refresh();
    }


    private boolean checkHOLineup(){
        int positionHO, orderHO;
        boolean isAligned, positionIsRequired;

        // return false if HOLineup not fully included in required Lineup
        for (IMatchRoleID obj: HOLineup.getPositionen()) {
            positionHO = ((MatchRoleID) obj).getId();
            orderHO = ((MatchRoleID) obj).getTaktik();
            isAligned = (((MatchRoleID) obj).getSpielerId() != 0 ) && IMatchRoleID.aFieldMatchRoleID.contains(positionHO);


            if (isAligned)
            {
                positionIsRequired = requiredLineup.containsKey(positionHO);
                if(!positionIsRequired) return false; // Player in the lineup at a position not listed in the requirements
                if(requiredLineup.get(positionHO) != orderHO) return false; // Player has incorrect orders
            }
            }

        // return false if required Lineup not fully included in HO Lineup
        for (Map.Entry<Integer, Byte> entry : requiredLineup.entrySet()) {
            HOLineup.getPositionById(entry.getKey()).getTaktik();
            if( (HOLineup.getPositionById(entry.getKey()).getTaktik() != entry.getValue()) ) return false;
        }

        return true;
    }


    private void sendToServer() { // TODO: to be implemented

    }


    private boolean fetchRequiredLineup() {

            try {
                URL url = new URL("https://akasolace.github.io/HO/feedback.json");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.connect();
//                BufferedReader json = new BufferedReader(new java.io.InputStreamReader(connection.getInputStream()));
                BufferedReader json = new BufferedReader(new FileReader("D:\\Temp\\feedback.json"));
                SimpleLineup temp =  new Gson().fromJson(json, SimpleLineup.class);
                requiredLineup = temp.lineup;
                return true;
            }

            catch (Exception e)
        {
            String message = HOVerwaltung.instance().getLanguageString("feedbackplugin.fetcRequiredLineupError");
            JOptionPane.showMessageDialog(null, message, "", JOptionPane.ERROR_MESSAGE);
            return false;
        }

    }


    private void formatPlayerBox(JLabel jl, String pos, Byte order) // TODO treat the order, make it visible somehow in the box
    {
        if(order != null) {
            String s_order = pos;
            jl.setBackground(Color.WHITE);
            jl.setForeground(Color.BLACK);
            jl.setBorder(BorderFactory.createLineBorder(Color.GREEN, 2));

            java.util.List<String> righSide = Arrays.asList("WBr", "CDr", "WIr", "IMr", "FWr");

            String right_arrow = "\uD83E\uDC7A";
            String left_arrow = "\uD83E\uDC78";
            String up_arrow = "\uD83E\uDC79";
            String down_arrow = "\uD83E\uDC7B";

            switch(order) {
                case IMatchRoleID.NORMAL:
                    break;
                case IMatchRoleID.OFFENSIVE:
                    s_order += " (OFF) " + down_arrow;
                    break;
                case IMatchRoleID.DEFENSIVE:
                    s_order += " (DEF) " + up_arrow;
                    break;
                case IMatchRoleID.TOWARDS_WING:
                    if (righSide.contains(pos)){s_order = left_arrow + " " + s_order + " (TW)";}
                    else {s_order = s_order + " (TW) " + right_arrow;}
                    break;
                case IMatchRoleID.TOWARDS_MIDDLE:
                    if (! righSide.contains(pos)){s_order = left_arrow + " " + s_order + " (TM)";}
                    else {s_order = s_order + " (TW) " + right_arrow;}
                    break;
            }
            jl.setText(s_order);
        }

    }


    private void formatSendButton()
    {
        if (isHOLineupValid) {
            jbSend.setEnabled(true);
            jbSend.setToolTipText(HOVerwaltung.instance().getLanguageString("feedbackplugin.jbSendActivated"));
        }
        else {
            jbSend.setEnabled(false);
            jbSend.setToolTipText(HOVerwaltung.instance().getLanguageString("feedbackplugin.jbSendDeactivated"));
        }
    }

    private void refreshRatingComparisonPanel()
    {
        HOPredictionRating.refresh();
        HTPredictionRating.refresh();
        DeltaPredictionRating.refresh();
    }

    private void refresh() {

        // Refresh HO Lineup and ratings
        HORatings = HOVerwaltung.instance().getModel().getLineup().getRatings();
        HOLineup = HOVerwaltung.instance().getModel().getLineup();

        // TODO: Parse HT Rating
        HTRatings = new MatchRating(0, 0, 0, 0, 0, 0, 0, 0, 0);

        if (bFetchLineupSuccess) {
            isHOLineupValid = checkHOLineup();
            formatSendButton();
        }

        HOPredictionRating.setMatchRating(HORatings);
        HTPredictionRating.setMatchRating(HTRatings);
        DeltaPredictionRating.setMatchRating(HOPredictionRating.getMatchRating().minus(HTPredictionRating.getMatchRating()));
        refreshRatingComparisonPanel();



//        requiredLineup.forEach((key,value) -> System.out.println(key + " = " + value));

//



//        double LD = oRatings.getLeftDefense().get(0);
//        double CD = oRatings.getCentralDefense().get(0);
//        double RD = oRatings.getRightDefense().get(0);
//        double MF = oRatings.getMidfield().get(0);
//        double LA = oRatings.getLeftAttack().get(0);
//        double CA = oRatings.getCentralAttack().get(0);
//        double RA = oRatings.getRightAttack().get(0);
//
//        MatchRating mrHOPredictionRating = new MatchRating(LD, CD, RD, MF, LA, CA, RA, 0, 0);
//        MatchRating mrHTmatchRating = new MatchRating(0, 0, 0, 0, 0, 0, 0, 0, 0); //FIXME
//
//        RatingComparisonPanel HOPredictionRating = new RatingComparisonPanel("HO", mrHOPredictionRating);

    }

    private void initComponents() {

        GridBagConstraints gbc = new GridBagConstraints();
        setTitle("Feedback Plugin");
        layout = new GridBagLayout();
        this.setLayout(layout);

        // Lineup recommendation =====================================================================
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 5, 0, 5);
        gbc.gridwidth = 5;
        gbc.gridx = 0;
        gbc.gridy = 0;
        lineupRecommendation = new JLabel("<html><font color='red' weight='bold'>1.</font> Please set the <b>same lineup both in HO and in HT</b>, as per the following recommendation:</html>");
        this.add(lineupRecommendation, gbc);
        // ==========================================================================================

        // Lineup ======================================================================
        // GK ======================================================================
        gbc.insets = new Insets(15, 5, 5, 5);  //top padding
        gbc.gridwidth = 1;
        gbc.ipadx = 10;
        gbc.weightx = 1;
        gbc.gridx = 2;
        gbc.gridy = 1;
        GK = new JLabel("GK", JLabel.CENTER);
        GK.setOpaque(true);
        GK.setBackground(Color.WHITE);
        GK.setForeground(Color.GRAY);
        GK.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        formatPlayerBox(GK, "GK", requiredLineup.get(IMatchRoleID.keeper));
        this.add(GK, gbc);
        // WBr ======================================================================
        gbc.insets = new Insets(5, 5, 5, 5);  //top padding
        gbc.gridx = 0;
        gbc.gridy = 2;
        WBr = new JLabel("WBr", JLabel.CENTER);
        WBr.setOpaque(true);
        WBr.setBackground(Color.WHITE);
        WBr.setForeground(Color.GRAY);
        WBr.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        formatPlayerBox(WBr, "WBr", requiredLineup.get(IMatchRoleID.rightBack));
        this.add(WBr, gbc);
        // CDr ======================================================================
        gbc.gridx = 1;
        gbc.gridy = 2;
        JLabel CDr = new JLabel("CDr", JLabel.CENTER);
        CDr.setOpaque(true);
        CDr.setBackground(Color.WHITE);
        CDr.setForeground(Color.GRAY);
        CDr.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        formatPlayerBox(CDr, "CDr", requiredLineup.get(IMatchRoleID.rightCentralDefender));
        this.add(CDr, gbc);
        // CDc ======================================================================
        gbc.gridx = 2;
        gbc.gridy = 2;
        JLabel CDc = new JLabel("CD", JLabel.CENTER);
        CDc.setOpaque(true);
        CDc.setBackground(Color.WHITE);
        CDc.setForeground(Color.GRAY);
        CDc.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        formatPlayerBox(CDc, "CD", requiredLineup.get(IMatchRoleID.middleCentralDefender));
        this.add(CDc, gbc);
        // CDl ======================================================================
        gbc.gridx = 3;
        gbc.gridy = 2;
        JLabel CDl = new JLabel("CDl", JLabel.CENTER);
        CDl.setOpaque(true);
        CDl.setBackground(Color.WHITE);
        CDl.setForeground(Color.GRAY);
        CDl.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        formatPlayerBox(CDl, "CDl", requiredLineup.get(IMatchRoleID.leftCentralDefender));
        this.add(CDl, gbc);
        // WBl ======================================================================
        gbc.gridx = 4;
        gbc.gridy = 2;
        JLabel WBl = new JLabel("WBl", JLabel.CENTER);
        WBl.setOpaque(true);
        WBl.setBackground(Color.WHITE);
        WBl.setForeground(Color.GRAY);
        WBl.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        formatPlayerBox(WBl, "WBl", requiredLineup.get(IMatchRoleID.leftBack));
        this.add(WBl, gbc);
        // WIr ======================================================================
        gbc.insets = new Insets(5, 5, 5, 5);  //top padding
        gbc.gridx = 0;
        gbc.gridy = 3;
        JLabel WIr = new JLabel("WIr", JLabel.CENTER);
        WIr.setOpaque(true);
        WIr.setBackground(Color.WHITE);
        WIr.setForeground(Color.GRAY);
        WIr.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        formatPlayerBox(WIr, "WIr", requiredLineup.get(IMatchRoleID.rightWinger));
        this.add(WIr, gbc);
        // IMr ======================================================================
        gbc.gridx = 1;
        gbc.gridy = 3;
        JLabel IMr = new JLabel("IMr", JLabel.CENTER);
        IMr.setOpaque(true);
        IMr.setBackground(Color.WHITE);
        IMr.setForeground(Color.GRAY);
        IMr.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        formatPlayerBox(IMr, "IMr", requiredLineup.get(IMatchRoleID.rightInnerMidfield));
        this.add(IMr, gbc);
        // IMc ======================================================================
        gbc.gridx = 2;
        gbc.gridy = 3;
        JLabel IMc = new JLabel("IM", JLabel.CENTER);
        IMc.setOpaque(true);
        IMc.setBackground(Color.WHITE);
        IMc.setForeground(Color.GRAY);
        IMc.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        formatPlayerBox(IMc, "IM", requiredLineup.get(IMatchRoleID.centralInnerMidfield));
        this.add(IMc, gbc);
        // IMl ======================================================================
        gbc.gridx = 3;
        gbc.gridy = 3;
        JLabel IMl = new JLabel("IMl", JLabel.CENTER);
        IMl.setOpaque(true);
        IMl.setBackground(Color.WHITE);
        IMl.setForeground(Color.GRAY);
        IMl.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        formatPlayerBox(IMl, "IMl", requiredLineup.get(IMatchRoleID.leftInnerMidfield));
        this.add(IMl, gbc);
        // WIl ======================================================================
        gbc.gridx = 4;
        gbc.gridy = 3;
        JLabel WIl = new JLabel("WIl", JLabel.CENTER);
        WIl.setOpaque(true);
        WIl.setBackground(Color.WHITE);
        WIl.setForeground(Color.GRAY);
        WIl.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        formatPlayerBox(WIl, "WIl", requiredLineup.get(IMatchRoleID.leftWinger));
        this.add(WIl, gbc);
        // FWr ======================================================================
        gbc.gridx = 1;
        gbc.gridy = 4;
        JLabel FWr = new JLabel("FWr", JLabel.CENTER);
        FWr.setOpaque(true);
        FWr.setBackground(Color.WHITE);
        FWr.setForeground(Color.GRAY);
        FWr.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        formatPlayerBox(FWr, "FWr", requiredLineup.get(IMatchRoleID.rightForward));
        this.add(FWr, gbc);
        // FWc ======================================================================
        gbc.gridx = 2;
        gbc.gridy = 4;
        JLabel FWc = new JLabel("FW", JLabel.CENTER);
        FWc.setOpaque(true);
        FWc.setBackground(Color.WHITE);
        FWc.setForeground(Color.GRAY);
        FWc.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        formatPlayerBox(FWc, "FW", requiredLineup.get(IMatchRoleID.centralForward));
        this.add(FWc, gbc);
        // FWl ======================================================================
        gbc.gridx = 3;
        gbc.gridy = 4;
        JLabel FWl = new JLabel("FWl", JLabel.CENTER);
        FWl.setOpaque(true);
        FWl.setBackground(Color.WHITE);
        FWl.setForeground(Color.GRAY);
        FWl.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        formatPlayerBox(FWl, "FWl", requiredLineup.get(IMatchRoleID.leftForward));
        this.add(FWl, gbc);
        // ==========================================================================================

        // ========================================================================
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(30, 5, 0, 5);
        gbc.gridwidth = 5;
        gbc.gridx = 0;
        gbc.gridy = 5;
        lineupRecommendation = new JLabel("<html><font color='red' weight='bold'>2.</font> Paste the prediction ratings provided by HT</html>");
        this.add(lineupRecommendation, gbc);
        // ==========================================================================================

        // Copy Paste Area  ==========================================================================
//        gbc.fill =  GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 5, 0, 10);
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
        this.add(areaScrollPane, gbc);

        // ================ PREDICTION RATING  =========================================================================
        gbc.gridx = 0;
        gbc.gridy = 7;

        HOPredictionRating = new RatingComparisonPanel("HO");
        HTPredictionRating = new RatingComparisonPanel("HT");
        DeltaPredictionRating = new RatingComparisonPanel("Delta");

        JPanel content = new JPanel();
        content.add(HOPredictionRating);
        content.add(HTPredictionRating);
        content.add(DeltaPredictionRating);

        this.add(content, gbc);

        // =================BUTTONS    ===============================================================================================

        gbc.fill = GridBagConstraints.NONE;
        gbc.insets = new Insets(0, 5, 10, 5);
        gbc.ipady = 0;
        gbc.gridx = 1;
        gbc.gridy = 8;
        jbRefresh = new JButton("Refresh");
        jbRefresh.addActionListener(e -> refresh());
        this.add(jbRefresh, gbc);

        gbc.gridx = 3;
        jbSend = new JButton("Send");
        jbSend.addActionListener(e -> sendToServer());
        formatSendButton();


        this.add(jbSend, gbc);

        setSize(900, 800);
        setPreferredSize(getSize());
        setResizable(false);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

    }

    private class SimpleLineup {
        String lineupName;
        Map<Integer, Byte> lineup;
    }
}
