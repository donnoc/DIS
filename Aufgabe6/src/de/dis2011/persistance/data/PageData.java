package de.dis2011.persistance.data;

public class PageData
{
    private int    pageID;
    private String data;

    private PageData()
    {
    }

    public static PageData createPage(int pageID, String data)
    {
        PageData result = new PageData();
        result.pageID = pageID;
        result.data = data;
        return result;
    }

    public int getPageID()
    {
        return pageID;
    }

    public String getData()
    {
        return data;
    }

    //nur damit die fehler im PersistenceManager raus sind!!!
    public int getLogSequenceNumber()
    {
        return 1;
    }
    public static PageData createPage(int pageID2, int logSequenceNumber, String data2)
    {
        return null;
    }
}
