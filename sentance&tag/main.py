import random
import math

# Gaussian noice parameter
ratioOfRadius = 0.5
alphaValue = 0.4
numberOfCopy = 10

# range of the file want to deal with
lowerFileName = 1
upperFileName = 6

sentencesResult = "sentences_r.txt"
tagsResult = "tags_r.txt"

# Converter mapping
number2String = {
    1 : 'one',
    2 : 'two',
    3 : 'three',
    4 : 'four',
    5 : 'five',
    6 : 'six',
    7 : 'seven',
    8 : 'eight',
    9 : 'night'
}
digit2String = {
    5 : 'ten thousands',
    4 : 'thousand',
    3 : 'hundred',
    2 : 'ten'
}

def makeGaussianNoice(sigma, radius, copyTimes, string):
    """
        Generate several sentence that the value is different from gaussian noice

        Arg:    sigma       - The sigma variable of gaussian function
                radius      - The r^2 variable of gaussian function
                copyTimes   - The times that you want to repeat generate
                string      - The string that would be referred
        Ret:    The double list that contains the split strings
    """
    # Get the value of the string
    _string = string.split()
    value = -1
    for word in _string:
        if word.isdigit():
            value = int(word)
    
    # Get the limit
    gaussian = (math.exp(-1 * pow(radius, 2) * 0.5)) / ((2 * math.pi * pow(sigma, 2)) ** 0.5)
    upper = value * gaussian
    lower = value * (2 - gaussian)

    # Generate the string
    position = _string.index(str(value))
    strings = []
    for i in range(copyTimes):
        strings.append(list(_string))
    for i in range(1, copyTimes):
        _value = random.random() * (upper - lower) + lower
        strings[i][position] = str(int(_value))
    return strings

def numbersConvertAlphas(strings):
    """
        Convert the arabic number to the english string
        (For double string list)

        Arg:    strings - The double list want to convert 
        Ret:    The double list that contain the splitted result
    """
    _res = []
    for string in strings:
        _res.append(numberConvertAlpha(string))
    return _res

def numberConvertAlpha(string):
    """
        Convert the arabic number to the english string
        (For normal string list)

        Arg:    string - The list of the string want to convert
        Ret:    The list that contain the splitted result
    """
    _res = []
    for word in string:
        if not word.isdigit():
            _res.append(word)
        else:
            # Push into stack
            value = int(word)
            stack = []
            while value > 0:
                stack.append(value%10)
                value /= 10

            # Convert into string
            #while len(stack) > 0:
            digit = stack[-1]
            if not digit == 0:
                _res.append(number2String[digit])
                if len(stack) > 1 and len(stack) < 6:
                    _res.append(digit2String[len(stack)])
            stack.remove(digit)
    return _res

def doubleList2Strings(dblist):
    """
        Convert the double string list to string format

        Arg:    dblist - The double string list you want to convert
        Ret:    The list of the strings
    """
    string = []
    for _list in dblist:
        _string = ""
        for word in _list:
            _string = _string + str(word) + ' '
        _string += '\n'
        string.append(_string)
    return string

def work():
    """
        load the string file and generate .pos file & .neg file
    """
    wholeString = list()
    for i in range(lowerFileName, upperFileName):
        # Arrange sentences file
        fileName = "sentences_" + str(i) + ".txt"
        with open(fileName, 'r') as f:
            string = f.readlines()
            for i in range(len(string)):
                string[i] = string[i][:-2] + " . \n"
            wholeString += string
        with open(sentencesResult, 'w') as f:
            f.writelines(wholeString)

    wholeString = list()
    for i in range(lowerFileName, upperFileName):
        # Arrange tags file
        fileName = "tags_" + str(i) + ".txt"
        with open(fileName, 'r') as f:
            wholeString += f.readlines()
        with open(tagsResult, 'w') as f:
            f.writelines(wholeString)

    # Splite as pos and neg file
    posFileName = "gerli.pos"
    negFileName = "gerli.neg"
    posStrings = list()
    negStrings = list()
    wholeString1 = None
    wholeString2 = None
    with open(sentencesResult, 'r') as f:
        wholeString1 = f.readlines()
    with open(tagsResult, 'r') as f:
        wholeString2 = f.readlines()
    for i in range(len(wholeString2)):
        if wholeString2[i] == '1\n':
            posStrings.append(wholeString1[i])
        else:
            negStrings.append(wholeString1[i])
    with open(posFileName, 'w') as f:
        f.writelines(posStrings)
    with open(negFileName, 'w') as f:
        f.writelines(negStrings)

    # Generate additional positive data
    strings = ""
    with open(posFileName, 'r') as f:
        strings = f.readlines()
    _res = []
    for string in strings:
        _string = makeGaussianNoice(alphaValue, ratioOfRadius, numberOfCopy, string)
        _string = numbersConvertAlphas(_string)
        _res += doubleList2Strings(_string)
    with open(posFileName, 'w') as f:
        f.writelines(_res)

    # Generate additional negative data
    strings = ""
    with open(negFileName, 'r') as f:
        strings = f.readlines()
    _res = []
    for string in strings:
        _string = makeGaussianNoice(alphaValue, ratioOfRadius, numberOfCopy, string)
        _string = numbersConvertAlphas(_string)
        _res += doubleList2Strings(_string)
    with open(negFileName, 'w') as f:
        f.writelines(_res)


work()
#print doubleList2Strings( numbersConvertAlphas( makeGaussianNoice(alphaValue, ratioOfRadius, numberOfCopy, "I spend 50 dollars on pens") ) )
#print numbersConvertAlphas( [['I', 'spend', '52', 'dollars', 'on', 'pens']] )
#string = "I spend 52 dollars on pens"
#print [string.split()]
#print numberConvertAlpha(string.split())