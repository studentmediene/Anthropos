import commands
import sys
import json

def curl(type,parameter):
        c = "curl -H referer:https://ipa.studentmediene.local/ipa" + \
	"-H \"Content-Type:application/json\"" + \
	"-H \"Accept:applicaton/json\"" + \
	"--negotiate -u : --delegation always --cacert /etc/ipa/ca.crt -d" + \
	"\'{\"method\":\""+type+"\",\"params\":[[\""+parameter+"\"],{}],\"id\":0}\'" + \
	"-X POST https://ipa.studentmediene.local/ipa/json"
	
        s=commands.getstatusoutput(c)
        return  json.loads(s[1][s[1].index("{"):])

def getEmails(group):
        list =  curl("group_find",group)["result"]["result"][0]["member_user"]
        for name in list:
                #Obs, admin does not have an email (results in an error)
                print curl("user_find",name)["result"]["result"][0]["mail"][0]

def getGroups():
        list =  curl("group_find","")["result"]["result"]
        for group in list:
                print group["cn"][0]


def userAdd(givenname,sn,mail,uid,mobile):
        c = "curl -H referer:https://ipa.studentmediene.local/ipa" + \
	"-H \"Content-Type:application/json\"" + \
	"-H \"Accept:applicaton/json\"" + \
	"--negotiate -u : --delegation always --cacert /etc/ipa/ca.crt -d" + \
	"\'{\"method\":\"user_add\",\"params\":[[],{\"givenname\":\""+givenname+"\"," + \
	"\"sn\":\""+sn+"\", \"userpassword\":\""+mobile+"\",\"mail\":\""+mail+"\","+ \
	"\"uid\":\""+uid+"\",\"telephonenumber\":\""+mobile+"\",\"loginshell\":\"/bin/sh\"}],\"id\":0}'" + \
	"-X POST https://ipa.studentmediene.local/ipa/json"
	
        s = commands.getstatusoutput(c)
        js = json.loads(s[1][s[1].index("{"):])
	
        if(js["error"] != None): return js["error"]["message"]
        return js["result"]["summary"]

def argHandler(args):
	if(len(args) == 0):
		print "Error: please enter valid argument, try -help for help"
		return
	if(len(args) == 1):
		if(args[0] == "-help"):
			print "Not available yet"
			return
		elif(args[0] == "-add"):
			givenname = input("Givenname: ")
			sn = String(input("Familyname: "))
			uid = String(input("Username: "))
			mail = String(input("Email: "))
			mobile = String(input("Mobile number: "))
			print userAdd(givenname,sn,mail,uid,mobile)
			return
		else:
			return "Error: please enter valid argument, try -help for help"
	elif(len(args) == 2 and args[0] == "-add"):
		try:
			f = open(args[1],"r");
			for line in f:
				lineAry = line.split(";")
				if(len(lineAry) != 5):
					print "Error: Too few arguments!"
					return
				userAdd(lineAry[0],lineAry[1],lineAry[2],lineAry[3],lineAry[4])
		except:
			print sys.exc_info()[0]
			return

argHandler(sys.argv[1:])
	

#print userAdd("Jon","Tarstad","jon.tarstad@gmail.com","jonuser","45454545")
#getEmails("ipausers")
#getGroups()
#print curl("user_find","boyeborg")["result"]["result"][0]
