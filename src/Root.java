import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.*;

public class Root implements Node
{
    private String site;
    private String indent;
    private int depthLevel;
    private TreeSet<String> links;

    Root(String site, String level, int depthLevel, TreeSet<String> links)
    {
        this.site = site;
        this.indent = level;
        this.depthLevel = depthLevel;
        this.links = links;
    }

    @Override
    public List<Node> getChildren() throws Exception
    {
        Thread.sleep(500);
        Document doc = Jsoup.connect(site).ignoreContentType(true).maxBodySize(0).get();
        Elements links = doc.select("a[href^=/]");

        TreeSet<String> tempLinks = new TreeSet<>();
        for (Element link : links)
        {
            if (!(this.links.contains(link.absUrl("href").split("#", 2)[0])))
            {
                this.links.add(link.absUrl("href"));
                tempLinks.add(link.absUrl("href"));
            }
        }
        if (links.size() == 0) return null;

        List<Node> collection = new ArrayList<>();
        for (String link : tempLinks)
        {
            collection.add(new Root(link, indent + "\t", ++depthLevel, this.links));
        }
        return collection;
    }

    @Override
    public String getValue()
    {
        return site + "\n";
    }

    @Override
    public String getIndent()
    {
        return indent;
    }
}
