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

def numberConvertAlpha(string):
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

def List2Strings(_list):
    string = []
    _string = ""
    for word in _list:
        _string = _string + str(word) + ' '
    _string += '\n'
    string.append(_string)
    return string

def blur(string):
    return List2Strings(numberConvertAlpha(string.split()))

if __name__ == "__main__":
    print blur("I spend 52 dollars on pens")