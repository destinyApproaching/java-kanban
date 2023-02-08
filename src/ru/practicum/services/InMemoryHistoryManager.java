package ru.practicum.services;

import ru.practicum.models.Task;

import java.util.*;

public class InMemoryHistoryManager implements HistoryManager {
    private final CustomLinkedList<Task> history = new CustomLinkedList<>();

    @Override
    public void remove(int id) {
        if (history.nodeMap.containsKey(id)) {
            history.removeNode(history.nodeMap.get(id));
            history.nodeMap.remove(id);
        }
    }

    @Override
    public void add(Task task) {
        int id = task.getTaskId();
        if (history.nodeMap.containsKey(task.getTaskId())) {
            remove(id);
        }
        history.nodeMap.put(id, history.linkList(task));
    }

    @Override
    public List<Task> getHistory() {
        return history.getTasks();
    }

    private static class CustomLinkedList<T> {
        Map<Integer, Node<T>> nodeMap = new HashMap<>();
        private Node<T> head;
        private Node<T> tail;

        public Node<T> linkList(T t) {
            final Node<T> oldTail = tail;
            final Node<T> newNode = new Node<>(oldTail, t, null);
            tail = newNode;
            if (oldTail == null)
                head = newNode;
            else
                oldTail.next = newNode;
            return tail;
        }

        public List<T> getTasks() {
            List<T> list = new ArrayList<>();
            Node<T> newHead = head;
            while (newHead != null) {
                list.add(newHead.data);
                newHead = newHead.next;
            }
            System.out.println(list.size());
            return list;
        }

        public void removeNode(Node<T> node) {
            if (node == head && node == tail) {
                head = null;
                tail = null;
            } else if (node == head) {
                head = node.next;
            } else if (node == tail) {
                tail = node.prev;
            } else {
                Node<T> prev = node.prev;
                Node<T> next = node.next;
                prev.next = next;
                next.prev = prev;
            }
        }
    }
}