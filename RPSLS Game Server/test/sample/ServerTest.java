package sample;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ServerTest {

    @Test
    void serverConstructor1() {
        Server server = new Server(5555);
        assertNotNull(server);
    }

    @Test
    void serverConstructor2() {
        Server server = new Server(5555);
        assertEquals(server.port, 5555);
    }

    @Test
    void serverConstructor3() {
        Server server = new Server(5555);
        assertNotNull(server.player);
    }

    @Test
    void closeTest1(){
        Server server = new Server(5555);
        server.close();
        assertNull(server.ssocket);
    }

    @Test
    void closeTest2(){
        Server server = new Server(5555);
        server.close();
        assertNull(server.player[0]);
    }

    @Test
    void closeTest3(){
        Server server = new Server(5555);
        server.close();
        assertNull(server.player[1]);
    }
}