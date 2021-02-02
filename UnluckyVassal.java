import java.util.*;
import java.util.stream.Collectors;

public class UnluckyVassal {
    private Map<String, List<String>> map = new HashMap<>();

    public void printReportForKing(List<String> pollResults) {
        // по каждому человеку формируем список людей, которые являются его слугами. Если null - то слуг нет.
        pollResults.stream()
                .map(string -> string.split(":"))
                .forEach(array -> {
                    if (array.length == 1) {
                        map.put(array[0].trim(), null);
                    } else {
                        String[] split = array[1].split(",");
                        List<String> slaves = Arrays.stream(split).map(String::trim).sorted().collect(Collectors.toList());
                        map.put(array[0].trim(), slaves);
                    }
                });
        // получаем список людей, которые являются слугами
        List<String> slaves = map.values().stream()
                .filter(Objects::nonNull)
                .flatMap(List::stream)
                .distinct()
                .collect(Collectors.toList());
        // получаем список людей, которые не являются чьими-либо слугами
        List<String> peopleWithoutMaster = map.keySet().stream()
                .filter(key -> !slaves.contains(key))
                .sorted()
                .collect(Collectors.toList());
        // выводим информацию
        System.out.println("Король");
        printOneRecord(peopleWithoutMaster, "\t\t");
    }

    void printOneRecord(List<String> list, String delimeter) {
        if (list == null) {
            return;
        }
        for (String record : list) {
            System.out.println(delimeter + record);
            printOneRecord(map.get(record), delimeter + "\t\t");
        }
    }
}
