import com.googlecode.lanterna.gui2.MultiWindowTextGUI;
import com.googlecode.lanterna.gui2.dialogs.ActionListDialogBuilder;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.gui2.*;

public class Main {
    public static void main(String[] args) throws Exception {
        // Setup terminal and screen layers
        Terminal terminal = new DefaultTerminalFactory().createTerminal();
        Screen screen = new TerminalScreen(terminal);
        screen.startScreen();

        // Setup WindowBasedTextGUI for dialogs
        final WindowBasedTextGUI textGUI = new MultiWindowTextGUI(screen);

        // Create panel to hold components
        new ActionListDialogBuilder()
                .setTitle("Action List Dialog")
                .setDescription("Choose an item")
                .addAction("First Item", new Runnable() {
                    @Override
                    public void run() {
                        // Do 1st thing...
                    }
                })
                .addAction("Second Item", new Runnable() {
                    @Override
                    public void run() {
                        // Do 2nd thing...
                    }
                })
                .addAction("Third Item", new Runnable() {
                    @Override
                    public void run() {
                        // Do 3rd thing...
                    }
                })
                .build()
                .showDialog(textGUI);
    }
}
