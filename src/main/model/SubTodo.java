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

    @Override
    public void setStatus(int i) {
        super.setStatus(i);
        System.out.println(head.completedSubTodos() + " out of " + head.getSubList().size()
                + " sub todos are completed");
    }

    @Override
    public void printTodo(int i) {
        System.out.print("       ☆[" + i + "]  ");
        if (this.status) {
            System.out.println(this.name + "   :   " + this.due + "   " + "☑");
        } else {
            System.out.println(this.name + "   :   " + this.due + "   " + "☐");
        }
    }
}
