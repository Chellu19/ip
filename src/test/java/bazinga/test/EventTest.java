package bazinga.test;

import bazinga.task.Event;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EventTest {

    @Test
    void testToString_correctFormat() {
        bazinga.task.Event e = new bazinga.task.Event("CS lecture", "2025-09-01 10:00", "2025-09-01 12:00");
        String expected = "[E][ ] CS lecture (from: Sep 01 2025 10:00 to: Sep 01 2025 12:00)";
        assertEquals(expected, e.toString());
    }

    @Test
    void testToSaveFormat_notDone() {
        Event e = new Event("CS lecture", "2025-09-01 10:00", "2025-09-01 12:00");
        String expected = "E | 0 | CS lecture | 2025-09-01 10:00 | 2025-09-01 12:00";
        assertEquals(expected, e.toSaveFormat());
    }

    @Test
    void testToSaveFormat_done() {
        Event e = new Event("CS lecture", true, "2025-09-01 10:00", "2025-09-01 12:00");
        String expected = "E | 1 | CS lecture | 2025-09-01 10:00 | 2025-09-01 12:00";
        assertEquals(expected, e.toSaveFormat());
    }
}