import java.util.*;

public class MoneyMaker {
    public List<Integer> denominations = new ArrayList<>();
    public Map<Integer, Integer> moneyToCount = new HashMap<>();
    public int singleSum = 176;
    public int numberOfBuckets = 35;
    public int currBuckets = 0;
    public int numberOfCoins = 20;

    public MoneyMaker() {
        moneyToCount.put(1,209);
        moneyToCount.put(2, 185);
        moneyToCount.put(5, 92);
        moneyToCount.put(10, 106);
        moneyToCount.put(20, 99);
        moneyToCount.put(50, 11);
        moneyToCount.put(100, 17);

        denominations.add(100);
        denominations.add(50);
        denominations.add(20);
        denominations.add(10);
        denominations.add(5);
        denominations.add(2);
        denominations.add(1);
    }
    public static void main(String[] args) {

        MoneyMaker moneyMaker = new MoneyMaker();
        moneyMaker.generateBuckets(new HashMap<>(), 0, 0, 100);
        System.out.println();
        moneyMaker.printBucket(moneyMaker.moneyToCount, 1);
    }

    private void generateBuckets(HashMap<Integer, Integer> currMoneyToCount, int currSum, int currNumberOfCoins, Integer lastDenomination) {
        if (currBuckets >= numberOfBuckets) {
            return;
        }
        if (currSum > singleSum || currNumberOfCoins > numberOfCoins || (currSum == singleSum && currNumberOfCoins != numberOfCoins)) {
            return;
        }
        if (currSum == singleSum && currNumberOfCoins == numberOfCoins) {
            int times = timesBucketInMoney(currMoneyToCount);
            if (times > 0) {
                currBuckets++;
                removeMoneyFromAllMoney(currMoneyToCount, times);
                printBucket(currMoneyToCount, times);
            }
            return;
        }
        for (Integer denomination : denominations) {
            if (denomination <= lastDenomination && moneyToCount.get(denomination) > 0) {
                addDenomination(currMoneyToCount, denomination);

                generateBuckets(currMoneyToCount, currSum + denomination, currNumberOfCoins + 1, denomination);

                currMoneyToCount.put(denomination, currMoneyToCount.get(denomination) - 1);
            }
        }
    }

    private void removeMoneyFromAllMoney(HashMap<Integer, Integer> currMoneyToCount, int times) {
        for (Integer denomination : denominations) {
            if (currMoneyToCount.get(denomination) != null) {
                moneyToCount.put(denomination, moneyToCount.get(denomination) - times * currMoneyToCount.get(denomination));
            }
        }

    }

    private int timesBucketInMoney(HashMap<Integer, Integer> currMoneyToCount) {
        int times = 500;
        for (Integer denomination : denominations) {
            if (currMoneyToCount.get(denomination) != null && moneyToCount.get(denomination) < currMoneyToCount.get(denomination)) {
                return 0;
            }
            if (currMoneyToCount.get(denomination) > 0 && moneyToCount.get(denomination) / currMoneyToCount.get(denomination) < times) {
                times = moneyToCount.get(denomination) / currMoneyToCount.get(denomination);
            }
        }
        return times;
    }

    private void addDenomination(HashMap<Integer, Integer> currMoneyToCount, Integer denomination) {
        if (currMoneyToCount.get(denomination) == null) {
            currMoneyToCount.put(denomination, 1);
        } else {
            currMoneyToCount.put(denomination, currMoneyToCount.get(denomination) + 1);
        }
    }

    public void printBucket(Map<Integer, Integer> currMoneyToCount, int times) {
        for (int time = 0; time < times; time++) {
            denominations.forEach(i -> System.out.print(currMoneyToCount.get(i) + "x" + i + "\t"));
            System.out.println();
        }
    }
}
