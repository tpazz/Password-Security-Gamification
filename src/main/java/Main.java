import com.googlecode.lanterna.gui2.MultiWindowTextGUI;
import com.googlecode.lanterna.gui2.WindowBasedTextGUI;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import ui.Menu;
public class Main {

    public static void main(String[] args) throws Exception {
        // set up screen and terminal layers for application
        Terminal terminal = new DefaultTerminalFactory().createTerminal();
        Screen screen = new TerminalScreen(terminal);
        screen.startScreen();
        WindowBasedTextGUI wGUI = new MultiWindowTextGUI(screen);
        // start UI
        Menu.start(wGUI, terminal);
    }
}