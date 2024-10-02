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

    private Node first;
    private Node last;

    void linkLast(Task task) {
        final Node oldLast = last;
        final Node newNode = new Node(oldLast, task, null);
        last = newNode;
        if (oldLast == null) {
            first = newNode;
        } else {
            oldLast.next = newNode;
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
    public <T extends Task> void add(T task) {
        if (task!=null) {
            removeNode(task.getId());
            linkLast(task);
            history.put(task.getId(), last);
        }
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
