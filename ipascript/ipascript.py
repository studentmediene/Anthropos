import commands


def user_exists(term, value):
    s = commands.getoutput("ipa user-find --" + term + " " + value)

    if len(s) > 210:
        return True

    return False


def add_user(forename, lastname, mail, username, phone, active):
    if forename == "" or lastname == "" or username == "":
        return username + " not added. A required field is empty"
    if mail != "":
        mail = " --email " + mail
    if phone != "":
        phone = " --phone " + phone

    c = "ipa user-add " + username + "  --first " + forename + " --last " + \
        lastname + " --shell /bin/sh" + mail + phone + " --random"

    s = commands.getoutput(c)

    if not "Added user" in s:
        print "-----Error-----\n" + s + "\n---------------"
        return False

    if mail != "" and active:

        password = s[s.index("Random password: ") + 17: s.index("UID:")]
        fullname = forename + " " + lastname
        send_password(mail, username, password, fullname)
        print "User added: " + username + " (Password sent to " + mail + ")"
        return True

    print "User added: " + username + " (Password not sent)"
    return True


def add_users_to_group(group, users):
    c = "ipa group-add-member " + group + " --users " + ",".join(users)
    s = commands.getoutput(c)

    if "Failed members:" in s:
        error = s[s.index("Failed members:") + 15:].strip().split("member user: ")
        error[-1] = error[-1].split("member group:")[0]
        error.remove(error[0])
        print [e.strip() for e in error]
        return False
    elif "ERROR" in s:
        print "Group \"" + group + "\" not found"
        return False

    print user + " added to " + group
    return True


def add_group(group, description):
    c = "ipa group-add " + group + " --desc \"" + description + "\""
    s = commands.getoutput(c)

    if "ERROR" in s:
        if "invalid 'group_name'" in s:
            print "\"" + group + "\" is not a valid group name"
            return False
        else:
            print "Group with name \"" + group + "\" already exists"
            return False
    elif not "Added group" in s:
        print "-----\nERROR-----\n" + s + "\n---------------"
        return False

    print "Group \"" + group + "\" added"
    return True


def send_password(mail, username, password, name):
    import smtplib
    import email.utils
    from email.mime.text import MIMEText

    msg_text = open('msgText.txt', 'r').read().split('{split}')

    msg = MIMEText(msg_text[0] + username + msg_text[1] + password + msg_text[2])
    msg['To'] = email.utils.formataddr((name, mail))
    msg['From'] = email.utils.formataddr(('IT-Drift', 'it-drift@studentmediene.no'))
    msg['Subject'] = "Brukerkonto"

    server = smtplib.SMTP('mail.studentmediene.local')
    server.sendmail('it-drift@studentmediene.no', [mail], msg.as_string())
    server.quit()


def add_users_to_role(role,users):
    c = "ipa role-add-member " + role + " --users " + ",".join(users)
    s = commands.getoutput(c)

    if "ERROR" in s:
        print "Role \"" + role + "\" not found"
        return False
    elif "Failed members:" in s:
        error = s[s.index("Failed members:") + 15:].strip().split("member user: ")
        error[-1] = error[-1].split("member group:")[0]
        error.remove(error[0])
        print [e.strip() for e in error]
        return False

    return True


def get_names(name):
    name = name.split(" ")

    return [" ".join(name[:len(name)-1]), name[-1]]


def get_username(name):
	fullname = name.split(' ')
	username = ''
	for word in fullname[:-1]:
		username += word+'.'
	username += fullname[-1]
	if(user_exist('login',username)):
		return ''
	return username


f = open("medlemsliste.csv")

users = list()
sm = list()

for line in f:
    person = line.split(",")
    username = person[0]
    forename = person[1]
    lastname = person[2]
    phone = person[3]
    mail = person[4]
    memberOf = person[5]
    if memberOf == "SM":
        sm.append({"name": forename, "phone": phone, "mail": mail})
    else:
        users.append({"username": username, "forename": forename, "lastname": lastname,
                      "phone": phone, "mail": mail, "memberOf": memberOf, "active": False})


for aUser in sm:
    if aUser["mail"] == "" and aUser["phone"] == "":
        print "Not added: " + aUser["name"] + " (lack of mail, username and phone)"
        continue
    for user in users:
        if user["mail"] == aUser["mail"]:
            user["active"] = True
            break
        elif user["phone"] == aUser["phone"]:
            user["active"] = True
            break
    else:
        if aUser["mail"] != "":
            print "Not added: " + aUser["name"] + " (lack of mail and username)"
            continue
        add = not user_exists("email", aUser["mail"])
        if add and aUser["phone"] != "":
            add = not user_exists("email", aUser["mail"])
        if add:
            name = get_names(aUser["name"])
            add_user(name[0], name[1], aUser["mail"],get_username(aUser["email"]),aUser["phone"],True)
            print "no group added!"
            add_users_to_role("active", get_username(aUser["email"]))
        else:
            print aUser["name"] + " not added. (User exists)"

dusken = list()
radio = list()
stv = list()
aktive = list()
inaktive = list()

for user in users:
    if add_user(user["forename"], user["lastname"], user["mail"], user["username"], user["phone"], user["active"]):
        if user["username"] == "Dusken":
            dusken.append(user["username"])
        elif user["username"] == "Radio":
            radio.append(user["username"])
        elif user["username"] == "stv":
            stv.append(user["username"])
        else:
            print "unknown group"
        if user["aktiv"]:
            aktive.append(user["username"])
        else:
            inaktive.append(user["username"])

add_users_to_group("Dusken", dusken)
add_users_to_group("Radio", radio)
add_users_to_group("STV", stv)
add_users_to_role("aktiv", aktive)
add_users_to_role("inaktiv", inaktive)
