#-*- coding: utf-8 -*-
import commands
import smtplib
import email.utils
from email.MIMEMultipart import MIMEMultipart
from email.MIMEText import MIMEText
import string


def user_exists(term, value):
    s = commands.getoutput("ipa user-find --" + term + " " + value)

    if len(s) > 210:
        return True

    return False


def add_user(forename, lastname, mail, username, phone, active):
    global log
    if forename == "" or lastname == "" or username == "":
        return username + " not added. A required field is empty"
    if mail != "":
        passMail = mail
        mail = " --email " + mail
    if phone != "":
        phone = " --phone " + phone

    c = "ipa user-add " + "'" + username + "'" + "  --first " + "'" + forename.strip() + "'" + " --last " + \
        "'" + lastname.strip() + "'" + " --shell /bin/sh" + mail + phone + " --random"

    s = commands.getoutput(c)

    if not "Added user" in s:
        log.write("-----Error-----\n" + s + "\n---------------\n")
	print("-----Error-----\n" + s + "\n---------------\n")
	print("User: " + username)
        return False

    if mail != "" and active:

        password = s[s.index("Random password: ") + 17: s.index("UID:")]
        fullname = forename + " " + lastname
        send_password(passMail, username, password, fullname)
        log.write("User added: " + username + " (Password sent to " + passMail + ")\n")
	print("User added: " + username + " (Password sent to " + passMail + ")\n")
        return True

    log.write("User added: " + username + " (Password not sent)\n")
    print("User added: " + username + " (Password not sent)\n")
    return True


def add_users_to_group(group, users):
    global log
    c = "ipa group-add-member " + group + " --users " + ",".join(users)
    s = commands.getoutput(c)

    if "Failed members:" in s:
        error = s[s.index("Failed members:") + 15:].strip().split("member user: ")
        error[-1] = error[-1].split("member group:")[0]
        error.remove(error[0])
        log.write(", ".join([e.strip() for e in error])+'\n')
	print(", ".join([e.strip() for e in error])+'\n')
        return False
    elif "ERROR" in s:
        log.write("Group \"" + group + "\" not found\n")
	print("Group \"" + group + "\" not found\n")
        return False

    log.write(", ".join(users) + " added to " + group + '\n')
    print(", ".join(users) + " added to " + group + '\n')
    return True


def add_group(group, description):
    global log
    c = "ipa group-add " + group + " --desc \"" + description + "\""
    s = commands.getoutput(c)

    if "ERROR" in s:
        if "invalid 'group_name'" in s:
            log.write("\"" + group + "\" is not a valid group name\n")
	    print("\"" + group + "\" is not a valid group name\n")
            return False
        else:
            log.write("Group with name \"" + group + "\" already exists\n")
	    print("Group with name \"" + group + "\" already exists\n")
            return False
    elif not "Added group" in s:
        log.write("-----\nERROR-----\n" + s + "\n---------------\n")
	print("-----\nERROR-----\n" + s + "\n---------------\n")
        return False

    log.write("Group \"" + group + "\" added\n")
    print("Group \"" + group + "\" added\n")
    return True

def send_password(mail, username, password, name):

    msg_text = open('msgText.txt', 'r').read().split('{split}')

    Body = MIMEText(msg_text[0] + username + msg_text[1] + password + msg_text[2])
    msg = MIMEMultipart()
    msg['To'] = email.utils.formataddr((name, mail))
    msg['From'] = email.utils.formataddr(('IT-Drift', 'it-drift@studentmediene.no'))
    msg['Subject'] = 'Brukerkonto'
    msg['Reply-to'] = 'bruker@studentmediene.no'
    msg.attach(Body)

    server = smtplib.SMTP('mail.studentmediene.local')
    server.sendmail('it-drift@studentmediene.no', [mail], msg.as_string())
    server.quit()


def add_users_to_role(role,users):
    global log
    c = "ipa role-add-member " + role + " --users " + ",".join(users)
    s = commands.getoutput(c)

    if "ERROR" in s:
        log.write("Role \"" + role + "\" not found\n")
	print("Role \"" + role + "\" not found\n")
        return False
    elif "Failed members:" in s:
        error = s[s.index("Failed members:") + 15:].strip().split("member user: ")
        error[-1] = error[-1].split("member group:")[0]
        error.remove(error[0])
        log.write(", ".join([e.strip() for e in error]) + '\n')
	print(", ".join([e.strip() for e in error]) + '\n')
        return False

    return True


def get_names(name):
    name = name.strip().split(" ")

    return [" ".join(name[:-1]), name[-1]]


def get_username(name):
    fullname = name.strip().split(' ')
    username = ''
    for word in fullname:
        word = word.replace('å','aa')
	word = word.replace('Å','aa')
        word = word.replace('ø','oe')
	word = word.replace('Ø','oe')
        word = word.replace('æ','ae')
	word = word.replace('Æ','ae')
	word = filter(lambda x : x in string.printable, word)
	word = word.replace('.','')
	word = word.lower()
        username += word+'_'
    username = username[:-1]
    if(user_exists('login',username)):
        return ''
    return username

def remove_specials(s):
    s = s.strip()
    s = s.replace('å','aa')
    s = s.replace('Å','aa')
    s = s.replace('ø','oe')
    s = s.replace('Ø','oe')
    s = s.replace('æ','ae')
    s = s.replace('Æ','ae')
    s = s.replace('.','')
    s = s.lower()
    return s


log = open('log.txt','w')
f = open("medlemsliste.csv")

users = list()
sm = list()
ukjent = list()

for line in f:
    person = line.split(",")
    username = remove_specials(person[0])
    forename = person[1]
    lastname = person[2]
    phone = person[3]
    mail = person[4]
    memberOf = person[5][:-1]
    if memberOf.lower() == "sm":
        sm.append({"name": forename, "phone": phone, "mail": mail})
    else:
        users.append({"username": username, "forename": forename, "lastname": lastname,
                      "phone": phone, "mail": mail, "memberOf": memberOf, "active": False})


for aUser in sm:
    if aUser["mail"] == "" and aUser["phone"] == "":
        log.write("Not added: " + aUser["name"] + " (lack of mail, username and phone)\n")
	print("Not added: " + aUser["name"] + " (lack of mail, username and phone)\n")
        continue
    for user in users:
        if user["mail"] == aUser["mail"] and aUser["mail"] != "":
            user["active"] = True
            break
        elif user["phone"] == aUser["phone"] and aUser["phone"] != "":
            user["active"] = True
            break
    else:
        if aUser["mail"] == "":
            log.write("Not added: " + aUser["name"] + " (lack of mail and username)\n")
	    print("Not added: " + aUser["name"] + " (lack of mail and username)\n")
            continue
        add = not user_exists("email", aUser["mail"])
        if add and aUser["phone"] != "":
            add = not user_exists("phone", aUser["phone"])
        if add:
            name = get_names(aUser["name"])
            username = get_username(aUser["name"])
            if username == "":
                log.write("Not added: " + aUser["name"] + ". Username already exists\n")
		print("Not added: " + aUser["name"] + ". Username already exists\n")
                continue
            add_user(name[0], name[1], aUser["mail"],username,aUser["phone"],True)
            ukjent.append(username)
            add_users_to_role("Aktiv", [username])
        else:
            log.write(aUser["name"] + " not added. (User exists)\n")
            print(aUser["name"] + " not added. (User exists)\n")

dusken = list()
radio = list()
stv = list()
aktive = list()
inaktive = list()

for user in users:
    if add_user(user["forename"], user["lastname"], user["mail"], user["username"], user["phone"], user["active"]):
        if user["memberOf"].lower() == "dusken":
            dusken.append(user["username"])
        elif user["memberOf"].lower() == "radio":
            radio.append(user["username"])
        elif user["memberOf"].lower() == "stv":
            stv.append(user["username"])
        else:
            log.write("Group " + user["memberOf"] + " is unknown\n")
            print("Group " + user["memberOf"] + " is unknown\n")
        if user["active"]:
            aktive.append(user["username"])
        else:
            inaktive.append(user["username"])

add_users_to_group("Dusken", dusken)
add_users_to_group("Radio", radio)
add_users_to_group("STV", stv)
add_users_to_group("ukjent",ukjent)
add_users_to_role("Aktiv", aktive)
add_users_to_role("Inaktiv", inaktive)

log.close()
f.close()

print "Ferdig!"
