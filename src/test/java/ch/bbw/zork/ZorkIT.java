package ch.bbw.zork;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ZorkIT {

    @Mock
    private Parser parser;

    @Mock
    private ConfersationHandler confersationHandler;

    @Test
    public void gameStopsWhenQuitCommandIsIssued() {
        when(parser.getCommand(anyBoolean()))
                .thenReturn(new Command("go", "east"))
                .thenReturn(new Command("go", "north"))
                .thenReturn(new Command("go", "south"))
                .thenReturn(new Command("go", "south"))
                .thenReturn(new Command("go", "north"))
                .thenReturn(new Command("go", "east"))

                .thenReturn(new Command("go", "north"))
                .thenReturn(new Command("go", "south"))

                .thenReturn(new Command("go", "east"))
                .thenReturn(new Command("go", "north"))
                .thenReturn(new Command("go", "south"))
                .thenReturn(new Command("go", "south"))
                .thenReturn(new Command("go", "north"))
                .thenReturn(new Command("go", "east"))

                .thenReturn(new Command("go", "west"))
                .thenReturn(new Command("go", "west"))
                .thenReturn(new Command("go", "south"))
                .thenReturn(new Command("go", "south"))
                .thenReturn(new Command("go", "west"))
                .thenReturn(new Command("go", "west"))

                .thenReturn(new Command("go", "east"))
                .thenReturn(new Command("go", "north"))
                .thenReturn(new Command("go", "south"))
                .thenReturn(new Command("go", "south"))
                .thenReturn(new Command("go", "north"))
                .thenReturn(new Command("go", "east"))

                .thenReturn(new Command("go", "east"))
                .thenReturn(new Command("go", "north"))
                .thenReturn(new Command("go", "south"))
                .thenReturn(new Command("go", "south"))
                .thenReturn(new Command("go", "north"))
                .thenReturn(new Command("go", "east"))

                .thenReturn(new Command("go", "west"))
                .thenReturn(new Command("go", "west"))
                .thenReturn(new Command("go", "south"))

                .thenReturn(new Command("go", "west"))
                .thenReturn(new Command("go", "west"))

                .thenReturn(new Command("investigate"))
                .thenReturn(new Command("take", "Sprengstoff"))
                .thenReturn(new Command("take", "Uniform"))

                .thenReturn(new Command("go", "east"))

                .thenReturn(new Command("drop", "Sprengstoff"))
                .thenReturn(new Command("take", "Sprengstoff"))

                .thenReturn(new Command("go", "south"))

                .thenReturn(new Command("2"))

                .thenReturn(new Command("go", "north"))
                .thenReturn(new Command("go", "east"))
                .thenReturn(new Command("go", "east"))
                .thenReturn(new Command("go", "north"))

                .thenReturn(new Command("take", "Leiter"))
                .thenReturn(new Command("drop", "Sprengstoff"))
                .thenReturn(new Command("take", "Leiter"))

                .thenReturn(new Command("go", "south"))
                .thenReturn(new Command("go", "east"))

                .thenReturn(new Command("go", "east"))

                .thenReturn(new Command("1"))
                .thenReturn(new Command("3"))

                .thenReturn(new Command("go", "east"))

                .thenReturn(new Command("quit"));

        Game zorkgame = new Game(parser);
        zorkgame.play();

        assertTrue(Game.finished);
    }
}
