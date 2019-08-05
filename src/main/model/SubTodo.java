package model;

public class SubTodo extends Todo {
    private transient SuperTodo head;

    public SubTodo(String name, String due) {
        super(name, due, false, "Sub");
    }

    public SubTodo(String name, String due, boolean status) {
        super(name, due, status,"Sub");
    }

    public void addSuper(SuperTodo st) {
        if (!st.equals(head)) {
            head = st;
            st.addSubTodo(this);
        }
    }

    public SuperTodo getHead() {
        return head;
    }

    @Override
    public void setStatus(boolean i) {
        super.setStatus(i);
        System.out.println(head.completedSubTodos() + " out of " + head.getSubList().size()
                + " sub todos are completed");
    }

    @Override
    public String toString() {
        return "     " + super.toString();
    }
}
