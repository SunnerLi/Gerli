import ServerPrint as sp

class Record(object):
    id = -1
    name = None
    money = -1
    _type = -1
    time = None
    description = None

    def __init__(self, name="", money=-1):
        self.__setName(name)
        self.__setMoney(money)

    def __setId(self, id):
        self.id = id

    def __setName(self, name):
        self.name = name

    def __setMoney(self, money):
        self.money = int(money)

    def __setType(self, _type):
        self._type = int(_type)

    def __setTime(self, time):
        self.time = time

    def __setDescription(self, description):
        self.description = description

    def __getId(self):
        return self.id

    def __getName(self):
        return self.name

    def __getValue(self):
        return self.money

    def __getType(self):
        return self._type

    def __getTime(self):
        return self.time

    def __getDescription(self):
        return self.description

    def dump(self):
        sp.show("record dump --> Value: ", self.__getValue())
        sp.show("record dump --> Name : ", self.__getName())
        sp.show("record dump --> Type : ", self.__getType())