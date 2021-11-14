package ua.com.alevel;

public final class MathSet {

    private int capacity;
    private int countOfElements = 0;
    private Number[] mathSet;

    public MathSet() {
        mathSet = new Number[5];
        capacity = -1;
    }

    public MathSet(int capacity) {
        mathSet = new Number[capacity];
        this.capacity = capacity;
    }

    public MathSet(Number[] numbers) {
        mathSet = new Number[numbers.length];
        capacity = -1;
        for (Number n : numbers) {
            add(n);
        }
    }

    public MathSet(Number[]... numbers) {
        capacity = -1;
        int totalCountOfNums = getTotalCountOfNumsInVararg(numbers);
        Number[] fullNumbers = new Number[totalCountOfNums];
        mathSet = new Number[totalCountOfNums];
        int k = 0;
        for (int i = 0; i < numbers.length; i++) {
            for (int j = 0; j < numbers[i].length; j++) {
                fullNumbers[k] = numbers[i][j];
                k++;
            }
        }
        for (Number n : fullNumbers) {
            add(n);
        }
    }

    public MathSet(MathSet numbers) {
        Number[] temp = numbers.toArray();
        capacity = numbers.capacity;
        mathSet = new Number[numbers.toArray().length];
        for (int i = 0; i < temp.length; i++) {
            add(temp[i]);
        }
    }

    public MathSet(MathSet... numbers) {
        capacity = -1;
        int totalCountOfNums = 0;
        for (int i = 0; i < numbers.length; i++) {
            totalCountOfNums += numbers[i].toArray().length;
        }
        mathSet = new Number[totalCountOfNums];
        for (int i = 0; i < numbers.length; i++) {
            Number[] temp = numbers[i].toArray();
            for (int j = 0; j < temp.length; j++) {
                add(temp[j]);
            }
        }
    }

    public void add(Number n) {
        if (isExist(n)) {
            return;
        }
        if (capacity == -1) {
            if (mathSet.length == countOfElements) {
                Number[] temp = new Number[mathSet.length + 5];
                for (int i = 0; i < mathSet.length; i++) {
                    temp[i] = mathSet[i];
                }
                mathSet = temp;
            }
            mathSet[countOfElements++] = n;
        } else {
            if (countOfElements == capacity) {
                return;
            } else {
                mathSet[countOfElements++] = n;
            }
        }
    }

    public void add(Number... n) {
        for (Number i : n) {
            add(i);
        }
    }

    public void join(MathSet ms) {
        for (Number n : ms.toArray()) {
            add(n);
        }
    }

    public void join(MathSet... ms) {
        MathSet msFromParam = new MathSet(ms);
        join(msFromParam);
    }

    public void intersection(MathSet ms) {
        Number[] numbers = ms.toArray();
        MathSet general = new MathSet();
        for (int i = 0; i < countOfElements; i++) {
            for (int j = 0; j < ms.countOfElements; j++) {
                if (mathSet[i].doubleValue() == ms.mathSet[j].doubleValue()) {
                    general.add(mathSet[i]);
                }
            }
        }
        clear();
        for (int i = 0; i < general.countOfElements; i++) {
            add(general.mathSet[i]);
        }
    }

    public void intersection(MathSet... ms) {
        MathSet generalMathSetFromParam = new MathSet();
        generalMathSetFromParam.join(ms);
        intersection(generalMathSetFromParam);
    }

    public void sortAsc() {
        for (int i = 0; i < countOfElements; i++) {
            for (int j = i + 1; j < countOfElements; j++) {
                Number tmp = 0;
                if (mathSet[i].doubleValue() > mathSet[j].doubleValue()) {
                    tmp = mathSet[i];
                    mathSet[i] = mathSet[j];
                    mathSet[j] = tmp;
                }
            }
        }
    }

    public void sortAsc(int firstIndex, int lastIndex) {
        for (int i = firstIndex; i < lastIndex; i++) {
            for (int j = i + 1; j <= lastIndex; j++) {
                Number tmp = 0;
                if (mathSet[i].doubleValue() > mathSet[j].doubleValue()) {
                    tmp = mathSet[i];
                    mathSet[i] = mathSet[j];
                    mathSet[j] = tmp;
                }
            }
        }
    }

    public void sortAsc(Number value) {
        int index = getIndexOfNumberInArr(value);
        if (index == -1) {
            System.out.println("This value does not exist in MathSet");
            return;
        }
        for (int i = index; i < countOfElements; i++) {
            for (int j = i + 1; j < countOfElements; j++) {
                Number tmp = 0;
                if (mathSet[i].doubleValue() > mathSet[j].doubleValue()) {
                    tmp = mathSet[i];
                    mathSet[i] = mathSet[j];
                    mathSet[j] = tmp;
                }
            }
        }
    }

    public void sortDesc() {
        for (int i = 0; i < countOfElements; i++) {
            for (int j = i + 1; j < countOfElements; j++) {
                Number tmp = 0;
                if (mathSet[i].doubleValue() < mathSet[j].doubleValue()) {
                    tmp = mathSet[i];
                    mathSet[i] = mathSet[j];
                    mathSet[j] = tmp;
                }
            }
        }
    }

    public void sortDesc(int firstIndex, int lastIndex) {
        for (int i = firstIndex; i < lastIndex; i++) {
            for (int j = i + 1; j <= lastIndex; j++) {
                Number tmp = 0;
                if (mathSet[i].doubleValue() < mathSet[j].doubleValue()) {
                    tmp = mathSet[i];
                    mathSet[i] = mathSet[j];
                    mathSet[j] = tmp;
                }
            }
        }
    }

    public void sortDesc(Number value) {
        int index = getIndexOfNumberInArr(value);
        if (index == -1) {
            System.out.println("This value does not exist in MathSet");
            return;
        }
        for (int i = index; i < countOfElements; i++) {
            for (int j = i + 1; j < countOfElements; j++) {
                Number tmp = 0;
                if (mathSet[i].doubleValue() < mathSet[j].doubleValue()) {
                    tmp = mathSet[i];
                    mathSet[i] = mathSet[j];
                    mathSet[j] = tmp;
                }
            }
        }
    }

    public Number get(int index) {
        for (int i = 0; i < countOfElements; i++) {
            if (i == index) {
                return mathSet[i];
            }
        }
        return null;
    }

    public Number getMax() {
        Number max = Integer.MIN_VALUE;
        for (Number n : toArray()) {
            if (n.doubleValue() > max.doubleValue()) {
                max = n;
            }
        }
        return max;
    }

    public Number getMin() {
        Number min = Integer.MAX_VALUE;
        for (Number n : toArray()) {
            if (n.doubleValue() < min.doubleValue()) {
                min = n;
            }
        }
        return min;
    }

    public Number getAverage() {
        Number average = 0;
        for (Number n : toArray()) {
            average = average.doubleValue() + n.doubleValue();
        }
        return average.doubleValue() / countOfElements;
    }

    public Number getMedian() {
        MathSet clone = new MathSet(this);
        clone.sortAsc();
        int midIndex;
        midIndex = clone.countOfElements / 2 - 1;
        if (clone.countOfElements % 2 == 0) {
            return (clone.mathSet[midIndex].doubleValue() + clone.mathSet[midIndex + 1].doubleValue()) / 2;
        } else {
            return clone.mathSet[midIndex + 1];
        }
    }

    public Number[] toArray() {
        Number[] res = new Number[countOfElements];
        for (int i = 0; i < countOfElements; i++) {
            res[i] = mathSet[i];
        }
        return res;
    }

    public Number[] toArray(int firstIndex, int lastIndex) {
        Number[] res = new Number[lastIndex - firstIndex + 1];
        for (int i = 0; i < res.length; i++) {
            res[i] = mathSet[firstIndex++];
        }
        return res;
    }

    public MathSet cut(int firstIndex, int lastIndex) {
        Number[] numbers = toArray(firstIndex, lastIndex);
        return new MathSet(numbers);
    }

    public void clear() {
        for (int i = 0; i < countOfElements; i++) {
            mathSet[i] = null;
        }
        countOfElements = 0;
    }

    public void clear(Number[] numbers) {
        for (int i = 0; i < numbers.length; i++) {
            for (int j = 0; j < countOfElements; j++) {
                if (numbers[i].doubleValue() == mathSet[j].doubleValue()) {
                    for (int k = j; k < countOfElements - 1; k++) {
                        mathSet[k] = mathSet[k + 1];
                    }
                }
            }
        }
        for (int i = countOfElements - 1; i > countOfElements - 1 - numbers.length; i--) {
            mathSet[i] = null;
        }
        countOfElements -= numbers.length;
    }

    private int getIndexOfNumberInArr(Number n) {
        for (int i = 0; i < countOfElements; i++) {
            if (mathSet[i].doubleValue() == n.doubleValue())
                return i;
        }
        return -1;
    }

    private boolean isExist(Number n) {
        for (Number i : mathSet) {
            if (i != null) {
                if (i.doubleValue() == n.doubleValue()) {
                    return true;
                }
            }
        }
        return false;
    }

    private int getTotalCountOfNumsInVararg(Number[]... numbers) {
        int totalCountOfNums = 0;
        for (int i = 0; i < numbers.length; i++) {
            for (int j = 0; j < numbers[i].length; j++) {
                totalCountOfNums++;
            }
        }
        return totalCountOfNums;
    }
}