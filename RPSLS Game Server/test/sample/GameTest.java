package sample;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {
    @Test
    void gameConstructor1() {
        Game game = new Game();
        assertTrue(game.played[0].equals("nothing"), "played[0] faulty");
        assertTrue(game.played[1].equals("nothing"), "played[1] faulty");
    }

    @Test
    void gameConstructor2() {
        Game game = new Game();
        assertEquals(game.numPlayers, 0);
    }

    @Test
    void gameConstructor3() {
        Game game = new Game();
        assertEquals(game.score[0], 0, "score[0] faulty");
        assertEquals(game.score[1], 0, "score[1] faulty");
    }
}