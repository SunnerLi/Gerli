class Color():
    HEADER = '\033[95m'
    OKBLUE = '\033[94m'
    OKGREEN = '\033[92m'
    WARNING = '\033[93m'
    FAIL = '\033[91m'
    ENDC = '\033[0m'
    BOLD = '\033[1m'
    UNDERLINE = '\033[4m'   

TAG = "<Gerli>  "

# Constants to judge the type of input
err = -1
yes = 0 

def show(arg1="", arg2="", arg3="", arg4="", Type=yes):
    string = str(arg1) + str(arg2) + str(arg3) + str(arg4)
    if Type == yes: 
        print Color.OKGREEN + TAG + string + Color.ENDC
    if Type == err: 
        print Color.FAIL + TAG + string + Color.ENDC