import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Comparator;
import java.util.TreeSet;
import java.util.concurrent.ForkJoinPool;

public class Main
{
    public static void main(String[] args)
    {
        Node root = getRootNode();
        String result = new ForkJoinPool().invoke(new ChildrenList(root));
        System.out.println("Done!");
        try
        {
            FileWriter writer = new FileWriter(new File("data\\result.txt"), false);
            writer.write(result);
            writer.close();
        } catch (IOException ex)
        {
            ex.printStackTrace();
        }
    }

    private static Node getRootNode()
    {
        return new Root("https://pcgroup.ru/blog/termometr--pribor-dlya-izmereniya-temperatury/", "", 1, new TreeSet<>(Comparator.naturalOrder()));
    }

}
