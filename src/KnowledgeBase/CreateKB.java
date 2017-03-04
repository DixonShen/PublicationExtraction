package KnowledgeBase;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Z003R98D on 2/24/2017.
 */
public class CreateKB {

    public Map<String, List<String>> mKB = new HashMap<>();

    public static final String[] AttributeSet = {"title", "author", "journals", "booktitle",
                                                 "year", "pages", "volume"};

    public Map<String, List<String>> create() {

        return mKB;
    }

}
