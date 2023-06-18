import java.util.List;

public interface SearchEngine { //поисковый движок
    List<PageEntry> search(String word);
}