package Closure;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Closure {
    private static List<String[]> leftPart;
    private static List<String[]> rightPart;
    private static Set<String> initClosure;
    private static Set<String> resultClosure;

    public static void main(String[] args) {
        closureZ();

    }

    public static void init() {
        leftPart = new ArrayList<>();
        rightPart = new ArrayList<>();
        resultClosure = new HashSet<>();
        initClosure = new HashSet<>();
    }

    public static void closureZ() {
        System.out.println("Welcome to CLOSURE calculator.\n" +
                "You start by typing a closure you need to calculate in format:\n" +
                " A, B following by ENTER\nThen type in sequence of FD(Functional Dependencies)\n" +
                "A - BC then ENTER until you finish.\nThen type \"Calculate\"\nTo exit program just type \"exit\"");
        try (BufferedReader bf = new BufferedReader(new InputStreamReader(System.in))) {
            String line;
            init();
            while (!(line = bf.readLine()).equalsIgnoreCase("exit")) {
                if (line.equalsIgnoreCase("calculate")) {
                    while (true) {
                        Set<String> closure = new HashSet<>(resultClosure);
                        for (String[] left : leftPart) {
                            boolean go = true;
                            for (String s : left)
                                if (!resultClosure.contains(s)) go = false;
                            if (go)
                                Collections.addAll(closure, rightPart.get(leftPart.indexOf(left)));
                        }
                        if (resultClosure.equals(closure)) break;
                        else resultClosure.addAll(closure);
                    }
                    System.out.printf("Result of CLOSURE[Z,S]: {%s}+ = {%s}\n",
                            initClosure.toString().replaceAll("[]\\[]", ""),
                            resultClosure.toString().replaceAll("[]\\[]", ""));
                    init();
                    System.out.println("Calculate new closure or exit:");

                } else {
                    if (resultClosure.isEmpty()) {
                        Collections.addAll(resultClosure, line  .trim().split(","));
                        initClosure.addAll(resultClosure);
                    } else {
                        String[] insertLine = line.trim().replaceAll("\\p{Punct}", ",").trim().split(",");
                        leftPart.add(insertLine[0].trim().split(""));
                        rightPart.add(insertLine[1].trim().split(""));
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
