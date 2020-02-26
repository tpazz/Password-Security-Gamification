import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import ui.Helper;
import ui.Menu;
import ui.Profile;

public class Main extends Helper {

    public static void main(String[] args) throws Exception {

        // Setup terminal and screen layers
        Terminal terminal = new DefaultTerminalFactory().createTerminal();
        Screen screen = new TerminalScreen(terminal);

        WindowBasedTextGUI textGUI = new MultiWindowTextGUI(screen);
        screen.startScreen();

        //Profile profile = new Profile(textGUI,terminal);
        // launch menu UI
        Menu menu = new Menu(textGUI, terminal);
        menu.start(textGUI);
    }
}