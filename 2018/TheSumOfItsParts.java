import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.Collections;

public class TheSumOfItsParts {
    private final static int FIRST_STEP_INDEX = 5;
    private final static int SECOND_STEP_INDEX = 36;
    private final Map<Character, Step> nameMapper;

    public TheSumOfItsParts() {
        this.nameMapper = new HashMap<>();
    }

    public static void main(String[] args) {
        System.out.println(findPath(new String[] { "Step C must be finished before step A can begin.",
                "Step C must be finished before step F can begin.", "Step A must be finished before step B can begin.",
                "Step A must be finished before step D can begin.", "Step B must be finished before step E can begin.",
                "Step D must be finished before step E can begin.",
                "Step F must be finished before step E can begin." }));
        System.out.println(findPath(FileToArray.readFile("./resources/TheSumOfItsParts")));
    }

    public static String findPath(String[] lines) {
        TheSumOfItsParts sumOfPart = new TheSumOfItsParts();
        List<char[]> stepsNameList = sumOfPart.dealLines(lines);
        List<Character> startingSteps = sumOfPart.findStartingPoints(stepsNameList);
        sumOfPart.loadMapper(stepsNameList);
        return sumOfPart.getOrder(sumOfPart.nameMapper, startingSteps);
    }

    private List<char[]> dealLines(String[] lines) {
        List<char[]> stepsNameList = new ArrayList<>();
        for (int i = 0; i < lines.length; i++) {
            char firstStepName = getFirstStep(lines[i]);
            char secondStepName = getSecondStep(lines[i]);
            stepsNameList.add(new char[] { firstStepName, secondStepName });
        }
        return stepsNameList;
    }

    private List<Character> findStartingPoints(List<char[]> stepsNameList) {
        Set<Character> from = new HashSet<>();
        Set<Character> to = new HashSet<>();
        for (char[] stepName : stepsNameList) {
            from.add(stepName[0]);
            to.add(stepName[1]);
        }
        from.removeAll(to);
        return new ArrayList<>(from);
    }

    private void loadMapper(List<char[]> stepsNameList) {
        for (char[] stepName : stepsNameList) {
            char firstStepName = stepName[0];
            Step firstStep = findStepOrUpdateMap(firstStepName);
            char secondStepName = stepName[1];
            Step secondStep = findStepOrUpdateMap(secondStepName);
            firstStep.addStep(secondStep);
            secondStep.addPreviousStep(firstStep);
        }

    }

    private String getOrder(Map<Character, Step> nameMapper, List<Character> startingSteps) {
        StringBuilder sb = new StringBuilder();
        List<Step> firstSteps = startingSteps.stream().map(stepName -> findStepOrUpdateMap(stepName))
                .collect(Collectors.toList());
        Collections.sort(firstSteps);
        List<Step> steps = new ArrayList<>(firstSteps);
        Set<Character> nameSet = new HashSet<>();

        while (!steps.isEmpty()) {
            System.out.println(steps);
            Step step = steps.remove(0);
            System.out.println(step + " " + nameSet + " " + step.previousSteps);
            if (nameSet.containsAll(step.previousSteps.stream().map(it -> it.name).collect(Collectors.toList()))) {
                steps.addAll(step.newSteps);
                if (nameSet.add(step.name)) {
                    sb.append(step.name);
                }
                Collections.sort(steps);
            } else {
                steps.add(step);
            }
        }

        return sb.toString();
    }

    private char getFirstStep(String line) {
        return line.charAt(FIRST_STEP_INDEX);
    }

    private char getSecondStep(String line) {
        return line.charAt(SECOND_STEP_INDEX);
    }

    private Step findStepOrUpdateMap(char name) {
        Step step;
        if (nameMapper.containsKey(name)) {
            step = nameMapper.get(name);
        } else {
            step = new Step(name);
            nameMapper.put(name, step);
        }
        return step;
    }

    private static class Step implements Comparable<Step> {
        private final char name;
        private final List<Step> newSteps;
        private final List<Step> previousSteps;

        private Step(char name) {
            this.name = name;
            this.newSteps = new ArrayList<>();
            this.previousSteps = new ArrayList<>();
        }

        private void addPreviousStep(Step step) {
            previousSteps.add(step);
        }

        private void addStep(Step step) {
            newSteps.add(step);
        }

        public int compareTo(Step otherStep) {
            return this.name - otherStep.name;
        }

        public String toString() {
            return name + "";
        }

    }
}