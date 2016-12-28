from collections import Counter
import gerli_train_config

def getFreqList():
    with open(gerli_train_config.train_data, 'r') as f:
        string = f.readline().split()
        freqPairs = Counter(string).most_common(gerli_train_config.freqNumber)
        freqList = []
        for i in range(gerli_train_config.freqNumber):
            freqList.append(freqPairs[i][0])
    return freqList

if __name__ == "__main__":
    getFreqList()
