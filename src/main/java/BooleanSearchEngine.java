import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.canvas.parser.PdfTextExtractor;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class BooleanSearchEngine implements SearchEngine {
    protected Map<String, List<PageEntry>> wordsMap = new HashMap<>();

    public BooleanSearchEngine(File pdfsDir) throws IOException {

        File[] pdfs = pdfsDir.listFiles();
        if (pdfs != null) {
            for (File file : pdfs) {
                var doc = new PdfDocument(new PdfReader(file));
                for (int i = 1; i < doc.getNumberOfPages() + 1; i++) {
                    var textPage = doc.getPage(i);
                    var text = PdfTextExtractor.getTextFromPage(textPage);
                    var words = text.split("\\P{IsAlphabetic}+");
                    Map<String, Integer> freqs = new HashMap<>();
                    for (var word : words) {
                        if (word.isEmpty()) {
                            continue;
                        }
                        word = word.toLowerCase();
                        freqs.put(word, freqs.getOrDefault(word, 0) + 1);
                    }
                    for (var word : freqs.keySet()) {
                        int page = i;
                        List<PageEntry> pageList = new ArrayList<>();
                        if (!wordsMap.containsKey(word)) {
                            pageList.add(new PageEntry(file.getName(), page, freqs.get(word)));
                            pageList.sort(PageEntry::compareTo);
                            wordsMap.put(word, pageList);
                        } else {
                            List<PageEntry> entryList = wordsMap.get(word);
                            entryList.add(new PageEntry(file.getName(), page, freqs.get(word)));
                            entryList.sort(PageEntry::compareTo);
                            wordsMap.put(word, entryList);
                        }
                    }
                }
            }
        }
    }

    @Override
    public List<PageEntry> search(String word) {
        return wordsMap.get(word.toLowerCase());
    }

}
