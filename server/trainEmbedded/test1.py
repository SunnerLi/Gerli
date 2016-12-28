from collections import Counter

string = ['a', 'a', 'a', 'b', 'b', 'c']
counter = Counter(string)
print counter.most_common(2)