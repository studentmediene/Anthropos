import commands
import sys
import smtplib
import email.utils
import string
from email.mime.text import MIMEText

def addUser(firstname,lastname,mail,username,mobile):

        c =     "ipa user-add " + username+ "  --first " + firstname + " --last " + \
                lastname + " --shell /bin/sh --email " + mail + " --phone " + mobile + \
                " --random"

        s = commands.getoutput(c)

        if not "Added user" in s:
                return s

        password =  s[s.index("Random password: ")+17 : s.index("UID:")]
        fullname = firstname + " " + lastname
        status = s[: s.index("User login:")]
        sendMail(mail,username,password,fullname)

        return "User added: " + username + " (Password sent to " + mail + ")"

def addUserToGroup(group,user):
        c = "ipa group-add-member " + group + " --users " + ",".join(users)
        s = commands.getoutput(c)

        if "Failed members:" in s:
                return s

        return ",".join(users) + " added to " + group

def sendMail(mail,username,password,name):
        msgText = open('msgText.txt', 'r').read().split('{split}')

        msg = MIMEText(msgText[0]+username+msgText[1]+password+msgText[2])
        msg['To'] = email.utils.formataddr((name, mail))
        msg['From'] = email.utils.formataddr(('IT-Drift', 'it-drift@studentmediene.no'))
        msg['Subject'] = 'Brukerkonto'

        server = smtplib.SMTP('mail.studentmediene.local')
        server.sendmail('it-drift@studentmediene.no', [mail], msg.as_string())
        server.quit()
        
def addOneUser():
	firstname = raw_input("First Name: ")
	lastname = raw_input("Last Name: ")
	username = raw_input("Username: ")
	mail = raw_input("Email: ")
	mobile = raw_input("Mobile number: ")
	
	print addUser(firstname,lastname,mail,username,mobile)
	
def help():
	print "not avaliable yet... see [ https://github.com/boyeborg/IPA-SMMDB ] for instructions"
	
def addUsersFromFile(the_file):
	f = open(thefile,"r")
	for line in f:
		lineAry = line.split(";")
		lineAry[-1] = lineAry[-1][:len(lineAry[-1])-1]
		
		if not (5 <= len(lineAry) <= 6):
			print "----------"
			print "Error: Too few arguments on line:"
			print line
			print "----------"
			continue
                
		username = lineAry[0]        
		firstname = lineAry[1]
		lastname = lineAry[2]
		mail = lineAry[3]
		mobile = lineAry[4]
		print addUser(firstname,lastname,mail,username,mobile)
		if len(lineAry) == 6:
			group = lineAry[5]
			print addUserToGroup(group,[username])

def argHandler(args):
        if(len(args) == 0):
                print "Please enter an argument"
        elif(arg[0] == "add-user"):
        	#User add options
                if(len(args) == 1):
                        addOneUser()
                elif(len(args) == 2):
                	addUsersFromFile(args[1])
                else:
                     	print "Too many arguments. Expected one or two, "+len(args)+" given."
        elif(args[0] == "del-user"):
		##nothing yet
		return
        elif(args[0] == "help"):
        	help()
                
        else:
             	print "Not valid argument, try <ipascript help> for help"

argHandler(sys.argv[1:])
