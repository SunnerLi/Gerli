from numberConvert import *
import ServerPrint as sp

class ValueSubjectError():
    pass

"""
    The class is the basic element to deal with database
    The whole things would implement as same as Java platform
"""
class Record(object):
    __id = -1               # The id that would used in SQLite
    __name = None           # The name of the transaction
    __money = -1            # The value of the spending
    __type = -1             # The type of the transection
    __time = None           # The time when the deal happen
    __description = None    # The other description

    def __init__(self, name="", money=-1):
        # Judge if the subject is value string
        for word in name.split():
            if word == 'dollar' or word == 'dollars':
                raise ValueSubjectError
        self.setName(name)
        self.setMoney(money)

    def setId(self, id):
        self.__id = id

    def setName(self, name):
        self.__name = name

    def setMoney(self, money):
        """
            Set the value of the record
            In the later version, this function support money is a string
        """
        try:
            self.__money = int(money)
        except ValueError:
            for word in money.split():
                if word.isdigit():
                    self.__money = int(word)
            if self.__money == -1:
                self.__money = text2Num(money)

    def setType(self, _type):
        self.__type = int(_type)

    def setTime(self, time):
        self.__time = time

    def setDescription(self, description):
        self.__description = description

    def getId(self):
        return self.__id

    def getName(self):
        return self.__name

    def getValue(self):
        return self.__money

    def getType(self):
        return self.__type

    def getTime(self):
        return self.__time

    def getDescription(self):
        return self.__description

    def dump(self):
        """
            Dump the fundemential element of the object
        """
        sp.show("record dump --> Value: ", self.getValue())
        sp.show("record dump --> Name : ", self.getName())
        sp.show("record dump --> Type : ", self.getType())

    def serialize(self):
        """
            Change the object from Record instance into string type
            It should be done before transmit back to the phone

            Ret:    The serialized string
        """
        _res = dict()
        _res["id"] = self.getId()
        _res["name"] = self.getName()
        _res["money"] = self.getValue()
        _res["type"] = self.getType()
        _res["time"] = self.getTime()
        _res["description"] = self.getDescription()
        return _res