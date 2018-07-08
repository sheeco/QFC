import pers.sunyunmiao.qfc.Utils;

import java.util.*;

public class Cut implements Command {

    //e.g. (cut) -b(yte)|-c(haracter)
    @Override
    public String process(String input, List<String> args) throws Exception {

        if (args.isEmpty())
            return input;

        List<String> lines, results = new ArrayList<String>();

        //Use file as input if filename is provided
        //Only 1 filename is allowed
        if (!args.get(0).startsWith("-")) {

            String filename = args.get(0);
            input = Utils.readFile(filename);
            args = args.subList(1, args.size());
        }

        lines = Utils.splitIntoLines(input);

        if (lines.isEmpty())
            return "";

        if (args.size() < 2)
            throw new IllegalArgumentException("At least 2 option is required.");

        String mode = args.get(0);
        String strLocation = args.get(1);
        List<Map.Entry<Integer, Integer>> locations = resolveLocation(strLocation);

        StringBuffer buffer = new StringBuffer();
        if (mode.equals("-b")) {

            throw new IllegalArgumentException("Unimplemented");

        } else if (mode.equals("-c")) {

            for (String line : lines) {

                for (Map.Entry<Integer, Integer> segment : locations) {
                    Integer from = segment.getKey(),
                            to = segment.getValue();

                    from--;
                    if (from < 0)
                        from = 0;
                    if (from >= line.length())
                        continue;
                    if (to == null ||
                            to > line.length())
                        to = line.length();

                    buffer.append(line.subSequence(from, to));
                }
                results.add(buffer.toString());
                buffer.setLength(0);
            }

        } else
            throw new IllegalArgumentException("Unkown option '" + mode + "'. Only '-b'/'-c' is accepted.");

        return Utils.catenateLines(results);
    }

    private List<Map.Entry<Integer, Integer>> resolveLocation(String strLocation) {

        List<Map.Entry<Integer, Integer>> results = new ArrayList<>();
        String[] segments = strLocation.split(",");

        for (String segment : segments) {
            if (segment.matches("\\d+")) {

                Integer n = new Integer(segment);
                results.add(new AbstractMap.SimpleEntry<Integer, Integer>(n, n + 1));

            } else if (segment.matches("-\\d+")) {

                Integer n = new Integer(segment.substring(1, segment.length()));
                results.add(new AbstractMap.SimpleEntry<Integer, Integer>(0, n));

            } else if (segment.matches("\\d+-")) {

                Integer n = new Integer(segment.substring(0, segment.length() - 1));
                results.add(new AbstractMap.SimpleEntry<Integer, Integer>(n, null));

            } else if (segment.matches("\\d+-\\d+")) {

                String[] temp = segment.split("-");
                Integer from = new Integer(temp[0]),
                        to = new Integer(temp[1]);

                //swap if necessary
                if (from > to) {
                    from = from + to;
                    to = from - to;
                    from = from - to;
                }
                results.add(new AbstractMap.SimpleEntry<Integer, Integer>(from, to));

            } else
                throw new IllegalArgumentException("Cannot resolve '" + segment + "'.");
        }

        //sort by key(start index)
        Collections.sort(results, new Comparator<Map.Entry<Integer, Integer>>() {

            public int compare(Map.Entry<Integer, Integer> pair1, Map.Entry<Integer, Integer> pair2) {

                return pair1.getKey().compareTo(pair2.getKey());
            }
        });

        //merge overlapping pairs
        for (int i = 0; i < results.size(); ) {

            Integer myStart = results.get(i).getKey(),
                    myEnd = results.get(i).getValue();

            if (myEnd == null) {
                results = results.subList(0, i + 1);
                break;
            }

            if (Objects.equals(myStart, myEnd)) {
                results.remove(i);
                continue;
            }

            int j = i + 1;
            for (; j < results.size(); j++) {
                Integer yourStart = results.get(j).getKey(),
                        yourEnd = results.get(j).getValue();
                if (myEnd == null ||
                        myEnd < yourStart) {

                    break;
                } else if(yourEnd > myEnd){
                    myEnd = yourEnd;
                }
            }
            if (j > i + 1) {

                results.set(i, new AbstractMap.SimpleEntry<Integer, Integer>(myStart, myEnd));

                if (myEnd == null) {
                    results = results.subList(0, i + 1);
                    break;
                } else {
                    for (int k = j - 1; k > i; k--) {
                        results.remove(k);
                    }
                }

            }
            else
                i++;
        }

        return results;
    }
}
