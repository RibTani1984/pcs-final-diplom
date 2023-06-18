public class PageEntry implements Comparable<PageEntry> {
    final String pdfName;
    final int page;
    final int count;

    public PageEntry(String pdfName, int page, int count) {
        this.pdfName = pdfName;
        this.page = page;
        this.count = count;
    }

    @Override
    public int compareTo(PageEntry o) {
        if (this.count == 0) {
            return 0;
        } else if (this.count < o.count) {
            return 1;
        } else return -1;
    }

    @Override
    public String toString() {
        return "PageEntry{" +
                "pdfName='" + pdfName + '\'' +
                ", page=" + page +
                ", count=" + count +
                '}';
    }
}