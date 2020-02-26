package ui;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

public class Profile {
    public void start() throws Exception {
        Terminal terminal = new DefaultTerminalFactory().createTerminal();
        Screen screen = new TerminalScreen(terminal);
        TextGraphics textGraphics = screen.newTextGraphics();
        screen.setCursorPosition(null);
        StringBuilder stringBuilder = new StringBuilder();
        textGraphics.setBackgroundColor(TextColor.ANSI.MAGENTA);
        screen.refresh();
    }
}
