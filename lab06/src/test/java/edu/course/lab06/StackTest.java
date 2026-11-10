package edu.course.lab06;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.NoSuchElementException;
import org.junit.jupiter.api.Test;

class StackTest {

    @Test
    void newStackIsEmpty() {
        Stack<String> stack = new Stack<>();

        assertTrue(stack.isEmpty());
        assertEquals(0, stack.size());
    }

    @Test
    void pushMakesStackNonEmptyAndIncreasesSize() {
        Stack<String> stack = new Stack<>();

        stack.push("a");
        stack.push("b");

        assertFalse(stack.isEmpty());
        assertEquals(2, stack.size());
    }

    @Test
    void peekReturnsTopWithoutRemovingIt() {
        Stack<String> stack = new Stack<>();
        stack.push("a");
        stack.push("b");

        assertEquals("b", stack.peek());
        assertEquals("b", stack.peek());
        assertEquals(2, stack.size());
    }

    @Test
    void popReturnsTopAndRemovesIt() {
        Stack<String> stack = new Stack<>();
        stack.push("a");
        stack.push("b");

        assertEquals("b", stack.pop());
        assertEquals(1, stack.size());
        assertEquals("a", stack.peek());
    }

    @Test
    void elementsComeOutInReverseOrder() {
        Stack<String> stack = new Stack<>();
        stack.push("first");
        stack.push("second");
        stack.push("third");

        assertEquals("third", stack.pop());
        assertEquals("second", stack.pop());
        assertEquals("first", stack.pop());
        assertTrue(stack.isEmpty());
    }

    @Test
    void popOnEmptyStackThrowsNoSuchElementException() {
        Stack<String> stack = new Stack<>();

        assertThrows(NoSuchElementException.class, stack::pop);
    }

    @Test
    void peekOnEmptyStackThrowsNoSuchElementException() {
        Stack<String> stack = new Stack<>();

        assertThrows(NoSuchElementException.class, stack::peek);
    }

    @Test
    void stackBecomesEmptyAgainAfterPoppingEverything() {
        Stack<String> stack = new Stack<>();
        stack.push("a");
        stack.pop();

        assertTrue(stack.isEmpty());
        assertThrows(NoSuchElementException.class, stack::pop);
    }

    @Test
    void nullIsRejectedAndStackStaysUnchanged() {
        Stack<String> stack = new Stack<>();

        assertThrows(NullPointerException.class, () -> stack.push(null));
        assertEquals(0, stack.size());
    }

    @Test
    void worksWithIntegersWithoutCasts() {
        Stack<Integer> stack = new Stack<>();
        stack.push(1);
        stack.push(2);
        stack.push(3);

        int top = stack.pop();
        int next = stack.peek();

        assertEquals(3, top);
        assertEquals(2, next);
        assertEquals(2, stack.size());
    }

    @Test
    void worksWithProfileModel() {
        Stack<ProjectTask> stack = new Stack<>();
        ProjectTask first = new ProjectTask("T-101", "Prepare release", "backend", 5, TaskStatus.NEW);
        ProjectTask second = new ProjectTask("T-102", "Prepare release", "frontend", 3, TaskStatus.READY);
        stack.push(first);
        stack.push(second);

        assertSame(second, stack.pop());   // тип результата известен компилятору: приведение не нужно
        assertSame(first, stack.pop());
        assertTrue(stack.isEmpty());
    }
}
