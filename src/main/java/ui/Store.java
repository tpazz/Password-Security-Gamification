package ui;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.gui2.table.Table;
import com.googlecode.lanterna.terminal.Terminal;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.List;

public class Store extends UI {

    public static void start(WindowBasedTextGUI gui, Terminal terminal) throws Exception {

        BasicWindow window = new BasicWindow();
        Panel panel = new Panel(new GridLayout(1));
        Table<String> table = new Table<>("\u20BFitcoin", "Algorithm", "Description");

        panel.addComponent(new Label("Your bitcoin: \u20BF" + Float.toString(getBitcoin()))
                .setForegroundColor(TextColor.ANSI.BLUE)
                .setLayoutData(GridLayout.createLayoutData(GridLayout.Alignment.END, GridLayout.Alignment.CENTER)));

        table.getTableModel()
                .addRow("1.223", "num_brute.exe", "Numerical brute force algorithm")
                .addRow("1.255", "alpha_brute.exe", "Alphabetic brute force algorithm")
                .addRow("1.702", "alpha-num_brute.exe", "Alphanumeric brute force algorithm")
                .addRow("2.001", "dictionary.exe", "Dictionary attack algorithm")
                .addRow("2.267", "combinator_dic.exe", "Multi-dictionary attack algorithm")
                .addRow("2.455", "hybrid_dic.exe", "Brute force + dictionary attack algorithm")
                .addRow("2.998", "keyword.exe", "User defined brute force algorithm")
                .addRow("0.501", "english.dic", "English dictionary")
                .addRow("0.201", "common.dic", "Common passwords")
                .addRow("0.434", "pwnd.dic", "Most used passwords");
        table.setSelectAction(() -> {

            List<String> data = table.getTableModel().getRow(table.getSelectedRow());

            try {
                if (getBitcoin() < Float.valueOf(data.get(0))) {
                    DecimalFormat df = new DecimalFormat("0.000");
                    message(gui, "ERROR", "You need \u20BF" + df.format(Float.valueOf(data.get(0))
                            - getBitcoin()) + " more to purchase \n" + data.get(1));
                }
                else {
                    BasicWindow window2 = new BasicWindow();
                    Panel panel2 = new Panel(new GridLayout(1));
                    TerminalSize ts = new TerminalSize(0,1);
                    Button yes = new Button("Yes", () -> {
                        try {
                            gui.removeWindow(gui.getActiveWindow());
                            String write;
                            String items = getPurchaced();
                            int sRow = table.getSelectedRow();
                            write = items.substring(0,sRow) + '1' + items.substring(sRow+1);
                            writeFile(0,0.0f,write);
                        }
                        catch (Exception e) { e.printStackTrace(); }
                    }).setLayoutData(GridLayout.createLayoutData(GridLayout.Alignment.END, GridLayout.Alignment.CENTER));
                    Button no = new Button("No", () -> {
                        try { gui.removeWindow(gui.getActiveWindow()); }
                        catch (Exception e) { e.printStackTrace(); }
                    }).setLayoutData(GridLayout.createLayoutData(GridLayout.Alignment.END, GridLayout.Alignment.CENTER));

                    panel2.addComponent(new EmptySpace(TextColor.ANSI.DEFAULT, ts))
                          .addComponent(new Label("Are you sure you want to purchase \n" + data.get(1) + " for \u20BF" + data.get(0) + "?"))
                          .addComponent(new EmptySpace(TextColor.ANSI.DEFAULT, ts))
                          .addComponent(yes).addComponent(no);

                    window2.setTitle("CONFIRM");
                    window2.setHints(Arrays.asList(Window.Hint.CENTERED));
                    window2.setComponent(panel2);
                    gui.addWindowAndWait(window2);
                }
            } catch (Exception e) { e.printStackTrace(); }
        });

        TerminalSize ts = new TerminalSize(0,1);

        window.setHints(Arrays.asList(Window.Hint.CENTERED));
        window.setTitle("STORE");

        panel.addComponent(table);
        panel.addComponent(new EmptySpace(TextColor.ANSI.DEFAULT, ts));
        panel.addComponent(new Button("OK", () -> {
            try { Menu.start(gui, terminal); }
            catch (Exception e) { e.printStackTrace(); }
        }).setLayoutData(GridLayout.createLayoutData(GridLayout.Alignment.END, GridLayout.Alignment.CENTER)));

        window.setComponent(panel);
        gui.addWindowAndWait(window);
    }
}