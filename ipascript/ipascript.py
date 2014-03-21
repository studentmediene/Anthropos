import commands
import sys

def addUser(firstname,lastname,mail,username,mobile):
	
	if firstname == "" or lastname == "" or username == "" : return username + " not added. A required field is empty"
	if mail == "" : mail = username
	if mobile == "" : mobile = "00000000"

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
        c = "ipa group-add-member " + group + " --users " + user
        s = commands.getoutput(c)
        
        if "Failed members:" in s:
                if "no such entry" in s:
                	return "User \"" + user + "\" not foud"
                else:
                	return "User \"" + user + "\" if already member of \"" + group + "\""
	elif "ERROR" in s:
		return "Group \"" + group + "\" not found"
	
        return user + " added to " + group

def sendMail(mail,username,password,name):
	import smtplib
	import email.utils
	import string
	from email.mime.text import MIMEText
	
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
	mail = raw_input("Email ["+username+"@smint.no]: ")
	mobile = raw_input("Mobile number: ")
	
	print addUser(firstname,lastname,mail,username,mobile)
	
def help():
	return "not avaliable yet... see [ https://github.com/boyeborg/IPA-SMMDB/tree/master/ipascript ] for instructions"

def deleteUser(username):
	c = "ipa user-del " + username
	s = commands.getoutput(c)
	
	if "ERROR" in s:
		return "User \"" + username + "\" not found"
	
	return "User \"" + username + "\" deleted"
	
def addGroup(group,description):
	c = "ipa group-add " + group + " --desc " + description
	s = commands.getoutput(c)
	
	if "ERROR" in s:
		if "invalid 'group_name'" in s:
			return "\"" + group + "\" is not a valid group name"
		else:
			return "Group with name \"" + group + "\" already exists"
	elif not "Added group" in s:
		return "-----\nERROR!\n" + s + "\n-----"
	
	return "Group \"" + group + "\" added"
	
def deleteGroup(group):
	c = "ipa group-del " + group
	s = commands.getoutput(c)
	
	if "ERROR" in s:
		return "Group \"" + group + "\" not found"
	
	return "Group \"" + group + "\" deleted"

def addAllUsersToGroup(group):
	#This oneliner should really be replaced
	s = commands.getoutput("ipa group-add-member "+group+" --user "+",".join([x[:len(x)-4] for x in commands.getoutput("ipa user-find --pkey-only").split("User login: ")[1:len(commands.getoutput("ipa user-find --pkey-only").split("User login: "))-1]]))
	if "ERROR" in s:
		return "Error!\n" + s
	return "All users added to group \"" + group + "\""
	
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
			groups = lineAry[5].spilt(",")
			for group in groups:
				print addUserToGroup(group,username)

def argHandler(args):
        if len(args) == 0:
                print "Please enter an argument"
        elif(arg[0] == "add-user"):
                if len(args) == 1:
                        addOneUser()
                elif len(args) == 2:
                	addUsersFromFile(args[1])
                else:
                     	print "Too many arguments. Expected one or two, " + len(args) + " given."
        elif(args[0] == "del-user"):
		if len(args) == 2:
			print deleteUser(args[1])
		else:
			print "Expected two arguments, " + len(args) + " given"
	elif(args[0] == "add-group"):
		if len(args) == 3:
			print addGroup(args[1],args[2])
		else:
			print "Expected three arguments, " + len(args) + " given"
	elif(args[0] == "del-group"):
		if len(args) == 2:
			print deleteGroup(args[1])
		else:
			print "Expected two arguments, " + len(args) + " given"
	elif(args[0] == "add-all"):
		if len(args) == 2:
			warning = "This will add all users to the group \"" + args[1] + "\". Are you sure you want to continue [Y/N]? "
			answer = input(warning)
			while answer.upper() != "Y":
				if answer.upper() == "N":
					return
				answer = input(warning)
			print addAllUsersToGroup(group)
        elif(args[0] == "help"):
        	print help()
        else:
             	print "Not valid argument, try \"help\" for help"

argHandler(sys.argv[1:])
