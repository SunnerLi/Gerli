"""
    This program define the colorful server log
    You can used it to seperate the unofficial log and official log
"""

class Color():
    HEADER = '\033[95m'
    OKBLUE = '\033[94m'
    OKGREEN = '\033[92m'
    WARNING = '\033[93m'
    FAIL = '\033[91m'
    ENDC = '\033[0m'
    BOLD = '\033[1m'
    UNDERLINE = '\033[4m'

# Tag at first
TAG = "<Gerli>  "

# Constants to judge the type of input
err = -1
yes = 0
war = 1 

def show(arg1="", arg2="", arg3="", arg4="", Type=yes):
    """
        Show the information

        Arg:    arg*    - The element you want to print
                Type    - The color you can assign
    """
    string = str(arg1) + str(arg2) + str(arg3) + str(arg4)
    if Type == yes: 
        print Color.OKGREEN + TAG + string + Color.ENDC
    if Type == err: 
        print Color.FAIL + TAG + string + Color.ENDC
    if Type == war:
        print Color.WARNING + TAG + string + Color.ENDC