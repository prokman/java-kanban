package service;

import model.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {

    private static class Node {
        Task item;
        Node next;
        Node prev;

        Node(Node prev, Task element, Node next) {
            this.item = element;
            this.next = next;
            this.prev = prev;
        }
    }

    Node first;
    Node last;

    void linkLast(Task task) {
        final Node Last = last;
        final Node newNode = new Node(Last, task, null);
        last = newNode;
        if (Last == null) {
            first = newNode;
        } else {
            Last.next = newNode;
        }
    }

    HashMap<Integer, Node> history = new HashMap<>();

    @Override
    public void removeNode(Integer id) {
        Node removedNode = history.remove(id);

        //final Task item = removedNode.item;
        if (removedNode != null) {
            final Node next = removedNode.next;
            final Node prev = removedNode.prev;

            if (prev == null) {
                first = next;
            } else {
                prev.next = next;
                removedNode.prev = null;
            }

            if (next == null) {
                last = prev;
            } else {
                next.prev = prev;
                removedNode.next = null;
            }
            removedNode.item = null;
        }
    }

    @Override
    public <T extends Task> void add(T task, Integer id) {
        removeNode(id);
        linkLast(task);
        history.put(id, last);
    }

    @Override
    public List<Task> getAll() {
        ArrayList<Task> arrayOfhistory = new ArrayList<>();
        for (Node item = first; item != null; item = item.next) {
            arrayOfhistory.add(item.item);
        }
        return arrayOfhistory;
    }
}
