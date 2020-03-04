import Player.Player;
import com.googlecode.lanterna.graphics.TextGraphics;
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
        TextGraphics textGraphics = screen.newTextGraphics();
        // create player object and start menu UI
        Player player = new Player(wGUI, terminal, screen, textGraphics);
        Menu.start(player);
    }
}