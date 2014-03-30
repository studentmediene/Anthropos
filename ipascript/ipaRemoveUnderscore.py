import commands

__author__ = 'Boye'


def getAllUsersWithUndescore():
    returnList = [x.strip() for x in commands.getoutput('ipa user-find "_" --pkey-only --sizelimit=9999').split('User login: ')]
    returnList.remove(returnList[0])
    returnList[-1] = returnList[-1].split('\n')[0]
    return returnList


def deleteUsers(userList):
    c = 'ipa user-del '
    for user in userList:
        print commands.getoutput(c + user)


deleteUsers(getAllUsersWithUndescore())
