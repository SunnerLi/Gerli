"""
    Since the size of the text8 is 100MB that might to large for me,
    so I reduce the size and save it as text10

    Author: SunnerLi
    Finish: 23/10/2016
"""
import gerli_train_config

with open('text8', 'r') as f:
    string = f.read()

with open('text10', 'w') as f:
    f.write(string[:gerli_train_config.lengthOfRevised])
