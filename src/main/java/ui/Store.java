package ui;

import Player.Player;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.gui2.table.Table;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.List;

class Store extends UI_Helper {

    static void start(Player p, int selectedRow) throws Exception {

        BasicWindow window = new BasicWindow();
        Panel panel = new Panel(new GridLayout(1));
        Table<String> table = new Table<>("\u20BFitcoin", "Algorithm", "Description");
        table.setSelectedRow(selectedRow);
        panel.addComponent(new Label("Your bitcoin: \u20BF" + Float.toString(p.getBitcoin()))
                .setForegroundColor(TextColor.ANSI.BLUE)
                .setLayoutData(GridLayout.createLayoutData(GridLayout.Alignment.END, GridLayout.Alignment.CENTER)));

        table.getTableModel()
                .addRow("1.223", "num_brute", "Numerical brute force algorithm")
                .addRow("1.255", "alpha_brute", "Alphabetic brute force algorithm")
                .addRow("2.001", "dic", "Dictionary attack algorithm")
                .addRow("2.267", "comb_dic", "Multi-dictionary attack algorithm")
                .addRow("2.455", "hybrid_dic", "Brute force + dictionary attack algorithm")
                .addRow("0.501", "english", "English language dictionary")
                .addRow("0.434", "fnames", "Common English first names")
                .addRow("0.567", "snames", "Common english surnames")
                .addRow("0.599", "10kmc", "10K most common passwords")
                .addRow("1.234", "1mmc", "1m most common passwords");
        table.setSelectAction(() -> {

            List<String> data = table.getTableModel().getRow(table.getSelectedRow());

            try {
                if (p.getAllItmes().contains(data.get(1))) message(p.getGUI(), "ERROR", "You already own this item");
                else if (p.getBitcoin() < Float.valueOf(data.get(0))) {
                    DecimalFormat df = new DecimalFormat("0.000");
                    message(p.getGUI(), "ERROR", "You need \u20BF" + df.format(Float.valueOf(data.get(0))
                            - p.getBitcoin()) + " more to purchase \n" + data.get(1));
                }
                else {
                    BasicWindow window2 = new BasicWindow();
                    Panel panel2 = new Panel(new GridLayout(1));
                    TerminalSize ts = new TerminalSize(0,1);
                    Button yes = new Button("Yes", () -> {
                        try {
                            p.clearWindow();
                            Float price = Float.valueOf(data.get(0));
                            String write;
                            String items = p.getPurchaced();
                            int sRow = table.getSelectedRow();
                            write = items.substring(0,sRow) + '1' + items.substring(sRow+1);
                            p.writeProgress(0, price*-1 ,write,p.getColour());
                            p.getGUI().getActiveWindow().close();
                            start(p, table.getSelectedRow());
                        }
                        catch (Exception e) { e.printStackTrace(); }
                    }).setLayoutData(GridLayout.createLayoutData(GridLayout.Alignment.END, GridLayout.Alignment.CENTER));
                    Button no = new Button("No", () -> {
                        try { p.clearWindow(); }
                        catch (Exception e) { e.printStackTrace(); }
                    }).setLayoutData(GridLayout.createLayoutData(GridLayout.Alignment.END, GridLayout.Alignment.CENTER));

                    panel2.addComponent(new EmptySpace(TextColor.ANSI.DEFAULT, ts))
                          .addComponent(new Label("Are you sure you want to purchase \n" + data.get(1) + " for \u20BF" + data.get(0) + "?"))
                          .addComponent(new EmptySpace(TextColor.ANSI.DEFAULT, ts))
                          .addComponent(yes).addComponent(no);

                    window2.setTitle("CONFIRM");
                    window2.setHints(Arrays.asList(Window.Hint.CENTERED));
                    window2.setComponent(panel2);
                    p.getGUI().addWindowAndWait(window2);
                }
            } catch (Exception e) { e.printStackTrace(); }
        });

        TerminalSize ts = new TerminalSize(0,1);

        window.setHints(Arrays.asList(Window.Hint.CENTERED));
        window.setTitle("STORE");

        panel.addComponent(table);
        panel.addComponent(new EmptySpace(TextColor.ANSI.DEFAULT, ts));
        panel.addComponent(new Button("OK", () -> {
            try { Menu.start(p); }
            catch (Exception e) { e.printStackTrace(); }
        }).setLayoutData(GridLayout.createLayoutData(GridLayout.Alignment.END, GridLayout.Alignment.CENTER)));

        window.setComponent(panel);
        p.getGUI().addWindowAndWait(window);
    }
}