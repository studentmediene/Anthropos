import commands
import json

def curl(type,parameter):
        c = "curl -H referer:https://ipa.studentmediene.local/ipa -H \"Content-Type:application/json\" -H \"Accept:applicaton/json\" --negotiate -u : --delegation always --cace$
        s=commands.getstatusoutput(c)
        return  json.loads(s[1][s[1].index("{"):])

def getEmails():
        list =  curl("group_find",group)["result"]["result"][0]["member_user"]
        for name in list:
                #Obs, admin har ikke mail
                print curl("user_find",name)["result"]["result"][0]["mail"][0]

def getGroups():
        list =  curl("group_find","")["result"]["result"]
        for group in list:
                print group["cn"][0]


def userAdd(givenname,sn,mail,uid,mobile):
        c = "curl -H referer:https://ipa.studentmediene.local/ipa -H \"Content-Type:application/json\" -H \"Accept:applicaton/json\" --negotiate -u : --delegation always --cace$
        s = commands.getstatusoutput(c)
        js = json.loads(s[1][s[1].index("{"):])
        if(js["error"] != None): return js["error"]["message"]
        return js["result"]["summary"]


#print userAdd("Jon","Tarstad","jon.tarstad@gmail.com","jonuser","45454545")
#getEmails("ipausers")
#getGroups()
#print curl("user_find","boyeborg")["result"]["result"][0]
