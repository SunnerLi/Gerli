def voteWork():
    """
        Deal with the result of the vote
    """
    wholeText = ""

    # Generate positive file
    with open('sentenceses.txt', 'r') as f:
        wholeText = f.readlines()

    for i in range(len(wholeText)):
        try:
            if len(wholeText[i]) == 1:
                wholeText.remove(wholeText[i])
            if wholeText[i][0] == '@':
                wholeText.remove(wholeText[i])
        except IndexError:
            break

    with open('voteResult.pos', 'w') as f:
        f.writelines(wholeText)

    # Generate negative file
    res = []
    for sentence in wholeText:
        sentence = sentence.split()
        for word in sentence:
            if word.isdigit():
                word = int(word) * 10
                sentence[sentence.index(str(word/10))] = word
        res.append(sentence)
    for i in range(len(res)):
        string = ""
        for j in range(len(res[i])):
            string = string + str(res[i][j]) + ' '
        string += '\n'
        res[i] = string
    with open('voteResult.neg', 'w') as f:
        f.writelines(res)