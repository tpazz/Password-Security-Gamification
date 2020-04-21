package ui;

import Player.Player;
import algorithms.MD5;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.gui2.table.Table;
import me.gosimple.nbvcxz.Nbvcxz;
import me.gosimple.nbvcxz.matching.match.Match;
import me.gosimple.nbvcxz.resources.Feedback;
import me.gosimple.nbvcxz.scoring.Result;
import me.gosimple.nbvcxz.scoring.TimeEstimate;

import java.text.DecimalFormat;
import java.util.Collections;

public class Password extends UI_Helper {

    public static void start(Player p) throws Exception {
        p.clearWindow();

        BasicWindow window = new BasicWindow();
        window.setTitle("ENTER A PASSWORD");
        Panel panel = new Panel(new GridLayout(2));
        Label lbl = new Label("Enter password:")
                .setLayoutData(GridLayout.createLayoutData(GridLayout.Alignment.BEGINNING, GridLayout.Alignment.CENTER));
        Label lbl2 = new Label("Confirm password:")
                .setLayoutData(GridLayout.createLayoutData(GridLayout.Alignment.CENTER, GridLayout.Alignment.CENTER));
        TextBox tb = new TextBox(new TerminalSize(20,1))
                .setMask('•')
                .setLayoutData(GridLayout.createLayoutData(GridLayout.Alignment.CENTER, GridLayout.Alignment.CENTER));
        TextBox tb2 = new TextBox(new TerminalSize(20,1))
                .setMask('•')
                .setLayoutData(GridLayout.createLayoutData(GridLayout.Alignment.CENTER, GridLayout.Alignment.CENTER));
        Button ok = new Button("OK", () -> {
            if (tb.getText().equals("")) {
                message(p.getGUI(), "ERROR", "Password cannot be empty.");
            } else if (!tb.getText().equals(tb2.getText())) {
                message(p.getGUI(), "ERROR", "Passwords must match.");
            } else {
                try { displayResults(p, tb2.getText()); }
                catch (Exception e) { e.printStackTrace(); }
            }
        }).setLayoutData(GridLayout.createLayoutData(GridLayout.Alignment.END, GridLayout.Alignment.END));
        Button cancel = new Button("Cancel", () -> {
            try {
                Menu.start(p);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).setLayoutData(GridLayout.createLayoutData(GridLayout.Alignment.END, GridLayout.Alignment.END));

        panel.addComponent(new EmptySpace(TextColor.ANSI.DEFAULT, new TerminalSize(0,1)));
        panel.addComponent(new EmptySpace(TextColor.ANSI.DEFAULT, new TerminalSize(0,1)));
        panel.addComponent(lbl);
        panel.addComponent(tb);
        panel.addComponent(lbl2);
        panel.addComponent(tb2);
        panel.addComponent(new EmptySpace(TextColor.ANSI.DEFAULT, new TerminalSize(0,1)));
        panel.addComponent(new EmptySpace(TextColor.ANSI.DEFAULT, new TerminalSize(0,1)));
        panel.addComponent(new EmptySpace(TextColor.ANSI.DEFAULT, new TerminalSize(0,1)));
        panel.addComponent(ok);
        panel.addComponent(new EmptySpace(TextColor.ANSI.DEFAULT, new TerminalSize(0,1)));
        panel.addComponent(cancel);
        window.setHints(Collections.singletonList(Window.Hint.CENTERED));
        window.setComponent(panel);
        p.getGUI().addWindowAndWait(window);
    }

    private static void displayResults(Player p, String pwd) throws Exception {
        p.clearWindow();
        Nbvcxz nbvcxz = new Nbvcxz();
        Result firstPwd = nbvcxz.estimate(pwd);
        Feedback firstFeedback = firstPwd.getFeedback();
        String timeToCrackBC1 = TimeEstimate.getTimeToCrackFormatted(firstPwd, "OFFLINE_BCRYPT_12");
        String timeToCrackON1 = TimeEstimate.getTimeToCrackFormatted(firstPwd, "ONLINE_THROTTLED");
        String timeToCrackON2 = TimeEstimate.getTimeToCrackFormatted(firstPwd, "ONLINE_UNTHROTTLED");
        BasicWindow window = new BasicWindow();
        Panel panel = new Panel(new GridLayout(1));
        Table<String> table = new Table<>("Test", "Output");
        StringBuilder sb = new StringBuilder();
        String achievements = p.getAchievements();
        float bitcoin = 0.0f;
        if (firstPwd.isMinimumEntropyMet()) {
            bitcoin = Math.round((1.0f + (firstPwd.getEntropy()/100f)) * 1000f) / 1000f;
            p.writeProgress(0, bitcoin ,p.getPurchaced(),p.getColour(),p.getAchievements());
        }
        if (firstFeedback.getWarning() != null) {
            if (firstFeedback.getWarning().equals("This is a top-10 common password.")) {
                achievements = achievements.substring(0,4)+'1'+achievements.substring(5);
            }
        }
        if (firstPwd.getBasicScore() == 1) {
            achievements = achievements.substring(0,5)+'1'+achievements.substring(6);
        }
        if (firstPwd.getBasicScore() == 2) {
            achievements = achievements.substring(0,6)+'1'+achievements.substring(7);
        }
        if (firstPwd.getBasicScore() == 3) {
            achievements = achievements.substring(0,7)+'1'+achievements.substring(8);
        }
        if (firstPwd.getEntropy() >= 60) {
            achievements = achievements.substring(0,8)+'1'+achievements.substring(9);
        }
        p.writeProgress(0,0.0f,p.getPurchaced(),p.getColour(),achievements);

        if (firstFeedback.getWarning() != null && firstFeedback.getSuggestion() != null) {
            String warnin = firstFeedback.getWarning();
            if (firstFeedback.getWarning() != null) {
                if (warnin.length() > 59) {
                    warnin = warnin.substring(0,58) + "\n" + warnin.substring(59);
                }
                sb.append("! - ").append(warnin).append("\n");
            }
            for (String suggestion : firstFeedback.getSuggestion()) {
                if (suggestion.length() > 56) {
                    suggestion = suggestion.substring(0,55) + "\n" + suggestion.substring(56);
                }
                sb.append("? - ").append(suggestion).append("\n");
            }
        } else {
            sb.append("No feedback available for this password. Feedback is\n" +
                    "only displayed for insecure passwords.");
        }

        Button feedback = new Button("Feedback", () -> {
            message(p.getGUI(), "FEEDBACK", sb.toString());
        }).setLayoutData(GridLayout.createLayoutData(GridLayout.Alignment.END, GridLayout.Alignment.CENTER));
        Button ok = new Button("Return to Menu", () -> {
            try {
                Menu.start(p);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).setLayoutData(GridLayout.createLayoutData(GridLayout.Alignment.END, GridLayout.Alignment.CENTER));
        Button again = new Button("Enter another password", () -> {
            p.getGUI().getActiveWindow().close();
            try {
                start(p);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).setLayoutData(GridLayout.createLayoutData(GridLayout.Alignment.END, GridLayout.Alignment.CENTER));
        DecimalFormat df = new DecimalFormat("#.###");
        table.getTableModel()
                .addRow("Plain-text:", firstPwd.getPassword())
                .addRow("Online throttled crack:", timeToCrackON1)
                .addRow("Online unthrottled crack:", timeToCrackON2)
                .addRow("Offline BCRYPT-12 crack", timeToCrackBC1)
                .addRow("Number of guesses:", String.format("%,.0f",firstPwd.getGuesses()))
                .addRow("Entropy:", String.valueOf(df.format(firstPwd.getEntropy())))
                .addRow("Score:", String.valueOf(firstPwd.getBasicScore()))
                .addRow("Secure?", String.valueOf(firstPwd.isMinimumEntropyMet()))
                .addRow("\u20BFitcoin earned:", String.valueOf(bitcoin));
        table.setEnabled(false);
        table.setLayoutData(GridLayout.createLayoutData(GridLayout.Alignment.CENTER, GridLayout.Alignment.CENTER));
        panel.addComponent(new Label("Your bitcoin: \u20BF" + Float.toString(p.getBitcoin()))
                .setForegroundColor(TextColor.ANSI.BLUE)
                .setLayoutData(GridLayout.createLayoutData(GridLayout.Alignment.END, GridLayout.Alignment.CENTER)));
        panel.addComponent(table);
        panel.addComponent(new EmptySpace(TextColor.ANSI.DEFAULT, new TerminalSize(0,1)));
        panel.addComponent(new Label("• Entropy is a measure of predictability (1 - predictable)"));
        panel.addComponent(new Label("• Score ranges from 0 (insecure) - 4 (very secure)"));
        panel.addComponent(new Label("• A password is deemed secure if it passes the entropy threshold (35)"));
        panel.addComponent(new EmptySpace(TextColor.ANSI.DEFAULT, new TerminalSize(0,1)));
        panel.addComponent(feedback);
        panel.addComponent(again);
        panel.addComponent(ok);

        window.setTitle("RESULTS");
        window.setHints(Collections.singletonList(Window.Hint.CENTERED));
        window.setComponent(panel);
        p.getGUI().addWindowAndWait(window);
    }
}
