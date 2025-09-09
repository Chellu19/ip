package bazinga.test;
import bazinga.task.Todo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;


class TodoTest {
    @Test
    void testToString_correctFormat() {
        Todo t = new Todo("Do SWE");
        String expected = "[T][ ] Do SWE";
        assertEquals(expected, t.toString());
    }

    @Test
    void testToSaveFormat_notDone() {
        Todo t = new Todo("Do SWE", false);
        String expected = "T | 0 | Do SWE";
        assertEquals(expected, t.toSaveFormat());
    }

    @Test
    void testToSaveFormat_done() {
        Todo t = new Todo("Do SWE", true);
        String expected = "T | 1 | Do SWE";
        assertEquals(expected, t.toSaveFormat());
    }
}
