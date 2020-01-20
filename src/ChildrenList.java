import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.RecursiveTask;

public class ChildrenList extends RecursiveTask<String>
{
    private final Node node; // узел

    ChildrenList(Node node) {
        this.node = node;
    }

    @Override
    protected String compute() {
        StringBuffer buffer = new StringBuffer(node.getValue());
        System.out.println(buffer.toString());// чтобы не скучно было ждать конец программы

        List<ChildrenList> subTasks = new LinkedList<>();
        try {
            List<Node> getChildren = node.getChildren();
            if (getChildren != null)
            {
                for (Node child : getChildren) {
                    ChildrenList task = new ChildrenList(child);
                    task.fork();
                    subTasks.add(task);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        for(ChildrenList task : subTasks)
        {
            String join = task.join();
            String nodeIndent = node.getIndent();
            buffer.append(nodeIndent).append(join);
        }
        return buffer.toString();
    }
}
