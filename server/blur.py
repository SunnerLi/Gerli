# Converter mapping
number2String = {
    0 : 'zero',
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
            if len(stack) == 0:
                stack.append(0)
            digit = stack[-1]
            if not digit == 0:
                _res.append(number2String[digit])
                if len(stack) > 1 and len(stack) < 6:
                    _res.append(digit2String[len(stack)])
            stack.remove(digit)
    return _res

def List2Strings(_list):
    """
        Convert the string list to string format

        Arg:    _list - The double string list you want to convert
        Ret:    The list of the strings
    """
    string = []
    _string = ""
    for word in _list:
        _string = _string + str(word) + ' '
    _string += '\n'
    string.append(_string)
    return string

def blur(string):
    """
        The wrapper function to do the blurring process

        Arg:    string - The string want to blur
        Ret:    The string that has been blurred
    """
    return List2Strings(numberConvertAlpha(string.split()))[0]

if __name__ == "__main__":
    print blur("I spend 52 dollars on pens")